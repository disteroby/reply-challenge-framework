public abstract class ChallengeSolver {

    protected final ChallengeDataModel model;
    private final int numberOfTries;

    protected ChallengeSolver(ChallengeDataModel model) {
        this(model, 1);
    }

    protected ChallengeSolver(ChallengeDataModel model, int numberOfTries) {
        this.model = model;
        this.numberOfTries = numberOfTries;
    }

    public ChallengeResult run() {
        ChallengeResult bestResult = null;

        for (int i = 0; i < numberOfTries; i++) {
            String[][] result = solve();
            long score = computeScore(result);

            if (bestResult == null || score > bestResult.score()) {
                bestResult = new ChallengeResult(score, result);
            }
        }

        return bestResult;
    }

    protected abstract String[][] solve();

    public abstract long computeScore(String[][] result);
}
