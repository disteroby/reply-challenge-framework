package it.tt.challenge.example;

import it.tt.challenge.BaseChallengeDataModel;
import it.tt.challenge.core.io.IOReplyParser;

public class ChallengeExampleDataModel implements BaseChallengeDataModel<ChallengeExampleDataModel> {
    public Object data;

    @Override
    public ChallengeExampleDataModel fromParser(IOReplyParser parser) {
        ChallengeExampleDataModel challengeExampleDataModel = new ChallengeExampleDataModel();
        challengeExampleDataModel.data = new Object();
        return challengeExampleDataModel;
    }
}