package it.tt.challenge;

import it.tt.challenge.core.BaseChallengeDataModel;
import it.tt.challenge.core.ChallengeResult;
import it.tt.challenge.core.ChallengeSolver;
import it.tt.utils.CustomLogger;
import it.tt.utils.EnvUtils;
import it.tt.utils.IOReplyParser;

import static it.tt.ChallengeMainEntrypoint.*;

public class ChallengeExecutor {

    public static <DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>, SOLVER extends ChallengeSolver<DATA_MODEL>> void exec(Class<DATA_MODEL> dataModelClass, Class<SOLVER> solverClass) {

        try {

            // Instantiate the DataModel and Solver using the provided Class objects
            DATA_MODEL dataModelFactoryInstance = dataModelClass.getDeclaredConstructor().newInstance();
            SOLVER solverFactoryInstance = solverClass.getDeclaredConstructor().newInstance();

            System.out.println();
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║                                      ║");
            System.out.println("║          CHALLENGE STARTED!          ║");
            System.out.println("║                                      ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println();

            IOReplyParser parser = new IOReplyParser(EnvUtils.get("INPUT_FOLDER"), EnvUtils.get("OUTPUT_FOLDER"));
            String[][] contents = parser.readInput(inputFileName);

            if (enableMatrixLogger) {
                System.out.println("\n\n=== INPUT ===\n");
                CustomLogger.printMatrix(contents);
                System.out.println("\n\n");
            }

            DATA_MODEL challengeDataModel = dataModelFactoryInstance.fromParser(parser);

            ChallengeSolver<DATA_MODEL> challengeSolver = solverFactoryInstance.fromDataModel(challengeDataModel, progression);
            ChallengeResult challengeResult = challengeSolver.run();

            if (enableMatrixLogger) {
                System.out.println("\n\n=== OUTPUT with score: " + challengeResult.score() + " ===\n");
                CustomLogger.printMatrix(challengeResult.result());
                System.out.println("\n\n");
            }

            parser.writeOutput(outputFileName, challengeResult.result());

            System.out.println();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║                                        ║");
            System.out.println("║          CHALLENGE COMPLETED!          ║");
            System.out.println("║                                        ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println();

        } catch (Exception e) {
            System.err.println("An error occurred while executing the challenge.\n" + e);
        }
    }
}
