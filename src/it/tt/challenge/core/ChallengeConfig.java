package it.tt.challenge.core;

import it.tt.challenge.core.progression.ChallengeProgressionStrategy;
import it.tt.challenge.core.progression.FixedChallengeProgressionStrategy;
import it.tt.challenge.core.progression.OneShotChallengeProgressionStrategy;

/**
 * Represents the configuration for a challenge, containing all necessary parameters.
 * <p>
 * This class is immutable and can only be instantiated using the {@link Builder} class.
 * The builder allows for a flexible and structured way to set optional and mandatory parameters.
 * </p>
 *
 * @param <DATA_MODEL> The type representing the data model.
 * @param <SOLVER>     The type representing the solver.
 */
public final class ChallengeConfig<DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>, SOLVER extends ChallengeSolver<DATA_MODEL>> {

    // Defaults
    private static final String DEFAULT_INPUT_FOLDER_PATH = "./";
    private static final String DEFAULT_OUTPUT_FOLDER_PATH = "./";
    private static final String DEFAULT_OUTPUT_FILE_NAME_PREFIX = "out-";
    private static final ChallengeProgressionStrategy DEFAULT_PROGRESSION_IMPLEMENTATION = new OneShotChallengeProgressionStrategy(ChallengeType.MAXIMUM);
    private static final Boolean DEFAULT_IO_LOGS = false;
    private static final Boolean DEFAULT_LOGS_PARTIAL_RESULT_AS_TABLE = false;
    private static final int DEFAULT_LOG_EVERY_N_INTERATIONS = 1;

    private final Class<DATA_MODEL> dataModelClass;
    private final Class<SOLVER> solverClass;
    private final String inputFileName;
    private final String outputFileName;
    private final String inputFolder;
    private final String outputFolder;
    private final ChallengeProgressionStrategy progressionStrategy;
    private final boolean ioLogs;
    private final boolean logsPartialResultAsTable;
    private final int logEveryNIterations;

    private ChallengeConfig(
            Class<DATA_MODEL> dataModelClass,
            Class<SOLVER> solverClass,
            String inputFileName,
            String outputFileName,
            String inputFolder,
            String outputFolder,
            ChallengeProgressionStrategy progressionStrategy,
            boolean ioLogs,
            boolean logsPartialResultAsTable,
            int logEveryNIterations
    ) {
        this.dataModelClass = dataModelClass;
        this.solverClass = solverClass;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.inputFolder = inputFolder;
        this.outputFolder = outputFolder;
        this.progressionStrategy = progressionStrategy;
        this.ioLogs = ioLogs;
        this.logsPartialResultAsTable = logsPartialResultAsTable;
        this.logEveryNIterations = logEveryNIterations;
    }

    public Class<DATA_MODEL> getDataModelClass() {
        return dataModelClass;
    }

    public Class<SOLVER> getSolverClass() {
        return solverClass;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String getInputFolder() {
        return inputFolder;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public ChallengeProgressionStrategy getProgressionStrategy() {
        return progressionStrategy;
    }

    public boolean getIOLogs() {
        return ioLogs;
    }

    public boolean getLogsPartialResultAsTable() {
        return logsPartialResultAsTable;
    }

    public int getLogEveryNIterations() {
        return logEveryNIterations;
    }

    /**
     * Builder class for constructing instances of {@link ChallengeConfig}.
     * <p>
     * This builder provides a fluent API for setting configuration parameters
     * before creating a new {@link ChallengeConfig} instance. It ensures that
     * mandatory parameters such as {@code dataModelClass} and {@code solverClass}
     * are provided while allowing optional customization of input/output file names,
     * folder locations, progression strategy, and logging settings.
     * </p>
     *
     * @param <DATA_MODEL> The type representing the data model.
     * @param <SOLVER>     The type representing the solver.
     */
    public static class Builder<DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>, SOLVER extends ChallengeSolver<DATA_MODEL>> {
        private final Class<DATA_MODEL> dataModelClass;
        private final Class<SOLVER> solverClass;
        private String inputFileName;
        private String outputFileName;
        private String inputFolder;
        private String outputFolder;
        private ChallengeProgressionStrategy progression;
        private Boolean ioLogs;
        private Boolean logsPartialResultAsTable;
        private Integer logEveryNIterations;

        /**
         * Constructs a new Builder instance for ChallengeConfig.
         *
         * @param dataModelClass the class type representing the DataModel
         * @param solverClass    the class type representing the Solver
         */
        public Builder(Class<DATA_MODEL> dataModelClass, Class<SOLVER> solverClass) {
            this.dataModelClass = dataModelClass;
            this.solverClass = solverClass;
        }

        /**
         * The name of the input file.
         * <p>
         * This constant defines the file from which the input data will be read. It is used as the source
         * of data for solving the challenge. Ensure that the file exists in the expected directory before
         * executing the program.
         * </p>
         */
        @SuppressWarnings("unused")
        public Builder<DATA_MODEL, SOLVER> setInputFileName(String inputFileName) {
            this.inputFileName = inputFileName;
            return this;
        }

        /**
         * The name of the output file.
         * <p>
         * This constant defines the name of the file where the output will be saved. The output file
         * will contain the results of the challenge after execution.
         * </p>
         */
        @SuppressWarnings("unused")
        public Builder<DATA_MODEL, SOLVER> setOutputFileName(String outputFileName) {
            this.outputFileName = outputFileName;
            return this;
        }

        /**
         * The directory where input files are stored.
         * <p>
         * This parameter specifies the folder path where input files for the challenge are located.
         * Ensure that the directory exists and contains the necessary files before execution.
         * </p>
         */
        @SuppressWarnings("unused")
        public Builder<DATA_MODEL, SOLVER> setInputFolder(String inputFolder) {
            this.inputFolder = inputFolder;
            return this;
        }

        /**
         * The directory where output files will be saved.
         * <p>
         * This parameter specifies the folder path where output files will be written after execution.
         * Ensure that the directory exists or has the necessary permissions for writing output data.
         * </p>
         */
        @SuppressWarnings("unused")
        public Builder<DATA_MODEL, SOLVER> setOutputFolder(String outputFolder) {
            this.outputFolder = outputFolder;
            return this;
        }

        /**
         * The number of executions before generating the final output.
         * <p>
         * This constant controls how many times the challenge solver should run before producing the final output.
         * The value is typically used for optimization, simulation, or other scenarios where multiple runs might
         * provide more accurate or refined results. The challenge may need to run multiple times to ensure the best solution
         * is found within the defined constraints.
         * </p>
         * <p>
         * The interface {@link ChallengeProgressionStrategy} is responsible for managing the logic
         * behind the number of trials required to complete the challenge. Implementations of this interface define the
         * strategy for selecting how many runs (or "trials") should be allowed before the final result is considered.
         * </p>
         * <p>
         * Several standard implementations of the {@link ChallengeProgressionStrategy} interface
         * are available in the package {@link it.tt.challenge.core.progression}. For example:
         * </p>
         * <ul>
         *     <li>{@link OneShotChallengeProgressionStrategy}: Defines a strategy where only a single execution (or "trial") is performed. This is the default base strategy for the solver.</li>
         *     <li>{@link FixedChallengeProgressionStrategy}: Defines a fixed number of trials to be used in the challenge</li>
         * </ul>
         * The default implementation is {@link OneShotChallengeProgressionStrategy}
         */
        @SuppressWarnings("unused")
        public Builder<DATA_MODEL, SOLVER> setProgression(ChallengeProgressionStrategy progression) {
            this.progression = progression;
            return this;
        }

        /**
         * A flag to enable logging of input and output matrices.
         * <p>
         * If this constant is set to <code>true</code>, the program will print the input and output matrices
         * to the console. This can be useful for debugging or understanding the intermediate steps
         * of the challenge solver. Set this flag to <code>false</code> to disable logging.
         * </p>
         */
        @SuppressWarnings("unused")
        public Builder<DATA_MODEL, SOLVER> setIOLogs(boolean ioLogs) {
            this.ioLogs = ioLogs;
            return this;
        }

        /**
         * Enables or disables logging of partial results as a table.
         * <p>
         * If set to <code>true</code>, the solver will output partial results in a tabular format.
         * This can be useful for tracking progress and understanding intermediate computations.
         * If set to <code>false</code>, only the final result will be logged.
         * </p>
         */
        @SuppressWarnings("unused")
        public Builder<DATA_MODEL, SOLVER> setLogsPartialResultAsTable(boolean logsPartialResultAsTable) {
            this.logsPartialResultAsTable = logsPartialResultAsTable;
            return this;
        }

        /**
         * Sets the interval at which logs should be printed.
         * <p>
         * This parameter determines how frequently logs will be printed during execution.
         * A value of <code>1</code> means that logs will be printed at every iteration,
         * while a higher value (e.g., 10) means that logs will be printed every 10 iterations.
         * </p>
         */
        @SuppressWarnings("unused")
        public Builder<DATA_MODEL, SOLVER> setLogEveryNIterations(int logEveryNIterations) {
            this.logEveryNIterations = logEveryNIterations;
            return this;
        }

        /**
         * Builds a new ChallengeConfig instance using the set parameters.
         *
         * @return a new ChallengeConfig instance
         */
        @SuppressWarnings("unused")
        public ChallengeConfig<DATA_MODEL, SOLVER> build() {

            // Fields that cannot be null
            if (dataModelClass == null) {
                throwIllegalArgumentException("dataModelClass");
            }
            if (solverClass == null) {
                throwIllegalArgumentException("solverClass");
            }
            if (inputFileName == null) {
                throwIllegalArgumentException("inputFileName");
            }

            // Fields with default value
            if (outputFileName == null) {
                outputFileName = DEFAULT_OUTPUT_FILE_NAME_PREFIX + inputFileName;
            }
            if (inputFolder == null) {
                inputFolder = DEFAULT_INPUT_FOLDER_PATH;
            }
            if (outputFolder == null) {
                outputFolder = DEFAULT_OUTPUT_FOLDER_PATH;
            }
            if (ioLogs == null) {
                ioLogs = DEFAULT_IO_LOGS;
            }
            if (logsPartialResultAsTable == null) {
                logsPartialResultAsTable = DEFAULT_LOGS_PARTIAL_RESULT_AS_TABLE;
            }
            if (logEveryNIterations == null) {
                logEveryNIterations = DEFAULT_LOG_EVERY_N_INTERATIONS;
            }
            if (progression == null) {
                progression = DEFAULT_PROGRESSION_IMPLEMENTATION;
            }

            return new ChallengeConfig<>(dataModelClass,
                    solverClass,
                    inputFileName,
                    outputFileName,
                    inputFolder,
                    outputFolder,
                    progression,
                    ioLogs,
                    logsPartialResultAsTable,
                    logEveryNIterations);
        }

        /**
         * Throws an IllegalArgumentException when a mandatory field is null.
         *
         * @param fieldName the name of the field that is null
         * @throws IllegalArgumentException if a mandatory field is null
         */
        private void throwIllegalArgumentException(String fieldName) {
            throw new IllegalArgumentException("Mandatory field '" + fieldName + "' cannot be null. Please provide a valid value when creating a ChallengeConfig instance.");
        }
    }
}
