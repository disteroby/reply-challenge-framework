package it.tt.challenge;

import it.tt.challenge.core.io.IOReplyParser;

public interface BaseChallengeDataModel<T extends BaseChallengeDataModel<T>> {
    T fromParser(IOReplyParser parser);
}
