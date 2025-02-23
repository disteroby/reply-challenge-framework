package it.tt.challenge.core.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IOReplyParser {
    private final String inputFolderPath;
    private final String outputFolderPath;

    private String[][] data;
    private int iteratorIndexI;
    private int iteratorIndexJ;

    public IOReplyParser(String inputFolderPath, String outputFolderPath) {
        this.inputFolderPath = inputFolderPath;
        this.outputFolderPath = outputFolderPath;
        this.iteratorIndexI = 0;
        this.iteratorIndexJ = 0;
    }

    public String[][] readInput(String inputFileName) {
        Path completeInputFilePath = Paths.get(inputFolderPath, inputFileName);
        System.out.println("Parsing file at location: " + completeInputFilePath);

        try {
            this.data = readFileToMatrix(completeInputFilePath);
            return this.data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeOutput(String outputFileName, String[][] data) {
        Path completeOutputFilePath = Paths.get(outputFolderPath, outputFileName);
        System.out.println("Writing file at location: " + completeOutputFilePath);

        try {
            if (!Files.exists(completeOutputFilePath.getParent())) {
                Files.createDirectories(completeOutputFilePath.getParent());
                System.out.println("Output folder created at: " + completeOutputFilePath.getParent());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(completeOutputFilePath.toString()))) {
                for (String[] row : data) {
                    writer.write(String.join(" ", row));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeOutput(String outputFileName, List<List<String>> data) {
        String[][] dataMatrix;

        if (data == null || data.isEmpty()) {
            dataMatrix = new String[0][0];
        } else {
            int rows = data.size();
            int cols = data.get(0).size();
            String[][] array = new String[rows][cols];
            for (int i = 0; i < rows; i++) {
                array[i] = data.get(i).toArray(new String[0]);
            }
            dataMatrix = array;
        }

        writeOutput(outputFileName, dataMatrix);
    }

    private static String[][] readFileToMatrix(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        List<String[]> matrixList = new ArrayList<>();

        for (String line : lines) {
            String[] row = line.trim().split("\\s+");
            matrixList.add(row);
        }

        String[][] matrix = new String[matrixList.size()][];
        return matrixList.toArray(matrix);
    }

    private void incrementIndexes() {
        if (iteratorIndexI >= data.length) {
            throw new RuntimeException("No more data to parse!");
        }

        iteratorIndexJ++;

        if (iteratorIndexJ >= data[iteratorIndexI].length) {
            iteratorIndexJ = 0;
            iteratorIndexI++;
        }
    }

    @SuppressWarnings("unused")
    public String parseString() {
        String value = data[iteratorIndexI][iteratorIndexJ];
        incrementIndexes();
        return value;
    }

    @SuppressWarnings("unused")
    public int parseInt() {
        return Integer.parseInt(parseString());
    }

    @SuppressWarnings("unused")
    public Integer parseIntOrNull() {
        try {
            return parseInt();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @SuppressWarnings("unused")
    public long parseLong() {
        return Long.parseLong(parseString());
    }

    @SuppressWarnings("unused")
    public Long parseLongOrNull() {
        try {
            return parseLong();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @SuppressWarnings("unused")
    public float parseFloat() {
        return Float.parseFloat(parseString());
    }

    @SuppressWarnings("unused")
    public Float parseFloatOrNull() {
        try {
            return parseFloat();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @SuppressWarnings("unused")
    public double parseDouble() {
        return Double.parseDouble(parseString());
    }

    @SuppressWarnings("unused")
    public Double parseDoubleOrNull() {
        try {
            return parseDouble();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @SuppressWarnings("unused")
    public char parseChar() {
        return parseString().charAt(0);
    }

    @SuppressWarnings("unused")
    public Character parseCharOrNull() {
        String parsedString = parseString();
        if(parsedString.isEmpty()) {
            return null;
        }
        return parsedString.charAt(0);
    }
}