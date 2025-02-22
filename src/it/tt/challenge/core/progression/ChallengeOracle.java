package it.tt.challenge.core.progression;

public record ChallengeOracle(ChallengeProgressionStrategy progressionStrategy) {
    @SuppressWarnings("unused")
    public boolean getRandomBoolean() {
        return getRandomFloat() <= .5f;
    }

    @SuppressWarnings("unused")
    public float getRandomFloat() {
        return progressionStrategy.randomChoice();
    }
}
