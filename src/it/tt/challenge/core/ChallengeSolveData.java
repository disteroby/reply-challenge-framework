package it.tt.challenge.core;

import it.tt.challenge.BaseChallengeDataModel;

/**
 * Represents the data related to a challenge solution process.
 *
 * @param <DATA_MODEL>   The type of the Data Model used in the challenge. It's an implementation of BaseChallengeDataModel
 * @param model          The data model used for solving the challenge.
 * @param iterationIndex The index of the current iteration.
 * @param bestResult     The best result obtained so far.
 * @param previousResult The result from the previous iteration.
 */
public record ChallengeSolveData<DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>>(
        DATA_MODEL model,
        int iterationIndex,
        ChallengeResult bestResult,
        ChallengeResult previousResult) {
}
