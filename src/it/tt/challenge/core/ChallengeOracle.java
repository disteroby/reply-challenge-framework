package it.tt.challenge.core;

import it.tt.challenge.core.progression.ChallengeProgressionStrategy;

public record ChallengeOracle(ChallengeProgressionStrategy progressionStrategy) {
    @SuppressWarnings("unused")
    public float getRandomFloat() {
        return progressionStrategy.randomChoice();
    }

    @SuppressWarnings("unused")
    public float getRandomFloatInRange(float min, float max) {
        return getRandomFloat() * (max - min) + min;
    }

    @SuppressWarnings("unused")
    public int getRandomIntInRange(int min, int max) {
        return (int) (getRandomFloat() * (max - min) + min);
    }

    @SuppressWarnings("unused")
    public boolean getRandomBoolean() {
        return getRandomFloat() <= .5f;
    }
}
