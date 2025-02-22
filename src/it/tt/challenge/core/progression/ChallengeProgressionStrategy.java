package it.tt.challenge.core.progression;

public abstract class ChallengeProgressionStrategy {
    abstract public boolean continuing();
    abstract public void update();
    abstract public float randomChoice();

    public String getStrategyStatus() {
        return null;
    }
}
