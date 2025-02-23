package it.tt.challenge.core;

import it.tt.challenge.core.progression.ChallengeProgressionStrategy;
import it.tt.challenge.IOReplyLogger;
import it.tt.utils.DateTimeUtils;

import java.util.*;

public abstract class ChallengeSolver<DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>> {

    /**
     * The structured data model representing the input for the challenge.
     * This object holds the data that will be processed and used during the challenge.
     */
    protected DATA_MODEL model;

    /**
     * The oracle that allows making random choices during the challenge.
     * It helps in determining progression and decision-making based on a given strategy.
     */
    protected ChallengeOracle oracle;

    /**
     * The best result achieved during the current execution.
     * This is updated each time a better solution is found during a trial.
     */
    protected ChallengeResult currentBestResult;

    /**
     * The result of the previous computation or trial.
     * This result is stored for comparison with the current result.
     */
    protected ChallengeResult previousResult;

    private ChallengeConfig<DATA_MODEL, ? extends ChallengeSolver<DATA_MODEL>> configs;

    public ChallengeSolver() {
        this(null, null);
    }

    public ChallengeSolver(DATA_MODEL model, ChallengeProgressionStrategy progressionStrategy) {
        this.model = model;
        this.oracle = new ChallengeOracle(progressionStrategy);
        this.currentBestResult = null;
        this.previousResult = null;
        this.configs = null;
    }

    public abstract ChallengeSolver<DATA_MODEL> fromDataModel(DATA_MODEL challengeDataModel, ChallengeProgressionStrategy progressionStrategy);

    protected abstract List<List<String>> solve();

    protected abstract long computeScore(List<List<String>> result);

    public final ChallengeResult run() {
        int trialIdx = 1;

        Map<String, String> strategyStatus = this.oracle.progressionStrategy().getStrategyStatus();
        if (strategyStatus == null) {
            strategyStatus = new HashMap<>();
        }

        ArrayList<IOReplyLogger.DataPrintableValue> headers = composeHeaders(strategyStatus);

        List<Integer> lengths = null;
        if (configs.getLogsPartialResultAsTable()) {
            lengths = IOReplyLogger.printTableHeader(headers);
        }

        while (this.oracle.progressionStrategy().continuing()) {
            Date dateStart = new Date();

            strategyStatus = this.oracle.progressionStrategy().getStrategyStatus();
            if (strategyStatus == null) {
                strategyStatus = new HashMap<>();
            }

            boolean showLogForThisIteration = (trialIdx - 1) % configs.getLogEveryNIterations() == 0;

            if (showLogForThisIteration && !configs.getLogsPartialResultAsTable()) {
                IOReplyLogger.printListIterationHeader(trialIdx, dateStart);
            }

            List<List<String>> result = solve();
            long score = computeScore(result);

            ChallengeResult currentResult = new ChallengeResult(score, result);
            boolean acceptSolution = this.oracle.progressionStrategy().acceptSolution(currentResult, this.previousResult, this.currentBestResult);

            if (this.currentBestResult == null || acceptSolution) {
                this.currentBestResult = new ChallengeResult(score, result);
            }

            this.previousResult = currentResult;

            Date dateEnd = new Date();

            if (showLogForThisIteration) {
                List<IOReplyLogger.DataPrintableValue> values = new ArrayList<>();
                values.add(new IOReplyLogger.DataPrintableValue("#" + trialIdx));
                values.add(new IOReplyLogger.DataPrintableValue(DateTimeUtils.getTimeDifference(dateStart, dateEnd), 11));
                values.add(new IOReplyLogger.DataPrintableValue(String.valueOf(score), 10));
                values.add(new IOReplyLogger.DataPrintableValue(acceptSolution ? "Yes" : "No"));
                for (Map.Entry<String, String> param : strategyStatus.entrySet()) {
                    values.add(new IOReplyLogger.DataPrintableValue(param.getValue(), param.getKey().length()));
                }

                if (configs.getLogsPartialResultAsTable()) {
                    IOReplyLogger.printPartialResultAsTable(values, lengths);
                } else {
                    IOReplyLogger.printPartialResultAsList(values, headers);
                }
            }

            trialIdx++;
            this.oracle.progressionStrategy().update();
        }

        return this.currentBestResult;
    }

    private static ArrayList<IOReplyLogger.DataPrintableValue> composeHeaders(Map<String, String> strategyStatus) {
        ArrayList<IOReplyLogger.DataPrintableValue> tableHeaders = new ArrayList<>();
        tableHeaders.add(new IOReplyLogger.DataPrintableValue("Iteration"));
        tableHeaders.add(new IOReplyLogger.DataPrintableValue("Duration", 12));
        tableHeaders.add(new IOReplyLogger.DataPrintableValue("Score", 10));
        tableHeaders.add(new IOReplyLogger.DataPrintableValue("Accepted?"));
        for (Map.Entry<String, String> param : strategyStatus.entrySet()) {
            tableHeaders.add(new IOReplyLogger.DataPrintableValue(param.getKey()));
        }
        return tableHeaders;
    }

    public void setConfigs(ChallengeConfig<DATA_MODEL, ? extends ChallengeSolver<DATA_MODEL>> configs) {
        this.configs = configs;
    }
}
