package it.tt.challenge;

import it.tt.challenge.core.BaseChallengeDataModel;
import it.tt.challenge.core.ChallengeConfig;
import it.tt.challenge.core.ChallengeResult;
import it.tt.challenge.core.ChallengeSolver;
import it.tt.challenge.core.ChallengeOracle;
import it.tt.utils.DateTimeUtils;

import java.util.Date;

public class ChallengeExecutor {

    public static <DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>, SOLVER extends ChallengeSolver<DATA_MODEL>> void exec(ChallengeConfig<DATA_MODEL, SOLVER> challengeConfig) {

        try {

            // Instantiate the DataModel and Solver using the provided Class objects
            DATA_MODEL dataModelFactoryInstance = challengeConfig.getDataModelClass().getDeclaredConstructor().newInstance();
            SOLVER solverFactoryInstance = challengeConfig.getSolverClass().getDeclaredConstructor().newInstance();

            System.out.println();
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║                                      ║");
            System.out.println("║          CHALLENGE STARTED!          ║");
            System.out.println("║                                      ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println();

            IOReplyParser parser = new IOReplyParser(challengeConfig.getInputFolder(), challengeConfig.getOutputFolder());
            String[][] contents = parser.readInput(challengeConfig.getInputFileName());

            if (challengeConfig.getIOLogs()) {
                System.out.println("\n\n═════╣  PARSED INPUT  ╠═════\n");
                IOReplyLogger.printMatrix(contents);
                System.out.println("\n\n");
            }

            DATA_MODEL challengeDataModel = dataModelFactoryInstance.fromParser(parser);

            ChallengeOracle oracle = new ChallengeOracle(challengeConfig.getProgressionStrategy());

            ChallengeSolver<DATA_MODEL> challengeSolver = solverFactoryInstance.fromDataModel(challengeDataModel, oracle.progressionStrategy());
            challengeSolver.setConfigs(challengeConfig);

            Date startRunDate = new Date();
            System.out.println("Started computation time: " + startRunDate);

            ChallengeResult challengeResult = challengeSolver.run();

            Date endRunDate = new Date();
            System.out.println("Ended computation time: " + endRunDate);
            System.out.println("Duration: " + DateTimeUtils.getTimeDifference(startRunDate, endRunDate));

            if (challengeConfig.getIOLogs()) {
                System.out.println("\n\n═════╣  GENERATED OUTPUT with score '" + challengeResult.score() + "'  ╠═════\n");
                IOReplyLogger.printMatrix(challengeResult.result());
                System.out.println("\n\n");
            }

            parser.writeOutput(challengeConfig.getOutputFileName(), challengeResult.result());

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
