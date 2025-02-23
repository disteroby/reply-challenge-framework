package it.tt;

import it.tt.challenge.BaseChallengeDataModel;
import it.tt.challenge.core.ChallengeExecutor;
import it.tt.challenge.ChallengeSolver;
import it.tt.challenge.core.config.ChallengeConfig;
import it.tt.challenge.core.config.ChallengeType;
import it.tt.challenge.core.strategy.*;
import it.tt.challenge.example.Challenge2023DataModel;
import it.tt.challenge.example.Challenge2023Solver;
import it.tt.utils.EnvUtils;

public class ChallengeMainEntrypoint {

    private static final String inputFileName = "00-example.txt";

    /**
     * The main entry point for executing the challenge solution.
     * <p>
     * This method initializes and configures the challenge execution environment. It sets up the required
     * data model and solver classes, retrieves environment-specific paths, and executes the challenge solver.
     * </p>
     *
     * <p>
     * The data model class must extend {@link BaseChallengeDataModel}, ensuring the
     * correct input data structure is defined. The solver class must implement {@link ChallengeSolver},
     * containing the logic to process and solve the challenge.
     * </p>
     *
     * @param args Command-line arguments (not used in this execution flow).
     */
    public static void main(String[] args) {
        ChallengeExecutor.exec(new ChallengeConfig
                .Builder<>(Challenge2023DataModel.class, Challenge2023Solver.class) //Mandatory
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
