package it.tt.challenge.core.progression;

import java.util.Random;

public class FixedChallengeProgressionStrategy extends ChallengeProgressionStrategy {

    private final int maxNumberOfTrials;
    private int currentTrial;
    private final Random rand;

    public FixedChallengeProgressionStrategy(int maxNumberOfTrials) {
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
}
