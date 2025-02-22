package it.tt.utils;

import java.util.List;

public class CustomLogger {
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
}