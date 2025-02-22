package it.tt.challenge.core;

import it.tt.challenge.core.progression.ChallengeOracle;
import it.tt.challenge.core.progression.ChallengeProgressionStrategy;

import java.util.Date;
import java.util.List;

public abstract class ChallengeSolver<DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>> {

    /**
     * The structured data model representing the input for the challenge.
     * This object holds the data that will be processed and used during the challenge.
     */
    protected DATA_MODEL model;

    /**
     * The oracle that allows making random choices during the challenge.
     * It helps in determining progression and decision-making based on a given strategy.
     */
    protected ChallengeOracle oracle;

    /**
     * The best result achieved during the current execution.
     * This is updated each time a better solution is found during a trial.
     */
    protected ChallengeResult currentBestResult;

    /**
     * The result of the previous computation or trial.
     * This result is stored for comparison with the current result.
     */
    protected ChallengeResult previousResult;

    public ChallengeSolver() {
        this(null, null);
    }

    public ChallengeSolver(DATA_MODEL model, ChallengeProgressionStrategy progressionStrategy) {
        this.model = model;
        this.oracle = new ChallengeOracle(progressionStrategy);
        this.currentBestResult = null;
        this.previousResult = null;
    }

    public abstract ChallengeSolver<DATA_MODEL> fromDataModel(DATA_MODEL challengeDataModel, ChallengeProgressionStrategy progressionStrategy);

    protected abstract List<List<String>> solve();

    protected abstract long computeScore(List<List<String>> result);

    public final ChallengeResult run() {
        int trialIdx = 1;
        while (this.oracle.progressionStrategy().continuing()) {
            Date dateStart = new Date();
            System.out.println("Started Trial #" + trialIdx + " - " + dateStart);
            List<List<String>> result = solve();
            long score = computeScore(result);

            ChallengeResult currentResult = new ChallengeResult(score, result);

            if (this.currentBestResult == null || currentResult.score() >= this.currentBestResult.score()) {
                this.currentBestResult = new ChallengeResult(score, result);
            }

            this.previousResult = currentResult;

            Date dateEnd = new Date();
            System.out.println("Completed Trial #" + trialIdx);
            System.out.println("\t╠══> Completion Time - " + dateEnd);
            System.out.println("\t╠══> Trial duration: " + getTimeDifference(dateStart, dateEnd));
            System.out.println("\t╚══> Score: " + score);
            System.out.println();

            trialIdx++;
            this.oracle.progressionStrategy().update();
        }

        return this.currentBestResult;
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
