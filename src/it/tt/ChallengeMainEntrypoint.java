package it.tt;

import it.tt.challenge.ChallengeExecutor;
import it.tt.challenge.core.ChallengeConfig;
import it.tt.challenge.core.progression.*;
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
     * The data model class must extend {@link it.tt.challenge.core.BaseChallengeDataModel}, ensuring the
     * correct input data structure is defined. The solver class must implement {@link it.tt.challenge.core.ChallengeSolver},
     * containing the logic to process and solve the challenge.
     * </p>
     *
     * @param args Command-line arguments (not used in this execution flow).
     */
    public static void main(String[] args) {
        ChallengeExecutor.exec(new ChallengeConfig
                .Builder<>(Challenge2023DataModel.class, Challenge2023Solver.class)
                .setInputFileName(inputFileName)
                .setInputFolder(EnvUtils.get("INPUT_FOLDER"))
                .setOutputFolder(EnvUtils.get("OUTPUT_FOLDER"))
                .setProgression(new SimulatedAnnealingProgressionStrategy(1000, 10))
                .setEnableMatrixLogger(true)
                .build());
    }
}
