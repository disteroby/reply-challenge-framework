package it.tt;

import it.tt.challenge.BaseChallengeDataModel;
import it.tt.challenge.core.ChallengeExecutor;
import it.tt.challenge.ChallengeSolver;
import it.tt.challenge.core.config.ChallengeConfig;
import it.tt.challenge.core.config.ChallengeType;
import it.tt.challenge.core.strategy.*;
import it.tt.challenge.example.ChallengeExampleDataModel;
import it.tt.challenge.example.ChallengeExampleSolver;
import it.tt.utils.EnvUtils;

/**
 * The main entry point for executing the challenge solution.
 * <p>
 * This class initializes and configures the challenge execution environment. It sets up the required
 * data model and solver classes, retrieves environment-specific paths, and executes the challenge solver.
 * </p>
 *
 * <p>
 * The data model class must extend {@link BaseChallengeDataModel}, ensuring the
 * correct input data structure is defined. The solver class must implement {@link ChallengeSolver},
 * containing the logic to process and solve the challenge.
 * </p>
 */
public class ChallengeMainEntrypoint {

    private static final String inputFileName = "00-example.txt";

    public static void main(String[] args) {
        ChallengeExecutor.exec(new ChallengeConfig
                .Builder<>(ChallengeExampleDataModel.class, ChallengeExampleSolver.class) //Mandatory
                .setInputFileName(inputFileName) //Mandatory
                .setInputFolder(EnvUtils.get("INPUT_FOLDER"))
                .setOutputFolder(EnvUtils.get("OUTPUT_FOLDER"))
                .setProgression(new SimulatedAnnealingProgressionStrategy(ChallengeType.MAXIMUM, 1_200_000, 0.99f, 10))
                .setIOFileLogs(false)
                .setLogsPartialResultAsTable(true)
                .setLogEveryNIterations(10)
                .build());
    }
}
