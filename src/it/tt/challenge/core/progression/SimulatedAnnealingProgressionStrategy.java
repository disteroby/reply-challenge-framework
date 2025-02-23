package it.tt.challenge.core.progression;

import it.tt.challenge.core.ChallengeResult;
import it.tt.challenge.core.ChallengeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SimulatedAnnealingProgressionStrategy extends ChallengeProgressionStrategy {

    private final float initialTemperature;
    private final float alpha;
    private final float minTemperature;
    private float currentTemperature;
    private final Random rand;

    public SimulatedAnnealingProgressionStrategy(ChallengeType challengeType, float initialTemperature, float alpha) {
        this(challengeType, initialTemperature, alpha, 1f);
    }

    public SimulatedAnnealingProgressionStrategy(ChallengeType challengeType, float initialTemperature, float alpha, float minTemperature) {
        super(challengeType);
        this.initialTemperature = initialTemperature;
        this.currentTemperature = initialTemperature;
        this.alpha = alpha;
        this.minTemperature = minTemperature;
        this.rand = new Random();
    }

    @Override
    public boolean continuing() {
        return this.currentTemperature > this.minTemperature;
    }

    @Override
    public void update() {
        this.currentTemperature *= this.alpha;
    }

    @Override
    public float randomChoice() {
        return rand.nextFloat() * (this.initialTemperature - this.currentTemperature) / this.initialTemperature;
    }

    @Override
    public boolean acceptSolution(ChallengeResult newResult, ChallengeResult prevResult, ChallengeResult bestResult) {
        if(bestResult == null) {
            return true;
        }

        float delta = newResult.score() - bestResult.score();

        if(ChallengeType.MINIMUM.equals(this.challengeType)) {
            delta *= -1f;
        }

        if(delta > 0) return true;

        return rand.nextFloat() < acceptanceProbability(delta, this.currentTemperature);
    }

    private static double acceptanceProbability(float delta, float temperature) {
        return Math.exp(delta / temperature);
    }

    @Override
    public Map<String, String> getStrategyStatus() {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("Current Temperature", String.format("%.03f", this.currentTemperature).replace(',', '.'));
        return statusMap;
    }
}
