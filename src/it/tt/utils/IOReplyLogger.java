package it.tt.utils;

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

    public static void printPartialResult(boolean tableMode, int trialIdx, Date dateStart, Date dateEnd, float score, boolean accepted, Map<String, String> params) {
        if (tableMode) {
            printPartialResultAsTable(trialIdx, dateStart, dateEnd, score, accepted, params);
        } else {
            printPartialResultAsRecap(trialIdx, dateStart, dateEnd, score, accepted);
        }
    }

    private static void printPartialResultAsTable(int trialIdx, Date dateStart, Date dateEnd, float score, boolean accepted, Map<String, String> params) {

        List<TableValue> values = new ArrayList<>();
        values.add(new TableValue("#" + trialIdx, 4));
        values.add(new TableValue(getTimeDifference(dateStart, dateEnd), 11));
        values.add(new TableValue(String.valueOf(score), 10));
        values.add(new TableValue(accepted ? "Yes" : "No", 3));
        for (Map.Entry<String, String> param : params.entrySet()) {
            values.add(new TableValue(param.getValue(), param.getKey().length()));
        }

        for (int i = 0; i < values.size(); i++) {
            if(i == 0) {
                System.out.print("║ ");
            }

            Integer minLength = values.get(i).minLength();
            String value = values.get(i).value();

            int finalLength = Math.max(minLength, value.length());

            for (int j = 0; j < finalLength - value.length(); j++) {
                System.out.print(" ");
            }

            System.out.print(value);
            System.out.print(" ║ ");
        }

        System.out.println();
    }

    private static void printPartialResultAsRecap(int trialIdx, Date dateStart, Date dateEnd, float score, boolean accepted) {
        System.out.println("Completed Trial #" + trialIdx);
        System.out.println("\t╠══> Accepted? " + (accepted ? "Yes" : "No"));
        System.out.println("\t╠══> Completion Time - " + dateEnd);
        System.out.println("\t╠══> Trial duration: " + getTimeDifference(dateStart, dateEnd));
        System.out.println("\t╚══> Score: " + score);
        System.out.println();
    }

    public record TableValue(String value, Integer minLength) {

    }

    private static String getTimeDifference(Date dateStart, Date dateEnd) {
        // Calculate the difference in milliseconds
        long diffInMillis = dateEnd.getTime() - dateStart.getTime();

        // Calculate minutes, seconds, and milliseconds
        long minutes = diffInMillis / 60_000;
        long seconds = (diffInMillis % 60_000) / 1_000;
        long milliseconds = diffInMillis % 1_000;

        // Return the formatted result as a string
        return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
    }
}