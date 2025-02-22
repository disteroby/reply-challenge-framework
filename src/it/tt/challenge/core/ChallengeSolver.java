package it.tt.challenge.core;

import it.tt.challenge.core.progression.ChallengeOracle;
import it.tt.challenge.core.progression.ChallengeProgressionStrategy;

import java.util.Date;
import java.util.List;

public abstract class ChallengeSolver<DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>> {

    protected DATA_MODEL model;
    protected ChallengeProgressionStrategy progression;

    public ChallengeSolver() {
        this(null, null);
    }

    public ChallengeSolver(DATA_MODEL model, ChallengeProgressionStrategy progression) {
        this.model = model;
        this.progression = progression;
    }

    public abstract ChallengeSolver<DATA_MODEL> fromDataModel(DATA_MODEL challengeDataModel, ChallengeProgressionStrategy progression);

    protected abstract List<List<String>> solve(ChallengeResult bestResult, ChallengeOracle oracle);

    protected abstract long computeScore(List<List<String>> result);

    public final ChallengeResult run(ChallengeOracle oracle) {
        ChallengeResult bestResult = null;

        int trialIdx = 1;
        while (progression.continuing()) {
            Date dateStart = new Date();
            System.out.println("Started Trial #" + trialIdx + " - " + dateStart);
            List<List<String>> result = solve(bestResult, oracle);
            long score = computeScore(result);

            if (bestResult == null || score > bestResult.score()) {
                bestResult = new ChallengeResult(score, result);
            }

            Date dateEnd = new Date();
            System.out.println("Completed Trial #" + trialIdx);
            System.out.println("\t╠══> Completion Time - " + dateEnd);
            System.out.println("\t╠══> Trial duration: " + getTimeDifference(dateStart, dateEnd));
            System.out.println("\t╚══> Score: " + score);
            System.out.println();

            trialIdx++;
            progression.update();
        }

        return bestResult;
    }

    private static String getTimeDifference(Date dateStart, Date dateEnd) {
        // Calculate the difference in milliseconds
        long diffInMillis = dateEnd.getTime() - dateStart.getTime();

        // Calculate minutes, seconds, and milliseconds
        long minutes = diffInMillis / 60000;
        long seconds = (diffInMillis % 60000) / 1000;
        long milliseconds = diffInMillis % 1000;

        // Return the formatted result as a string
        return String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
    }
}
