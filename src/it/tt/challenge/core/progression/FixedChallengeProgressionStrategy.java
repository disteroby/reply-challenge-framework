package it.tt.challenge.core.progression;

import it.tt.challenge.core.ChallengeResult;
import it.tt.challenge.core.ChallengeType;

import java.util.Random;

public class FixedChallengeProgressionStrategy extends ChallengeProgressionStrategy {

    private final int maxNumberOfTrials;
    private int currentTrial;
    private final Random rand;

    public FixedChallengeProgressionStrategy(ChallengeType challengeType, int maxNumberOfTrials) {
        super(challengeType);
        this.maxNumberOfTrials = maxNumberOfTrials;
        this.currentTrial = 0;
        this.rand = new Random();
    }

    @Override
    public boolean continuing() {
        return this.currentTrial < this.maxNumberOfTrials;
    }

    @Override
    public void update() {
        this.currentTrial++;
    }

    @Override
    public float randomChoice() {
        return rand.nextFloat() * (this.maxNumberOfTrials - this.currentTrial) / this.maxNumberOfTrials;
    }

    @Override
    public boolean acceptSolution(ChallengeResult newResult, ChallengeResult prevResult, ChallengeResult bestResult) {
        if (bestResult == null) {
            return true;
        }

        if(ChallengeType.MAXIMUM.equals(this.challengeType)) {
            return newResult.score() > bestResult.score();
        } else {
            return newResult.score() < bestResult.score();
        }
    }

    @Override
    public String getStrategyDescription() {
        return "Fixed Progression Strategy.\n\t" +
                "Type: " + this.challengeType +
                ", Max Number Of Trials: " + this.maxNumberOfTrials;
    }
}
