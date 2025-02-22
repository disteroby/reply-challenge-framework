package it.tt.challenge.core.progression;

public interface ChallengeProgressionStrategy {
    boolean continuing();
    void update();
    float randomChoice();
}
