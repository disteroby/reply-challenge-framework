package it.tt.challenge.core;

import it.tt.challenge.IOReplyParser;

public interface BaseChallengeDataModel<T extends BaseChallengeDataModel<T>> {
    T fromParser(IOReplyParser parser);
}
