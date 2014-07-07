package com.augurworks.alfred;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Simple rectangular neural network.
 *
 * @author saf
 *
 */
public class RectNetFixed extends Net {

    // Inputs to network
    protected InputImpl[] inputs;
    // Every neuron with the same i is in the
    // same "layer". Indexed as [col][row].
    protected FixedNeuron[][] neurons;
    // X is depth of network
    protected int x;
    // Y is height of network (number of inputs)
    protected int y;
    // There's only one final output neuron
    // since this is built to make booleans.
    protected FixedNeuron output;
    // Prints debug output when true.
    protected boolean verbose = false;

    /**
     * Constructs a new RectNet with 10 inputs and 5 layers of network.
     */
    public RectNetFixed() {
        this.x = 5;
        this.y = 10;
        init();
    }

    /**
     * Constructs a new RectNet with given depth and number of inputs.
     *
     * @param depth
     *            number of layers in the network
     * @param numInputs
     *            number of inputs to the network
     */
    public RectNetFixed(int depth, int numInputs) {
        if (depth < 1 || numInputs < 1) {
            throw new RuntimeException("Depth and numinputs must be >= 1");
        }
        this.x = depth;
        this.y = numInputs;
        init();
    }

    /**
     * Constructs a new RectNet with given depth and number of inputs. Sets the
     * verbose boolean as given.
     *
     * @param depth
     *            number of layers in the network
     * @param numInputs
     *            number of inputs to the network
     * @param verb
     *            true when RectNet displays debug output.
     */
    public RectNetFixed(int depth, int numInputs, boolean verb) {
        if (depth < 1 || numInputs < 1) {
            throw new RuntimeException("Depth and numinputs must be >= 1");
        }
        this.x = depth;
        this.y = numInputs;
        this.verbose = verb;
        init();
    }

    /**
     * Gets the weight between two neurons. Only works for internal layers (not
     * the output neuron layer).
     *
     * @param leftCol
     *            column number of neuron to left of connection
     * @param leftRow
     *            row number of neuron to left of connection
     * @param rightCol
     *            column number of neuron to right of connection
     * @param rightRow
     *            row number of neuron to right of connection
     * @return weight from right neuron to left neuron.
     */
    public double getWeight(int leftCol, int leftRow, int rightCol, int rightRow) {
        assert (leftCol >= 0);
        assert (leftRow >= 0);
        assert (rightCol >= 0);
        assert (rightRow >= 0);
        assert (leftCol < this.y);
        assert (rightCol < this.y);
        assert (leftRow < this.x);
        assert (rightRow < this.x);
        return this.neurons[rightCol][rightRow].getWeight(leftRow);
    }

    /**
     * Returns the width of this net
     *
     * @return the width of this net
     */
    public int getX() {
        return x;
    }

    /**
     * returns the height of this net
     *
     * @return the height of this net
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the weight from the output neuron to an input specified by the
     * given row.
     *
     * @param leftRow
     *            the column containing the neuron to the left of the output
     *            neuron.
     * @return the weight from the output neuron to the neuron in leftRow
     */
    public double getOutputNeuronWeight(int leftRow) {
        assert (leftRow >= 0);
        assert (leftRow < this.y);
        return this.output.getWeight(leftRow);
    }

    /**
     * Sets the weight from the output neuron to an input specified by the given
     * row
     *
     * @param leftRow
     *            the row containing the neuron to the left of the output
     *            neuron.
     * @param w
     *            the new weight from the output neuron to the neuron at leftRow
     */
    public void setOutputNeuronWeight(int leftRow, double w) {
        assert (leftRow >= 0);
        assert (leftRow < this.y);
        this.output.setWeight(leftRow, w);
    }

    /**
     * Sets the weight between two neurons to the given value w. Only works for
     * internal layers (not the output neuron layer).
     *
     * @param leftCol
     *            column number of neuron to left of connection
     * @param leftRow
     *            row number of neuron to left of connections
     * @param rightCol
     *            column number of neuron to right of connection
     * @param rightRow
     *            row number of neuron to right of connection
     * @param w
     *            weight to set on connection.
     */
    public void setWeight(int leftCol, int leftRow, int rightCol, int rightRow,
            double w) {
        assert (leftCol >= 0);
        assert (leftRow >= 0);
        // right column should be >= 1 because the weights from first row to
        // inputs should never be changed
        assert (rightCol >= 1);
        assert (rightCol - leftCol == 1);
        assert (rightRow >= 0);
        assert (leftCol < this.y);
        assert (rightCol < this.y);
        assert (leftRow < this.x);
        assert (rightRow < this.x);
        this.neurons[rightCol][rightRow].setWeight(leftRow, w);
    }

    /**
     * Initializes the RectNet by: 1) creating neurons and inputs as necessary
     * 2) connecting neurons to the inputs 3) connecting neurons to each other
     * 4) connecting neurons to the output
     *
     * Initial weights are specified by initNum(), allowing random initial
     * weights, or some other set.
     */
    private void init() {
        // Initialize arrays to blank neurons and inputs.
        this.inputs = new InputImpl[y];
        this.neurons = new FixedNeuron[x][y];
        this.output = new FixedNeuron(this.y);
        // Name the neurons for possible debug. This is not a critical
        // step.
        output.setName("output");
        for (int j = 0; j < this.y; j++) {
            this.inputs[j] = new InputImpl();
            // initialize the first row
            this.neurons[0][j] = new FixedNeuron(1);
            this.neurons[0][j].setName("(" + 0 + "," + j + ")");
            for (int i = 1; i < this.x; i++) {
                this.neurons[i][j] = new FixedNeuron(this.y);
                this.neurons[i][j].setName("(" + i + "," + j + ")");
            }
        }
        // Make connections between neurons and inputs.
        for (int j = 0; j < this.y; j++) {
            this.neurons[0][j].addInput(this.inputs[j], 1.0);
        }
        // Make connections between neurons and neurons.
        for (int leftCol = 0; leftCol < this.x - 1; leftCol++) {
            int rightCol = leftCol + 1;
            for (int leftRow = 0; leftRow < this.y; leftRow++) {
                for (int rightRow = 0; rightRow < this.y; rightRow++) {
                    this.neurons[rightCol][rightRow].addInput(
                            this.neurons[leftCol][leftRow], initNum());
                }
            }
        }
        // Make connections between output and neurons.
        for (int j = 0; j < this.y; j++) {
            this.output.addInput(this.neurons[this.x - 1][j], initNum());
        }
    }

    /**
     * Allows network weight initialization to be changed in one location.
     *
     * @return a double that can be used to initialize weights between network
     *         connections.
     */
    private double initNum() {
        return (Math.random() - .5) * 1.0;
    }

    /**
     * Sets the inputs of this network to the values given. Length of inpts must
     * be equal to the "height" of the network.
     *
     * @param inpts
     *            array of double to set as network inputs.
     */
    public void setInputs(double[] inpts) {
        assert (inpts.length == this.y);
        for (int j = 0; j < this.y; j++) {
            this.inputs[j].setValue(inpts[j]);
        }
    }

    /**
     * Returns the output value from this network run.
     */
    @Override
    public double getOutput() {
        double[] outs = new double[this.y];
        double[] ins = new double[this.y];
        for (int j = 0; j < this.y; j++) {
            // require recursion (depth = 0) here.
            ins[j] = this.neurons[0][j].getOutput();
        }
        // indexing must start at 1, because we've already computed
        // the output from the 0th row in the previous 3 lines.
        for (int i = 1; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                outs[j] = this.neurons[i][j].getOutput(ins);
            }
            ins = outs;
            outs = new double[this.y];
        }
        double d = this.output.getOutput(ins);
        return d;
    }

    /**
     * Trains the network on a given input with a given output the number of
     * times specified by iterations. Trains via a backpropagation algorithm.
     *
     * @param inpts
     *            input values for the network.
     * @param desired
     *            what the result of the network should be.
     * @param learningConstant
     *            the "rate" at which the network learns
     * @param iterations
     *            number of times to train the network.
     */
    public void train(double[] inpts, double desired, int iterations,
            double learningConstant) {
        assert (iterations > 0);
        for (int lcv = 0; lcv < iterations; lcv++) {
            // Set the inputs
            this.setInputs(inpts);
            // Compute the last node error
            double deltaF = this.outputError(desired);
            if (verbose) {
                System.out.println("DeltaF: " + deltaF);
            }
            // For each interior node, compute the weighted error
            // deltas are of the form
            // delta[col][row]
            double[][] deltas = new double[this.x + 1][this.y];
            // spoof the rightmost deltas
            for (int j = 0; j < y; j++) {
                deltas[this.x][j] = deltaF;
            }
            int leftCol = 0;
            int leftRow = 0;
            int rightCol = 0;
            int rightRow = 0;
            for (leftCol = this.x - 1; leftCol >= 0; leftCol--) {
                rightCol = leftCol + 1;
                for (leftRow = 0; leftRow < this.y; leftRow++) {
                    double lastOutput = this.neurons[leftCol][leftRow]
                            .getLastOutput();
                    // since we're using alpha = 3 in the neurons
                    double delta = 3 * lastOutput * (1 - lastOutput);
                    double summedRightWeightDelta = 0;
                    for (rightRow = 0; rightRow < this.y; rightRow++) {
                        if (rightCol == this.x) {
                            summedRightWeightDelta += this.output
                                    .getWeight(leftRow) * deltaF;
                            // without the break, we were adding too many of the
                            // contributions of the output node when computing
                            // the deltas value for the layer immediately left
                            // of it.
                            break;
                        } else {
                            // summing w * delta
                            summedRightWeightDelta += getWeight(leftCol,
                                    leftRow, rightCol, rightRow)
                                    * deltas[rightCol][rightRow];
                        }
                    }
                    deltas[leftCol][leftRow] = delta * summedRightWeightDelta;
                    if (verbose) {
                        System.out.println("leftCol: " + leftCol
                                + ", leftRow: " + leftRow + ", lo*(1-lo): "
                                + delta);
                        System.out.println("leftCol: " + leftCol
                                + ", leftRow: " + leftRow + ", srwd: "
                                + summedRightWeightDelta);
                        System.out.println("leftCol: " + leftCol
                                + ", leftRow: " + leftRow + ", delta: "
                                + deltas[leftCol][leftRow]);
                    }
                }
            }
            // now that we have the deltas, we can change the weights
            // again, we special case the last neuron
            for (int j = 0; j < this.y; j++) {
                // w' = w + r*i*delta
                // r is the learning constant
                // i is the output from the leftward neuron
                double dw = learningConstant
                        * this.neurons[this.x - 1][j].getLastOutput() * deltaF;
                this.output.changeWeight(j, dw);
            }
            // now we do the same for the internal nodes
            for (leftCol = this.x - 2; leftCol >= 0; leftCol--) {
                rightCol = leftCol + 1;
                for (leftRow = 0; leftRow < this.y; leftRow++) {
                    for (rightRow = 0; rightRow < this.y; rightRow++) {
                        // w' = w + r*i*delta
                        // r is the learning constant
                        // i is the output from the leftward neuron
                        double dw = learningConstant
                                * this.neurons[leftCol][leftRow]
                                        .getLastOutput()
                                * deltas[rightCol][rightRow];
                        this.neurons[rightCol][rightRow].changeWeight(leftRow,
                                dw);
                        if (verbose) {
                            System.out.println(leftCol + "," + leftRow + "->"
                                    + rightCol + "," + rightRow);
                            System.out.println(this.neurons[rightCol][rightRow]
                                    .getWeight(leftRow));
                        }
                    }
                }
            }
        }
    }

    /**
     * Computes the error in the network, assuming that the proper inputs have
     * been set before this method is called.
     *
     * @param desired
     *            the desired output.
     * @return error using equation (output*(1-output)*(desired-output))
     */
    protected double outputError(double desired) {
        this.getOutput();
        // since we're using alpha = 3 in the neurons
        return 3 * this.output.getLastOutput()
                * (1 - this.output.getLastOutput())
                * (desired - this.output.getLastOutput());
    }

    /**
     * Train a neural network from a .augtrain training file
     *
     * @param fileName
     *            File path to .augtrain training file
     * @param verbose
     *            Flag to display debugging text or not
     * @return The trained neural network
     */
    public static RectNetFixed trainFile(String fileName, boolean verbose,
            String saveFile, boolean testing) {
        boolean valid = Net.validateAUGt(fileName);
        if (!valid) {
            System.err.println("File not valid format.");
            throw new IllegalArgumentException("File not valid");
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
            throw new IllegalArgumentException("IOException in parsing file");
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
        // Actually do the training part
        long start = System.currentTimeMillis();
        RectNetFixed r = new RectNetFixed(depth, side);
        double maxScore = Double.NEGATIVE_INFINITY;
        int displayRounds = 1000;
        double score = 0;
        double testScore = 0;
        double lastScore = Double.POSITIVE_INFINITY;
        double bestCheck = Double.POSITIVE_INFINITY;
        double bestTestCheck = Double.POSITIVE_INFINITY;
        int i = 0;
        boolean brokeAtLocalMax = false;
        boolean brokeAtPerfCutoff = false;
        for (i = 0; i < fileIter; i++) {
            // long roundTime = System.currentTimeMillis();
            for (int lcv = 0; lcv < inputSets.size(); lcv++) {
                r.train(inputSets.get(lcv), targets.get(lcv), rowIter,
                        learningConstant);
            }
            score = 0;
            for (int lcv = 0; lcv < inputSets.size(); lcv++) {
                r.setInputs(inputSets.get(lcv));
                score += Math.pow((targets.get(lcv) - r.getOutput()), 2);
            }
            score *= -1.0;
            score = score / (1.0 * inputSets.size());
            if (i % displayRounds == 0) {
                int diffCounter = 0;
                int diffCounter2 = 0;
                double diffCutoff = .1;
                double diffCutoff2 = .05;
                if (bestCheck > -1.0 * score) {
                    RectNetFixed.saveNet(saveFile, r);
                    bestCheck = -1.0 * score;
                }
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
                System.out.println("Score change per round=" + (lastScore + score)/displayRounds);
                System.out.println("Inputs Over " + diffCutoff + "="
                        + diffCounter + " of " + inputSets.size());
                System.out.println("Inputs Over " + diffCutoff2 + "="
                        + diffCounter2 + " of " + inputSets.size());
                double diff = 0;
                for (int lcv = 0; lcv < inputSets.size(); lcv++) {
                    r.setInputs(inputSets.get(lcv));
                    diff += r.getOutput() - targets.get(lcv);
                }
                System.out
                        .println("AvgDiff=" + diff / (1.0 * inputSets.size()));
                System.out.println("Current learning constant: "
                        + learningConstant);
                System.out.println("Time elapsed (s): "
                        + (System.currentTimeMillis() - start) / 1000.0);
                System.out.println("");
                lastScore = -1.0 * score;
            }
            if (score > -1.0 * cutoff) {
                brokeAtPerfCutoff = true;
                break;
            }
            if (score > maxScore) {
                maxScore = score;
            } else if (i < minTrainingRounds) {
                continue;
            } else {
                brokeAtLocalMax = true;
                break;
            }
        }
        if (verbose) {
            // Information about performance and training.
            if (brokeAtLocalMax) {
                System.out.println("Local max hit.");
            } else if (brokeAtPerfCutoff) {
                System.out.println("Performance cutoff hit.");
            } else {
                System.out.println("Training round limit reached.");
            }
            System.out.println("Rounds trained: " + i);
            System.out.println("Final score of " + -1.0 * score
                    / (1.0 * inputSets.size()));
            System.out.println("Time elapsed (ms): "
                    + ((System.currentTimeMillis() - start)));
            // Results
            System.out.println("-------------------------");
            System.out.println("Test Results: ");
            for (int lcv = 0; lcv < Math.min(inputSets.size(), 10); lcv++) {
                r.setInputs(inputSets.get(lcv));
                System.out.println("Input " + lcv);
                System.out.println("\tTarget: " + targets.get(lcv));
                System.out.println("\tActual: " + r.getOutput());
            }
            System.out.println("-------------------------");
        }
        if (brokeAtLocalMax) {
            System.out.println("Retraining");
            r = RectNetFixed.trainFile(fileName, verbose, saveFile, testing);
        }
        return r;
    }

    /**
     * Input a filename and a neural network to save the neural network as a
     * .augsave file
     *
     * @author TheConnMan
     * @param fileName
     *            Filepath ending in .augsave where the network will be saved
     * @param net
     *            Neural net to be saved
     */
    public static void saveNet(String fileName, RectNetFixed net) {
        try {
            if (!(fileName.toLowerCase().endsWith(".augsave"))) {
                System.err
                        .println("Output file name to save to should end in .augsave");
                return;
            }
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            out.println("net " + Integer.toString(net.getX()) + ","
                    + Integer.toString(net.getY()));
            String line = "O ";
            for (int j = 0; j < net.getY(); j++) {
                line += net.getOutputNeuronWeight(j) + ",";
            }
            out.println(line.substring(0, line.length() - 1));
            for (int leftCol = 0; leftCol < net.getX() - 1; leftCol++) {
                int rightCol = leftCol + 1;
                for (int rightRow = 0; rightRow < net.getY(); rightRow++) {
                    line = rightCol + " ";
                    for (int leftRow = 0; leftRow < net.getY(); leftRow++) {
                        line += net.neurons[rightCol][rightRow]
                                .getWeight(leftRow) + ",";
                    }
                    out.println(line.substring(0, line.length() - 1));
                }
            }
            out.close();
        } catch (IOException e) {
            System.err.println("Error occured opening file to saveNet");
            throw new RuntimeException("Could not open file");
        }
    }

    /**
     * Load a neural network from a .augsave file
     *
     * @author TheConnMan
     * @param fileName
     *            File path to an .augsave file containing a neural network
     * @return Neural network from the .augsave file
     */
    public static RectNetFixed loadNet(String fileName) {
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
        RectNetFixed net = new RectNetFixed(depth, side);
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

    /**
     * @param fileName
     * @param r
     */
    public static double testNet(String fileName, RectNetFixed r,
            boolean verbose) {
        boolean valid = Net.validateAUGTest(fileName, r.y);
        if (!valid) {
            System.err.println("File not valid format.");
            throw new RuntimeException("File not valid format");
        }
        // Now we need to pull information out of the augtrain file.
        Charset charset = Charset.forName("US-ASCII");
        Path file = Paths.get(fileName);
        String line = null;
        int lineNumber = 1;
        String[] lineSplit;
        int side = r.y;
        String[] size;
        ArrayList<double[]> inputSets = new ArrayList<double[]>();
        ArrayList<Double> targets = new ArrayList<Double>();
        double[] maxMinNums = new double[4];
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            while ((line = reader.readLine()) != null) {
                try {
                    lineSplit = line.split(" ");
                    switch (lineNumber) {
                    case 1:
                        String[] temp = lineSplit[1].split(",");
                        for (int j = 0; j < 4; j++) {
                            maxMinNums[j] = Double.valueOf(temp[j + 2]);
                        }
                        break;
                    case 2:
                        size = lineSplit[1].split(",");
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
            System.exit(1);
        }
        double score = 0;
        for (int lcv = 0; lcv < inputSets.size(); lcv++) {
            r.setInputs(inputSets.get(lcv));
            score += Math.pow((targets.get(lcv) - r.getOutput()), 2);
            // System.out.println(r.getOutput());
        }
        if (verbose) {
            System.out.println("Final score of " + score
                    / (1.0 * inputSets.size()));
            // Results

            System.out.println("-------------------------");
            System.out.println("Test Results: ");
            System.out.println("Actual, Prediction");
            score = 0;
            double score2 = 0;
            for (int lcv = 0; lcv < inputSets.size(); lcv++) {
                r.setInputs(inputSets.get(lcv));

                /**
                 * System.out.println("Input " + lcv);
                 * System.out.println("\tTarget: " + targets.get(lcv));
                 * System.out.println("\tActual: " + r.getOutput());
                 **/
                double tempTarget = (targets.get(lcv) - maxMinNums[3])
                        * (maxMinNums[0] - maxMinNums[1])
                        / (maxMinNums[2] - maxMinNums[3]) + maxMinNums[1];
                double tempOutput = (r.getOutput() - maxMinNums[3])
                        * (maxMinNums[0] - maxMinNums[1])
                        / (maxMinNums[2] - maxMinNums[3]) + maxMinNums[1];
                System.out.println(tempTarget + "," + tempOutput);
                score += Math.abs(tempTarget - tempOutput);
                score2 += Math.pow(tempTarget - tempOutput, 2);
            }
            score /= (1.0 * inputSets.size());
            score2 /= (1.0 * inputSets.size());
            System.out.println("-------------------------");
            System.out.println("Average error=" + score);
            System.out.println("Average squared error=" + score2);
        }
        return score / (1.0 * inputSets.size());
    }

    /**
     *
     * @param trainingFile
     * @param predFile
     * @param verbose
     * @return
     */
    public static double predictTomorrow(RectNetFixed r, String trainingFile, String predFile,
            boolean verbose, String saveFile) {
        /*
         * boolean valid = Net.validateAUGPred(predFile, r.y); if (!valid) {
         * System.err.println("File not valid format."); System.exit(1); }
         */
        // Now we need to pull information out of the augtrain file.
        Charset charset = Charset.forName("US-ASCII");
        Path file = Paths.get(predFile);
        String line = null;
        int lineNumber = 1;
        String[] lineSplit;
        double maxNum = 1, minNum = 1, mx = 1, mn = 1, today = 0;
        ArrayList<double[]> inputSets = new ArrayList<double[]>();
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            while ((line = reader.readLine()) != null) {
                try {
                    lineSplit = line.split(",");
                    switch (lineNumber) {
                    case 1:
                        mx = Double.valueOf(lineSplit[0]);
                        mn = Double.valueOf(lineSplit[1]);
                        maxNum = Double.valueOf(lineSplit[2]);
                        minNum = Double.valueOf(lineSplit[3]);
                        today = Double.valueOf(lineSplit[4]);
                        break;
                    case 3:
                        boolean valid = Net.validateAUGPred(predFile,
                                lineSplit.length);
                        if (!valid) {
                            System.err.println("File not valid format.");
                            System.exit(1);
                        }
                        double[] input = new double[lineSplit.length];
                        for (int i = 0; i < lineSplit.length; i++) {
                            input[i] = Double.valueOf(lineSplit[i]);
                        }
                        inputSets.add(input);
                        break;
                    default:
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
            System.exit(1);
        }
        r.setInputs(inputSets.get(0));
        double scaledValue = (r.getOutput() - minNum) / (maxNum - minNum)
                * (mx - mn) + mn;
        System.out.println("Today's price is $" + today);
        System.out.println("Tomorrow's price/change predicted to be $" + scaledValue);
        return scaledValue;
    }

    public static void main(String[] args) {
        //What the net was trained for - prediction or twoThirds/oneThird
        boolean predict=false;
        //Which file system - root or local
        boolean root=false;
        //Where the net comes from - training of loading
        boolean train=true;

        String prefix, trainingFile, trainingFile2, predFile, testFile, savedFile;
        if (root) {
            prefix = "/root/Core/java/nets/test_files/";
        } else {
            prefix = "D:\\Users\\TheConnMan\\git\\Core\\java\\nets\\test_files\\";
        }
        trainingFile = prefix + "Train_1_Day.augtrain";
        predFile = prefix + "Pred_1_Day.augpred";
        savedFile = prefix + "TwoThirdsTrained.augsave";
        trainingFile2 = prefix + "TwoThirds.augtrain";
        testFile = prefix + "OneThird.augtrain";
        RectNetFixed r;
        if (train && predict) {
            r = RectNetFixed.trainFile(trainingFile, false, savedFile, false);
        } else if (train && !predict) {
            r = RectNetFixed.trainFile(trainingFile2, false, savedFile, true);
        } else {
            r = RectNetFixed.loadNet(savedFile);
        }
        if (predict) {
            // Predict
            RectNetFixed.predictTomorrow(r, trainingFile, predFile, true, savedFile);
        } else {
            //Test
            RectNetFixed.testNet(testFile, r, true);
        }
        System.exit(0);
    }
}
