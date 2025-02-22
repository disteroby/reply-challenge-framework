package it.tt.challenge.core.progression;

import java.util.Random;

public class SimulatedAnnealingProgressionStrategy extends ChallengeProgressionStrategy {

    private final float initialTemperature;
    private final float epsilon;
    private float currentTemperature;
    private final Random rand;

    public SimulatedAnnealingProgressionStrategy(float initialTemperature, float epsilon) {
        this.initialTemperature = initialTemperature;
        this.currentTemperature = initialTemperature;
        this.epsilon = epsilon;
        this.rand = new Random();
    }

    @Override
    public boolean continuing() {
        return this.epsilon < this.currentTemperature;
    }

    @Override
    public void update() {
        this.currentTemperature*=0.9f;
    }

    @Override
    public float randomChoice() {
        return rand.nextFloat() * (this.initialTemperature - this.currentTemperature) / this.initialTemperature;
    }

    @Override
    public String getStrategyStatus() {
        return "Simulated Annealing Progression Strategy with current temperature: " + this.currentTemperature;
    }
}
