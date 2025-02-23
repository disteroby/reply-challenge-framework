package it.tt.challenge.core.strategy;

import it.tt.challenge.core.ChallengeResult;
import it.tt.challenge.core.config.ChallengeType;

import java.util.Map;

public abstract class ChallengeProgressionStrategy {

    protected ChallengeType challengeType;

    public ChallengeProgressionStrategy(ChallengeType challengeType) {
        this.challengeType = challengeType;
    }

    abstract public boolean continuing();
    abstract public void update();
    abstract public float randomChoice();
    abstract public boolean acceptSolution(ChallengeResult newResult, ChallengeResult prevResult, ChallengeResult bestResult);

    public Map<String, String> getStrategyStatus() {
        return null;
    }

    public String getStrategyDescription() {
        return "Custom Progression Strategy.\n\t" +
                "Type: " + this.challengeType;
    }
}
