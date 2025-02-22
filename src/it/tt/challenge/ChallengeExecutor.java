package it.tt.challenge;

import it.tt.ChallengeMainEntrypoint;
import it.tt.challenge.core.BaseChallengeDataModel;
import it.tt.challenge.core.ChallengeResult;
import it.tt.challenge.core.ChallengeSolver;
import it.tt.utils.CustomLogger;
import it.tt.utils.EnvUtils;
import it.tt.utils.IOReplyParser;

public class ChallengeExecutor {

    public static <DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>, SOLVER extends ChallengeSolver<DATA_MODEL>> void exec(DATA_MODEL dataModelType, SOLVER solverType) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║                                      ║");
        System.out.println("║          CHALLENGE STARTED!          ║");
        System.out.println("║                                      ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();


        IOReplyParser parser = new IOReplyParser(EnvUtils.get("INPUT_FOLDER"), EnvUtils.get("OUTPUT_FOLDER"));
        String[][] contents = parser.readInput(ChallengeMainEntrypoint.inputFileName);
        System.out.println("\n\n=== INPUT ===\n");
        CustomLogger.printMatrix(contents);

        DATA_MODEL challengeDataModel = dataModelType.fromParser(parser);

        ChallengeSolver<DATA_MODEL> solver = solverType.fromDataModel(challengeDataModel);
        ChallengeResult challengeResult = solver.run();

        System.out.println("\n\n=== OUTPUT with score: " + challengeResult.score() + " ===\n");
        CustomLogger.printMatrix(challengeResult.result());

        System.out.println("\n\n");
        parser.writeOutput(ChallengeMainEntrypoint.outputFileName, challengeResult.result());

        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║                                        ║");
        System.out.println("║          CHALLENGE COMPLETED!          ║");
        System.out.println("║                                        ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }
}
