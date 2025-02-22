package it.tt.challenge.core;

import java.util.List;

public abstract class ChallengeSolver<DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>> {

    protected final DATA_MODEL model;
    private final int numberOfTries;

    protected ChallengeSolver(DATA_MODEL model) {
        this(model, 1);
    }

    protected ChallengeSolver(DATA_MODEL model, int numberOfTries) {
        this.model = model;
        this.numberOfTries = numberOfTries;
    }

    public ChallengeResult run() {
        ChallengeResult bestResult = null;

        for (int i = 0; i < numberOfTries; i++) {
            List<List<String>> result = solve();
            long score = computeScore(result);

            if (bestResult == null || score > bestResult.score()) {
                bestResult = new ChallengeResult(score, result);
            }
        }

        return bestResult;
    }

    protected abstract List<List<String>> solve();

    public abstract long computeScore(List<List<String>> result);

    public abstract ChallengeSolver<DATA_MODEL> fromDataModel(DATA_MODEL challengeDataModel);
}
