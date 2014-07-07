package com.augurworks.alfred;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

public abstract class Net {
    /**
     * Returns the output of this Net.
     *
     * @return output of this Net.
     */
    public abstract double getOutput();

    public enum NetType {
        TRAIN("augtrain"),
        SAVE("augsave"),
        PREDICTION("augpred"),
        ;

        private final String suffix;

        private NetType(String suffix) {
            this.suffix = suffix;
        }

        public String getSuffix() {
            return suffix;
        }

        public static NetType fromFile(String inputFile) {
            String extension = FilenameUtils.getExtension(inputFile);
            for (NetType netType : values()) {
                if (extension.equals(netType.getSuffix())) {
                    return netType;
                }
            }
            throw new IllegalArgumentException("Extension " + extension +
                    " not recognized for input file " + inputFile + ".");
        }
    }

    /**
     * Validates that an augtrain file is correctly structured.
     *
     * @param fileName
     *            absolute location of file to validate.
     * @return true when file is valid, else false.
     */
    public static boolean validateAUGt(String fileName) {
        if (!(fileName.toLowerCase().endsWith(".augtrain"))) {
            System.err.println("Training file should end in .augtrain");
            return false;
        }
        Charset charset = Charset.forName("US-ASCII");
        Path file = Paths.get(fileName);
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            int lineNumber = 1;
            String[] lineSplit;
            int n = 0;
            while ((line = reader.readLine()) != null) {
                try {
                    lineSplit = line.split(" ");
                    switch (lineNumber) {
                    case 1:
                        if (!lineSplit[0].equals("net"))
                            throw new RuntimeException();
                        String[] size = lineSplit[1].split(",");
                        if (!(Integer.valueOf(size[0]) > 0))
                            throw new RuntimeException();
                        n = Integer.valueOf(size[0]);
                        if (!(Integer.valueOf(size[1]) > 0))
                            throw new RuntimeException();
                        break;
                    case 2:
                        if (!lineSplit[0].equals("train"))
                            throw new RuntimeException();
                        size = lineSplit[1].split(",");
                        if (!(Integer.valueOf(size[0]) > 0))
                            throw new RuntimeException();
                        if (!(Integer.valueOf(size[1]) > 0))
                            throw new RuntimeException();
                        if (!(Double.valueOf(size[2]) > 0))
                            throw new RuntimeException();
                        if (!(Integer.valueOf(size[3]) > 0))
                            throw new RuntimeException();
                        if (!(Double.valueOf(size[4]) > 0))
                            throw new RuntimeException();
                        if (!(size.length == 5)) {
                            throw new RuntimeException();
                        }
                        break;
                    case 3:
                        if (!lineSplit[0].equals("TITLES"))
                            throw new RuntimeException();
                        size = lineSplit[1].split(",");
                        if (!(size.length == n))
                            throw new RuntimeException();
                        break;
                    default:
                        if (!(Double.valueOf(lineSplit[0]) != null))
                            throw new RuntimeException();
                        size = lineSplit[1].split(",");
                        for (String s : size) {
                            if (Double.valueOf(s).equals(null)) {
                                throw new RuntimeException();
                            }
                        }
                        if (!(size.length == n))
                            throw new RuntimeException();
                        break;
                    }
                    lineNumber++;
                } catch (Exception e) {
                    System.err.println("Validation failed at line: "
                            + lineNumber);
                    return false;
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            return false;
        }
        return true;
    }

    public static boolean validateAUGTest(String fileName, int side) {
        if (!(fileName.toLowerCase().endsWith(".augtrain"))) {
            System.err.println("Training file should end in .augtrain");
            return false;
        }
        Charset charset = Charset.forName("US-ASCII");
        Path file = Paths.get(fileName);
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            int lineNumber = 1;
            String[] lineSplit;
            while ((line = reader.readLine()) != null) {
                try {
                    lineSplit = line.split(" ");
                    switch (lineNumber) {
                    case 1:
                        if (!lineSplit[0].equals("net"))
                            throw new RuntimeException();
                        String[] size = lineSplit[1].split(",");
                        if (!(Integer.valueOf(size[0]) == side))
                            throw new RuntimeException();
                        if (!(Integer.valueOf(size[1]) > 0))
                            throw new RuntimeException();
                        if (!(Double.valueOf(size[2]) > 0))
                            throw new RuntimeException();
                        if (!(Double.valueOf(size[3]) != null))
                            throw new RuntimeException();
                        if (!(Double.valueOf(size[4]) > 0))
                            throw new RuntimeException();
                        if (!(Double.valueOf(size[5]) > 0))
                            throw new RuntimeException();
                        if (!(size.length == 6)) {
                            throw new RuntimeException();
                        }
                        break;
                    case 2:
                        if (!lineSplit[0].equals("train"))
                            throw new RuntimeException();
                        size = lineSplit[1].split(",");
                        if (!(Integer.valueOf(size[0]) > 0))
                            throw new RuntimeException();
                        if (!(Integer.valueOf(size[1]) > 0))
                            throw new RuntimeException();
                        if (!(Double.valueOf(size[2]) > 0))
                            throw new RuntimeException();
                        if (!(Integer.valueOf(size[3]) > 0))
                            throw new RuntimeException();
                        if (!(Double.valueOf(size[4]) > 0))
                            throw new RuntimeException();
                        if (!(size.length == 5)) {
                            throw new RuntimeException();
                        }
                        break;
                    case 3:
                        if (!lineSplit[0].equals("TITLES"))
                            throw new RuntimeException();
                        size = lineSplit[1].split(",");
                        if (!(size.length == side))
                            throw new RuntimeException();
                        break;
                    default:
                        if (!(Double.valueOf(lineSplit[0]) != null))
                            throw new RuntimeException();
                        size = lineSplit[1].split(",");
                        for (String s : size) {
                            if (Double.valueOf(s).equals(null)) {
                                throw new RuntimeException();
                            }
                        }
                        if (!(size.length == side))
                            throw new RuntimeException();
                        break;
                    }
                    lineNumber++;
                } catch (Exception e) {
                    System.err.println("Validation failed at line: "
                            + lineNumber);
                    return false;
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            return false;
        }
        return true;
    }

    /**
     * Validates an .augsave file format
     *
     * @author TheConnMan
     * @param fileName
     *            The filename of the .augsave file to be validated
     * @return True if file is correctly formatted, else false
     */
    public static boolean validateAUGs(String fileName) {
        if (!(fileName.toLowerCase().endsWith(".augsave"))) {
            System.err.println("Training file should end in .augsave");
            return false;
        }
        Charset charset = Charset.forName("US-ASCII");
        Path file = Paths.get(fileName);
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            int lineNumber = 1;
            String[] lineSplit;
            int x = 0;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                try {
                    lineSplit = line.split(" ");
                    switch (lineNumber) {
                    case 1:
                        if (!lineSplit[0].equals("net"))
                            throw new RuntimeException();
                        String[] size = lineSplit[1].split(",");
                        if (!(Integer.valueOf(size[0]) > 0)
                                || !(Integer.valueOf(size[1]) > 0))
                            throw new RuntimeException();
                        y = Integer.valueOf(size[0]);
                        x = Integer.valueOf(size[1]);
                        if (!(size.length == 2)) {
                            throw new RuntimeException();
                        }
                        break;
                    case 2:
                        if (!lineSplit[0].equals("O"))
                            throw new RuntimeException();
                        size = lineSplit[1].split(",");
                        for (String s : size) {
                            if (Double.valueOf(s).equals(null)) {
                                throw new RuntimeException();
                            }
                        }
                        if (!(size.length == x))
                            throw new RuntimeException();
                        break;
                    default:
                        if ((int) ((lineNumber - 3) / x) + 1 != Integer
                                .valueOf(lineSplit[0]))
                            throw new RuntimeException();
                        size = lineSplit[1].split(",");
                        for (String s : size) {
                            if (Double.valueOf(s).equals(null)) {
                                throw new RuntimeException();
                            }
                        }
                        if (!(size.length == x))
                            throw new RuntimeException();
                        break;
                    }
                    lineNumber++;
                } catch (Exception e) {
                    System.err.println("Validation failed at line: "
                            + lineNumber);
                    return false;
                }
            }
            if (lineNumber != x * (y-1) + 3)
                throw new RuntimeException();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            return false;
        }
        return true;
    }

    public static boolean validateAUGPred(String fileName, int size) {
        if (!(fileName.toLowerCase().endsWith(".augpred"))) {
            System.err.println("Training file should end in .augpred");
            return false;
        }
        Charset charset = Charset.forName("US-ASCII");
        Path file = Paths.get(fileName);
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            int lineNumber = 1;
            String[] lineSplit;
            while ((line = reader.readLine()) != null) {
                try {
                    lineSplit = line.split(",");
                    switch (lineNumber) {
                    case 1:
                        if (!(lineSplit.length==5))
                            throw new RuntimeException();
                        for (int i = 0; i < 5; i++) {
                            try {
                                Double.valueOf(lineSplit[i]);
                            } catch (NumberFormatException e) {
                                throw new RuntimeException();
                            }
                            if (Double.valueOf(lineSplit[i])<0)
                                throw new RuntimeException();
                        }
                        if (Double.valueOf(lineSplit[0])<Double.valueOf(lineSplit[1]) || Double.valueOf(lineSplit[2])<Double.valueOf(lineSplit[3]) || Double.valueOf(lineSplit[2])>1)
                            throw new RuntimeException();
                        break;
                    case 2:
                        if (lineSplit.length!=size)
                            throw new RuntimeException();
                        break;
                    default:
                        if (lineSplit.length!=size)
                            throw new RuntimeException();
                        for (int i = 0; i < size; i++) {
                            try {
                                Double.valueOf(lineSplit[i]);
                            } catch (NumberFormatException e) {
                                throw new RuntimeException();
                            }
                            if (Double.valueOf(lineSplit[i])<0)
                                throw new RuntimeException();
                        }
                        break;
                    }
                    lineNumber++;
                } catch (Exception e) {
                    System.err.println("Validation failed at line: "
                            + lineNumber);
                    return false;
                }
            }
            if (lineNumber != 4)
                throw new RuntimeException();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            return false;
        }
        return true;
    }
}
