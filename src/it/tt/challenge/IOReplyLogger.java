package it.tt.challenge;

import java.util.*;

public class IOReplyLogger {
    public static <T> void printMatrix(T[][] matrix) {
        for (T[] row : matrix) {
            for (T t : row) {
                System.out.print(t + " ");
            }
            System.out.println();
        }
    }

    public static <T> void printMatrix(List<List<T>> matrix) {
        for (List<T> row : matrix) {
            for (T t : row) {
                System.out.print(t + " ");
            }
            System.out.println();
        }
    }

    public static void printListIterationHeader(int trialIdx, Date dateStart) {
        String label = "Trial #" + trialIdx + " - " + dateStart;

        // Upper row
        System.out.print("╔");
        for (int i = 0; i < label.length()+2; i++) {
            System.out.print("═");
        }
        System.out.println("╗");

        // Inner row
        System.out.println("║ Trial #" + trialIdx + " - " + dateStart + " ║");

        // Lower row
        System.out.print("╚");
        for (int i = 0; i < label.length()+2; i++) {
            System.out.print("═");
        }
        System.out.println("╝");
    }

    public static List<Integer> printTableHeader(List<DataPrintableValue> fields) {
        List<Integer> finalLengths = new ArrayList<>();
        for (DataPrintableValue field : fields) {
            finalLengths.add(Math.max(field.minLength, field.value.length()) + 2); // +2 is for 'vertical bars'
        }

        // Upper row
        for (int i = 0; i < finalLengths.size(); i++) {
            int length = finalLengths.get(i) + 2; // +2 is for 'spaces'

            if (i == 0) {
                System.out.print("╔");
            } else {
                System.out.print("╦");
            }

            for (int j = 1; j < length - 1; j++) {
                System.out.print("═");
            }

            if (i == finalLengths.size() - 1) {
                System.out.print("╗");
            }
        }
        System.out.println();

        // Inner row
        for (int i = 0; i < finalLengths.size(); i++) {
            int length = finalLengths.get(i);

            System.out.print("║");

            System.out.print(" " + fields.get(i).value);

            for (int j = 0; j < length - fields.get(i).value.length() - 1; j++) {
                System.out.print(" ");
            }

            if (i == finalLengths.size() - 1) {
                System.out.print("║");
            }
        }
        System.out.println();

        // Lower row
        for (int i = 0; i < finalLengths.size(); i++) {
            int length = finalLengths.get(i) + 2;

            if (i == 0) {
                System.out.print("╠");
            } else {
                System.out.print("╬");
            }

            for (int j = 1; j < length - 1; j++) {
                System.out.print("═");
            }

            if (i == finalLengths.size() - 1) {
                System.out.print("╣");
            }
        }
        System.out.println();

        return finalLengths;
    }

    public static void printTableFooter(List<DataPrintableValue> fields, List<Integer> headerLengths) {
        List<Integer> finalLengths = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            DataPrintableValue field = fields.get(i);
            int headerLength = headerLengths != null ? headerLengths.get(i) - 2 : 0;
            finalLengths.add(Math.max(Math.max(field.minLength, headerLength), field.value.length()) + 2); // +2 is for 'vertical bars'
        }

        // Upper row
        for (int i = 0; i < finalLengths.size(); i++) {
            int length = finalLengths.get(i) + 2; // +2 is for 'spaces'

            if (i == 0) {
                System.out.print("╠");
            } else {
                System.out.print("╬");
            }

            for (int j = 1; j < length - 1; j++) {
                System.out.print("═");
            }

            if (i == finalLengths.size() - 1) {
                System.out.print("╣");
            }
        }
        System.out.println();

        // Inner row
        for (int i = 0; i < finalLengths.size(); i++) {
            int length = finalLengths.get(i);

            System.out.print("║");

            for (int j = 0; j < length - fields.get(i).value.length() - 1; j++) {
                System.out.print(" ");
            }

            System.out.print(fields.get(i).value + " ");

            if (i == finalLengths.size() - 1) {
                System.out.print("║");
            }
        }
        System.out.println();

        // Lower row
        for (int i = 0; i < finalLengths.size(); i++) {
            int length = finalLengths.get(i) + 2;

            if (i == 0) {
                System.out.print("╚");
            } else {
                System.out.print("╩");
            }

            for (int j = 1; j < length - 1; j++) {
                System.out.print("═");
            }

            if (i == finalLengths.size() - 1) {
                System.out.print("╝");
            }
        }
        System.out.println();
    }

    public static void printPartialResultAsTable(List<DataPrintableValue> values, List<Integer> headerLengths) {

        for (int i = 0; i < values.size(); i++) {
            if (i == 0) {
                System.out.print("║ ");
            }

            Integer minLength = values.get(i).minLength();
            String value = values.get(i).value();

            int minHeaderLength = headerLengths != null ? headerLengths.get(i) - 2 : 0;
            int finalLength = Math.max(Math.max(minLength, minHeaderLength), value.length());

            for (int j = 0; j < finalLength - value.length(); j++) {
                System.out.print(" ");
            }

            System.out.print(value);
            System.out.print(" ║ ");
        }

        System.out.println();
    }

    public static void printPartialResultAsList(List<DataPrintableValue> values, List<DataPrintableValue> headers) {
        for (int i = 0; i < values.size(); i++) {
            DataPrintableValue value = values.get(i);
            System.out.print("\t");
            if (i < values.size() - 1) {
                System.out.print("╠");
            } else {
                System.out.print("╚");
            }
            System.out.println("══> " + headers.get(i).value() + ": " + value.value());
        }
        System.out.println();
    }

    public record DataPrintableValue(String value, Integer minLength) {
        public DataPrintableValue(String value) {
            this(value, value.length());
        }
    }
}