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
     * The main entry point for the challenge.
     * <p>
     * This method serves as the starting point for executing the challenge solution. It instantiates the
     * necessary data model and solver classes to process the input and generate the output.
     * The data model class must extend {@link it.tt.challenge.core.BaseChallengeDataModel}, which defines the
     * input data structure, and the solver class must implement {@link it.tt.challenge.core.ChallengeSolver},
     * which contains the logic to solve the challenge.
     * </p>
     *
     * @param args Command-line arguments (not used in this case)
     */
    public static void main(String[] args) {

        // Initialize the data model and solver for the challenge and execute the solution.
        ChallengeExecutor.exec(new ChallengeConfig
                .Builder<>(Challenge2023DataModel.class, Challenge2023Solver.class)
                .setInputFileName(inputFileName)
                .setInputFolder(EnvUtils.get("INPUT_FOLDER"))
                .setOutputFolder(EnvUtils.get("OUTPUT_FOLDER"))
                .setProgression(new OneShotChallengeProgression())
                .setEnableMatrixLogger(true)
                .build());
    }
}
