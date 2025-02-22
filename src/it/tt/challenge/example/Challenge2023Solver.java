package it.tt.challenge.example;

import it.tt.challenge.core.ChallengeSolver;
import it.tt.challenge.core.progression.ChallengeProgressionStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Challenge2023Solver extends ChallengeSolver<Challenge2023DataModel> {

    @SuppressWarnings("unused")
    public Challenge2023Solver() {
        this(null, null);
    }

    private Challenge2023Solver(Challenge2023DataModel challengeDataModel, ChallengeProgressionStrategy progressionStrategy) {
        super(challengeDataModel, progressionStrategy);
    }

    @Override
    public Challenge2023Solver fromDataModel(Challenge2023DataModel challengeDataModel, ChallengeProgressionStrategy progressionStrategy) {
        return new Challenge2023Solver(challengeDataModel, progressionStrategy);
    }

    @Override
    protected List<List<String>> solve() {

        // Used cached result for performance improving
        if(this.previousResult != null) {
            return this.previousResult.result();
        }

        int rows = model.components.length;
        int cols = model.components[0].length;

        List<List<String>> result = new ArrayList<>();

        // First row
        result.add(Arrays.asList(
                String.valueOf(model.C),
                String.valueOf(model.R),
                String.valueOf(model.S)
        ));

        // Second row
        result.add(model.snakesLength.stream()
                .map(String::valueOf)
                .collect(Collectors.toList()));

        // Components matrix
        for (int i = 0; i < rows; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add((model.components[i][j] != null) ?
                        String.valueOf(model.components[i][j]) :
                        "*");
            }
            result.add(row);
        }

        // Make the thread sleep for 3 seconds (3000 milliseconds) to simulate a heavy computation
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted!");
        }

        return result;
    }

    @Override
    protected long computeScore(List<List<String>> result) {
        return 0;
    }
}