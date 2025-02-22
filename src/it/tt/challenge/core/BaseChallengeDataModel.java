package it.tt.challenge.core;

import it.tt.challenge.IOReplyParser;

public interface BaseChallengeDataModel<T> {
    T fromParser(IOReplyParser parser);
}
