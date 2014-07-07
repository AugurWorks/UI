package com.augurworks.alfred;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Master
 *
 * @author saf
 *
 */
public class PatternParallelRectNet extends RectNetFixed {

    public PatternParallelRectNet() {
        super();
    }

    public PatternParallelRectNet(int depth, int side) {
        super(depth, side);
    }

    /**
     * Trains a neural network from .augtrain file.
     *
     * @param fileName
     *            The absolute path to the .augtrain file.
     * @param nodes
     *            The number of nodes (threads) to use in training.
     * @param verbose
     *            Flag to display debug text or not
     * @return The trained neural network
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static PatternParallelRectNet trainFile(String fileName, int nodes,
            boolean verbose, String saveFile, boolean testing) throws InterruptedException,
            ExecutionException {
        /*
         * Copy-paste from the RectNetFixed parsing code. TODO abstract this
         * code block into another method.
         */
        boolean valid = Net.validateAUGt(fileName);
        if (!valid) {
            System.err.println("File not valid format.");
            throw new RuntimeException("File not valid");
        }
        // Now we need to pull information out of the augtrain file.
        Charset charset = Charset.forName("US-ASCII");
        Path file = Paths.get(fileName);
        String line = null;
        int lineNumber = 1;
        String[] lineSplit;
        int side = 0;
        int depth = 0;
        int rowIter = 0;
        int fileIter = 0;
        double learningConstant = 0;
        int minTrainingRounds = 0;
        double cutoff = 0;
        ArrayList<double[]> inputSets = new ArrayList<double[]>();
        ArrayList<Double> targets = new ArrayList<Double>();
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            while ((line = reader.readLine()) != null) {
                try {
                    lineSplit = line.split(" ");
                    switch (lineNumber) {
                    case 1:
                        // Information about network
                        String[] size = lineSplit[1].split(",");
                        side = Integer.valueOf(size[0]);
                        depth = Integer.valueOf(size[1]);
                        break;
                    case 2:
                        // Information about training run
                        size = lineSplit[1].split(",");
                        rowIter = Integer.valueOf(size[0]);
                        fileIter = Integer.valueOf(size[1]);
                        learningConstant = Double.valueOf(size[2]);
                        minTrainingRounds = Integer.valueOf(size[3]);
                        cutoff = Double.valueOf(size[4]);
                        break;
                    case 3:
                        // Titles
                        break;
                    default:
                        // expected
                        double target = Double.valueOf(lineSplit[0]);
                        targets.add(target);
                        // inputs
                        double[] input = new double[side];
                        size = lineSplit[1].split(",");
                        for (int i = 0; i < side; i++) {
                            input[i] = Double.valueOf(size[i]);
                        }
                        inputSets.add(input);
                        break;
                    }
                    lineNumber++;
                } catch (Exception e) {
                    System.err
                            .println("Training failed at line: " + lineNumber);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            throw new RuntimeException("IOException in parsing file");
        }
        // Information about the training file.
        if (verbose) {
            System.out.println("-------------------------");
            System.out.println("File path: " + fileName);
            System.out.println("Number Inputs: " + side);
            System.out.println("Net depth: " + depth);
            System.out.println("Number training sets: " + targets.size());
            System.out.println("Row iterations: " + rowIter);
            System.out.println("File iterations: " + fileIter);
            System.out.println("Learning constant: " + learningConstant);
            System.out.println("Minimum training rounds: " + minTrainingRounds);
            System.out.println("Performance cutoff: " + cutoff);
            System.out.println("-------------------------");
        }
        /*
         * END of copy-paste region
         */
        long start = System.currentTimeMillis();
        // Create the Parallel Net
        PatternParallelRectNet r = new PatternParallelRectNet(depth, side);
        double maxScore = Double.NEGATIVE_INFINITY;
        double score = 0;
        double testScore = 0;
        double lastScore = Double.POSITIVE_INFINITY;
        double bestCheck = Double.POSITIVE_INFINITY;
        double bestTestCheck = Double.POSITIVE_INFINITY;
        int i = 0;
        boolean brokeAtPerfCutoff = false;

        // For the partitioning (gross) FIXME
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int numberInList = 0; numberInList < inputSets.size(); numberInList++) {
            list.add(numberInList);
        }
        // Partition the data set, kick off children
        final ExecutorService service = Executors.newFixedThreadPool(nodes);

        try {
            for (i = 0; i < fileIter; i++) {
                List<Future<WeightDelta>> futures = new ArrayList<Future<WeightDelta>>(
                        nodes);
                for (int nodeNum = 0; nodeNum < nodes; nodeNum++) {
                    java.util.Collections.shuffle(list);
                    // TODO pick a fraction
                    int subsetSize = inputSets.size() / nodes;
                    subsetSize = 200; // FIXME
                    double[][] inpts = new double[subsetSize][inputSets.get(0).length];
                    double[] desired = new double[subsetSize];
                    for (int location = 0; location < subsetSize; location++) {
                        int index = list.get(location);
                        inpts[location] = inputSets.get(index);
                        desired[location] = targets.get(index);
                    }
                    PatternParallelNode p = new PatternParallelNode(r, inpts,
                            desired, rowIter, learningConstant);
                    futures.add(service.submit(p));
                }
                // Sync and check the status
                for (int futNum = 0; futNum < futures.size(); futNum++) {
                    Future<WeightDelta> future = futures.get(futNum);
                    WeightDelta wd = future.get();

                    // integrate the weight deltas
                    for (int j = 0; j < r.y; j++) {
                        // w' = w + r*i*delta
                        // r is the learning constant
                        // i is the output from the leftward neuron
                        double dw = wd.getOutputDelta(j) / (1.0 * nodes);
                        r.output.changeWeight(j, dw);
                    }
                    // now we do the same for the internal nodes
                    for (int leftCol = r.x - 2; leftCol >= 0; leftCol--) {
                        int rightCol = leftCol + 1;
                        for (int leftRow = 0; leftRow < r.y; leftRow++) {
                            for (int rightRow = 0; rightRow < r.y; rightRow++) {
                                // w' = w + r*i*delta
                                // r is the learning constant
                                // i is the output from the leftward neuron
                                double dw = wd.getInnerDelta(rightCol,
                                        rightRow, leftRow) / (1.0 * nodes);
                                r.neurons[rightCol][rightRow].changeWeight(
                                        leftRow, dw);
                            }
                        }
                    }
                }
                score = 0;
                for (int lcv = 0; lcv < inputSets.size(); lcv++) {
                    r.setInputs(inputSets.get(lcv));
                    score += Math.pow((targets.get(lcv) - r.getOutput()), 2);
                }
                score *= -1.0;
                score = score / (1.0 * inputSets.size());
                if (i % 100 == 0) {
                    int diffCounter = 0;
                    int diffCounter2 = 0;
                    double diffCutoff = .1;
                    double diffCutoff2 = .05;
                    if (bestCheck > -1.0 * score) {
                        RectNetFixed.saveNet(saveFile, r);
                        if (testing) {
                            int idx = saveFile.replaceAll("\\\\", "/").lastIndexOf(
                                    "/");
                            int idx2 = saveFile.lastIndexOf(
                                            ".");
                            testScore = RectNetFixed.testNet(
                                    saveFile.substring(0, idx + 1)
                                            + "OneThird.augtrain", r, verbose);
                            if (testScore < bestTestCheck) {
                                RectNetFixed.saveNet(saveFile.substring(0, idx2)
                                        + "Test.augsave", r);
                                bestTestCheck = testScore;
                            }
                        }
                        bestCheck = -1.0 * score;
                    }
                    for (int lcv = 0; lcv < inputSets.size(); lcv++) {
                        r.setInputs(inputSets.get(lcv));
                        if (Math.abs(targets.get(lcv) - r.getOutput()) > diffCutoff) {
                            diffCounter++;
                        }
                        if (Math.abs(targets.get(lcv) - r.getOutput()) > diffCutoff2) {
                            diffCounter2++;
                        }
                    }
                    System.out.println(i + " rounds trained.");
                    System.out.println("Current score: " + -1.0 * score);
                    System.out.println("Min Score=" + -1.0 * maxScore);
                    if (testing) {
                        System.out.println("Current Test Score=" + testScore);
                        System.out.println("Min Test Score=" + bestTestCheck);
                    }
                    System.out.println("Score change=" + (lastScore + score));
                    System.out.println("Inputs Over " + diffCutoff + "="
                            + diffCounter + " of " + inputSets.size());
                    System.out.println("Inputs Over " + diffCutoff2 + "="
                            + diffCounter2 + " of " + inputSets.size());
                    double diff = 0;
                    for (int lcv = 0; lcv < inputSets.size(); lcv++) {
                        r.setInputs(inputSets.get(lcv));
                        diff += r.getOutput() - targets.get(lcv);
                    }
                    System.out.println("AvgDiff=" + diff
                            / (1.0 * inputSets.size()));
                    System.out.println("Current learning constant: "
                            + learningConstant);
                    System.out.println("Time elapsed (s): "
                            + (System.currentTimeMillis() - start) / 1000.0);
                    System.out.println("");
                }
                lastScore = -1.0 * score;
                if (score > -1.0 * cutoff) {
                    brokeAtPerfCutoff = true;
                    break;
                }
                if (score > maxScore) {
                    maxScore = score;
                }
            }
        } finally {
            service.shutdown();
        }
        if (verbose) {
            // Information about performance and training.
            if (brokeAtPerfCutoff) {
                System.out.println("Performance cutoff hit.");
            } else {
                System.out.println("Training round limit reached.");
            }
            System.out.println("Rounds trained: " + i);
            System.out.println("Final score of " + -1 * score);
            System.out.println("Time elapsed (ms): "
                    + ((System.currentTimeMillis() - start)));
            // Results
            System.out.println("-------------------------");
            // System.out.println("Test Results: ");
            for (int lcv = 0; lcv < inputSets.size(); lcv++) {
                r.setInputs(inputSets.get(lcv));
                // System.out.println("Input " + lcv);
                // System.out.println("\tTarget: " + targets.get(lcv));
                // System.out.println("\tActual: " + r.getOutput());
            }
            // System.out.println("-------------------------");
        }
        return r;
    }

    /**
     * Load a neural network from a .augsave file
     *
     * @author TheConnMan
     * @param fileName
     *            File path to an .augsave file containing a neural network
     * @return Neural network from the .augsave file
     */
    public static PatternParallelRectNet loadNet(String fileName) {
        boolean valid = Net.validateAUGs(fileName);
        if (!valid) {
            System.err.println("File not valid format.");
            throw new RuntimeException("File not valid format");
        }
        // Now we need to pull information out of the augsave file.
        Charset charset = Charset.forName("US-ASCII");
        Path file = Paths.get(fileName);
        String line = null;
        int lineNumber = 1;
        String[] lineSplit;
        String[] edges;
        int side = 0;
        int depth = 0;
        int curCol = 0;
        int curRow = 0;
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            line = reader.readLine();
            try {
                lineSplit = line.split(" ");
                String[] size = lineSplit[1].split(",");
                side = Integer.valueOf(size[1]);
                depth = Integer.valueOf(size[0]);
            } catch (Exception e) {
                System.err.println("Loading failed at line: " + lineNumber);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            throw new RuntimeException("Failed to load file");
        }
        PatternParallelRectNet net = new PatternParallelRectNet(depth, side);
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            while ((line = reader.readLine()) != null) {
                try {
                    lineSplit = line.split(" ");
                    switch (lineNumber) {
                    case 1:
                        break;
                    case 2:
                        String outputs[] = lineSplit[1].split(",");
                        for (int edgeNum = 0; edgeNum < outputs.length; edgeNum++) {
                            net.output.setWeight(edgeNum,
                                    Double.parseDouble(outputs[edgeNum]));
                        }
                        break;
                    default:
                        curCol = Integer.valueOf(lineSplit[0]);
                        curRow = (lineNumber - 3) % side;
                        edges = lineSplit[1].split(",");
                        for (int edgeNum = 0; edgeNum < edges.length; edgeNum++) {
                            net.neurons[curCol][curRow].setWeight(edgeNum,
                                    Double.parseDouble(edges[edgeNum]));
                        }
                        break;
                    }
                    lineNumber++;
                } catch (Exception e) {
                    System.err.println("Loading failed at line: " + lineNumber);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            throw new RuntimeException("Failed to load file");
        }
        return net;
    }

    public static void main(String[] args) {
        // String prefix =
        // "C:\\Users\\Stephen\\workspace\\AugurWorks\\Core\\java\\nets\\test_files\\";
        String prefix = "/root/Core/java/nets/test_files/";
        // String prefix = "C:\\Users\\TheConnMan\\workspace\\Core\\java\\nets\\test_files\\";
        String trainingFile = prefix + "TwoThirds.augtrain";
        String testFile = prefix + "OneThird.augtrain";
        String savedFile = prefix + "TwoThirdsTrained.augsave";
        PatternParallelRectNet r;
        try {
            r = PatternParallelRectNet.trainFile(trainingFile, 4, false, savedFile, true);
            RectNetFixed.testNet(testFile, r, true);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*r = PatternParallelRectNet.loadNet(savedFile);
        RectNetFixed.testNet(testFile, r, true);*/


        System.exit(0);
    }
}
