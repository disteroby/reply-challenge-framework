package it.tt.challenge.example;

import it.tt.challenge.core.ChallengeResult;
import it.tt.challenge.core.ChallengeSolver;
import it.tt.challenge.core.progression.ChallengeOracle;
import it.tt.challenge.core.progression.ChallengeProgressionStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Challenge2023Solver extends ChallengeSolver<Challenge2023DataModel> {

    @Override
    public ChallengeSolver<Challenge2023DataModel> fromDataModel(Challenge2023DataModel challengeDataModel, ChallengeProgressionStrategy progression) {
        Challenge2023Solver challengeSolver = new Challenge2023Solver();
        challengeSolver.model = challengeDataModel;
        challengeSolver.progression = progression;
        return challengeSolver;
    }

    @Override
    protected List<List<String>> solve(ChallengeResult bestResult, ChallengeOracle oracle) {
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

        return result;
    }

    @Override
    protected long computeScore(List<List<String>> result) {
        return 0;
    }
}