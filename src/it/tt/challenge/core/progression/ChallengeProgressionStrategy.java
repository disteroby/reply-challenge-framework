package it.tt.challenge.core.progression;

import it.tt.challenge.core.ChallengeResult;
import it.tt.challenge.core.ChallengeType;

import java.util.HashMap;
import java.util.Map;

public abstract class ChallengeProgressionStrategy {

    protected ChallengeType challengeType;
    protected Map<String, String> params;

    public ChallengeProgressionStrategy(ChallengeType challengeType) {
        this(challengeType, new HashMap<>());
    }

    public ChallengeProgressionStrategy(ChallengeType challengeType, Map<String, String> params) {
        this.challengeType = challengeType;
        this.params = params;
    }

    abstract public boolean continuing();
    abstract public void update();
    abstract public float randomChoice();
    abstract public boolean acceptSolution(ChallengeResult newResult, ChallengeResult prevResult, ChallengeResult bestResult);

    public Map<String, String> getStrategyStatus() {
        return null;
    }
}
