import utils.CustomLogger;
import utils.EnvUtils;
import utils.IOReplyParser;

public class ChallengeMainEntrypoint {

    public static final String inputFileName = "00-example.txt";
    public static final String outputFileName = "out-" + inputFileName;

    public static void main(String[] args) {
        System.out.println();
        System.out.println("*************************************");
        System.out.println("*         CHALLENGE STARTED         *");
        System.out.println("*************************************");
        System.out.println();

        IOReplyParser parser = new IOReplyParser(EnvUtils.get("INPUT_FOLDER"), EnvUtils.get("OUTPUT_FOLDER"));
        String[][] contents = parser.readInput(inputFileName);
        System.out.println("\n\n=== INPUT ===\n");
        CustomLogger.printMatrix(contents);

        ChallengeDataModel challengeDataModel = ChallengeDataModel.fromParser(parser);

        // Change this with current challenge implementation
        ChallengeSolver solver = new Challenge2023Solver(challengeDataModel);
        ChallengeResult challengeResult = solver.run();

        System.out.println("\n\n=== OUTPUT with score: " + challengeResult.score() + " ===\n");
        CustomLogger.printMatrix(challengeResult.result());

        System.out.println("\n\n");
        parser.writeOutput(outputFileName, challengeResult.result());

        System.out.println();
        System.out.println("*************************************");
        System.out.println("*        CHALLENGE COMPLETED        *");
        System.out.println("*************************************");
        System.out.println();
    }
}
