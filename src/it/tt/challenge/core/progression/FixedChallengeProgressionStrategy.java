package it.tt.challenge.core.progression;

public class FixedChallengeProgressionStrategy implements ChallengeProgressionStrategy {

    private final int maxNumberOfTrials;
    private int currentTrial;

    public FixedChallengeProgressionStrategy(int maxNumberOfTrials) {
        this.maxNumberOfTrials = maxNumberOfTrials;
        this.currentTrial = 0;
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
        return 0f;
    }
}
