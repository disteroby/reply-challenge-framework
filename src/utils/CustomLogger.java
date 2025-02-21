package utils;

public class CustomLogger {
    public static <T> void printMatrix(T[][] matrix) {
        for (T[] row : matrix) {
            for (T t : row) {
                System.out.print(t + " ");
            }
            System.out.println();
        }
    }
}