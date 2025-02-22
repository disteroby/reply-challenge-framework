package it.tt;

import it.tt.challenge.ChallengeExecutor;
import it.tt.challenge.core.progression.*;
import it.tt.challenge.example.Challenge2023DataModel;
import it.tt.challenge.example.Challenge2023Solver;

public class ChallengeMainEntrypoint {

    /**
     * The name of the input file.
     * <p>
     * This constant defines the file from which the input data will be read. It is used as the source
     * of data for solving the challenge. Ensure that the file exists in the expected directory before
     * executing the program.
     * </p>
     */
    public static final String inputFileName = "00-example.txt";

    /**
     * The name of the output file.
     * <p>
     * This constant defines the name of the file where the output will be saved. The output file
     * will contain the results of the challenge after execution. By default, the output file is
     * prefixed with "out-" followed by the input file's name.
     * </p>
     */
    public static final String outputFileName = "out-" + inputFileName;

    /**
     * A flag to enable logging of input and output matrices.
     * <p>
     * If this constant is set to <code>true</code>, the program will print the input and output matrices
     * to the console. This can be useful for debugging or understanding the intermediate steps
     * of the challenge solver. Set this flag to <code>false</code> to disable logging.
     * </p>
     */
    public static final boolean enableMatrixLogger = true;

    /**
     * The number of executions before generating the final output.
     * <p>
     * This constant controls how many times the challenge solver should run before producing the final output.
     * The value is typically used for optimization, simulation, or other scenarios where multiple runs might
     * provide more accurate or refined results. The challenge may need to run multiple times to ensure the best solution
     * is found within the defined constraints.
     * </p>
     * <p>
     * The interface {@link it.tt.challenge.core.progression.ChallengeProgression} is responsible for managing the logic
     * behind the number of trials required to complete the challenge. Implementations of this interface define the
     * strategy for selecting how many runs (or "trials") should be allowed before the final result is considered.
     * </p>
     * <p>
     * Several standard implementations of the {@link it.tt.challenge.core.progression.ChallengeProgression} interface
     * are available in the package {@link it.tt.challenge.core.progression}. For example:
     * </p>
     * <ul>
     *     <li>{@link it.tt.challenge.core.progression.OneShotChallengeProgression}: Defines a strategy where only a single execution (or "trial") is performed. This is the default base strategy for the solver.</li>
     *     <li>{@link it.tt.challenge.core.progression.FixedChallengeProgression}: Defines a fixed number of trials to be used in the challenge</li>
     * </ul>
     */
    public static final ChallengeProgression progression = new OneShotChallengeProgression();

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
        ChallengeExecutor.exec(Challenge2023DataModel.class, Challenge2023Solver.class);
    }
}
