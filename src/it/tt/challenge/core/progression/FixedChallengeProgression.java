package it.tt.challenge.core.progression;

public class FixedChallengeProgression implements ChallengeProgression {

    private final int maxNumberOfTrials;
    private int currentTrial;

    public FixedChallengeProgression(int maxNumberOfTrials) {
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
}
