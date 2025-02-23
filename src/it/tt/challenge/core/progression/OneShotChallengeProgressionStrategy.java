package it.tt.challenge.core.progression;

import it.tt.challenge.core.ChallengeType;

public class OneShotChallengeProgressionStrategy extends FixedChallengeProgressionStrategy {

    public OneShotChallengeProgressionStrategy(ChallengeType challengeType) {
        super(challengeType, 1);
    }

    @Override
    public String getStrategyDescription() {
        return "OneShot Progression Strategy.\n\t" +
                "Type: " + this.challengeType;
    }
}
