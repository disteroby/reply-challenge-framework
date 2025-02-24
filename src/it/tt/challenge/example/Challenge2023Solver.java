package it.tt.challenge.example;

import it.tt.challenge.ChallengeSolver;
import it.tt.challenge.core.ChallengeSolveData;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Challenge2023Solver extends ChallengeSolver<Challenge2023DataModel> {

    @Override
    protected List<List<String>> solve(ChallengeSolveData<Challenge2023DataModel> solveData) {

        if(solveData.previousResult() != null) {
            // Make the thread sleep for 0.05 seconds (50 milliseconds) to simulate a light computation
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted!");
            }
            return solveData.previousResult().result();
        }

        int rows = solveData.model().components.length;
        int cols = solveData.model().components[0].length;

        List<List<String>> result = new ArrayList<>();

        // First row
        result.add(Arrays.asList(
                String.valueOf(solveData.model().C),
                String.valueOf(solveData.model().R),
                String.valueOf(solveData.model().S)
        ));

        // Second row
        result.add(solveData.model().snakesLength.stream()
                .map(String::valueOf)
                .collect(Collectors.toList()));

        // Components matrix
        for (int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add((solveData.model().components[i][j] != null) ?
                        String.valueOf(solveData.model().components[i][j]) :
                        "*");
            }
            result.add(row);
        }

        // Make the thread sleep for 1.5 seconds (1500 milliseconds) to simulate a heavy computation
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted!");
        }

        return result;
    }

    @Override
    protected long computeScore(List<List<String>> result) {
        return new Random().nextInt(1_000_000); //Mock Computation
    }
}