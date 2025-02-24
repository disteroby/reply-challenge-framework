package it.tt.challenge;

import it.tt.challenge.core.ChallengeResult;
import it.tt.challenge.core.ChallengeSolveData;
import it.tt.challenge.core.config.ChallengeConfig;
import it.tt.challenge.core.strategy.ChallengeOracle;
import it.tt.challenge.core.strategy.ChallengeProgressionStrategy;
import it.tt.challenge.core.io.IOReplyLogger;
import it.tt.utils.DateTimeUtils;

import java.util.*;

public abstract class ChallengeSolver<DATA_MODEL extends BaseChallengeDataModel<DATA_MODEL>> {

    private DATA_MODEL model;
    private ChallengeOracle oracle;
    private ChallengeResult currentBestResult;
    private ChallengeResult previousResult;

    private ChallengeConfig<DATA_MODEL, ? extends ChallengeSolver<DATA_MODEL>> configs;

    public ChallengeSolver() {
        this(null, null);
    }

    public ChallengeSolver(DATA_MODEL challengeDataModel, ChallengeProgressionStrategy progressionStrategy) {
        init(challengeDataModel, progressionStrategy);
    }

    public ChallengeSolver<DATA_MODEL> fromDataModel(DATA_MODEL challengeDataModel, ChallengeProgressionStrategy progressionStrategy) {
        init(challengeDataModel, progressionStrategy);
        return this;
    }

    private void init(DATA_MODEL model, ChallengeProgressionStrategy progressionStrategy) {
        this.model = model;
        this.oracle = new ChallengeOracle(progressionStrategy);
        this.currentBestResult = null;
        this.previousResult = null;
        this.configs = null;
    }

    protected abstract List<List<String>> solve(ChallengeSolveData<DATA_MODEL> solveData);

    protected abstract long computeScore(List<List<String>> result);

    private Map<String, String> getStrategyStatus() {
        Map<String, String> strategyStatus = this.oracle.progressionStrategy().getStrategyStatus();
        if (strategyStatus == null) {
            strategyStatus = new HashMap<>();
        }
        return strategyStatus;
    }

    public final ChallengeResult run() {
        int trialIdx = 1;

        int bestTrialIdx = -1;
        Date bestTrialDateStart = null;
        Date bestTrialDateEnd = null;
        Map<String, String> bestTrialStrategyStatus = null;

        Map<String, String> strategyStatus = getStrategyStatus();

        ArrayList<IOReplyLogger.DataPrintableValue> headers = composeHeaders(strategyStatus);

        List<Integer> lengths = null;
        if (configs.getLogsPartialResultAsTable()) {
            lengths = IOReplyLogger.printTableHeader(headers);
        }

        while (this.oracle.progressionStrategy().continuing()) {
            Date dateStart = new Date();

            boolean showLogForThisIteration = (trialIdx - 1) % configs.getLogEveryNIterations() == 0;

            if (showLogForThisIteration && !configs.getLogsPartialResultAsTable()) {
                IOReplyLogger.printListIterationHeader(trialIdx, dateStart);
            }

            ChallengeSolveData<DATA_MODEL> challengeSolveData = new ChallengeSolveData<>(model, trialIdx -1, currentBestResult, previousResult);
            List<List<String>> result = solve(challengeSolveData);
            long score = computeScore(result);

            Date dateEnd = new Date();

            ChallengeResult currentResult = new ChallengeResult(score, result);
            boolean acceptSolution = this.oracle.progressionStrategy().acceptSolution(currentResult, this.previousResult, this.currentBestResult);

            strategyStatus = getStrategyStatus();

            if (this.currentBestResult == null || acceptSolution) {
                this.currentBestResult = new ChallengeResult(score, result);
                bestTrialIdx = trialIdx;
                bestTrialDateStart = dateStart;
                bestTrialDateEnd = dateEnd;
                bestTrialStrategyStatus = strategyStatus;
            }

            this.previousResult = currentResult;

            if (showLogForThisIteration || acceptSolution) {
                List<IOReplyLogger.DataPrintableValue> values = computeDataPrintableValues(trialIdx, dateStart, dateEnd, score, acceptSolution, strategyStatus);

                if (configs.getLogsPartialResultAsTable()) {
                    IOReplyLogger.printPartialResultAsTable(values, lengths);
                } else {
                    IOReplyLogger.printPartialResultAsList(values, headers);
                }
            }

            trialIdx++;
            this.oracle.progressionStrategy().update();
        }

        if (configs.getLogsPartialResultAsTable()) {
            assert bestTrialStrategyStatus != null;
            List<IOReplyLogger.DataPrintableValue> values = computeDataPrintableValues(bestTrialIdx, bestTrialDateStart, bestTrialDateEnd, this.currentBestResult.score(), true, bestTrialStrategyStatus);
            IOReplyLogger.printTableFooter(values, lengths);
        }

        return this.currentBestResult;
    }

    private static List<IOReplyLogger.DataPrintableValue> computeDataPrintableValues(int trialIdx, Date dateStart, Date dateEnd, long score, boolean acceptSolution, Map<String, String> strategyStatus) {
        List<IOReplyLogger.DataPrintableValue> values = new ArrayList<>();
        values.add(new IOReplyLogger.DataPrintableValue("#" + trialIdx));
        values.add(new IOReplyLogger.DataPrintableValue(DateTimeUtils.getTimeDifference(dateStart, dateEnd), 11));
        values.add(new IOReplyLogger.DataPrintableValue(String.valueOf(score), 10));
        values.add(new IOReplyLogger.DataPrintableValue(acceptSolution ? "Yes" : "No"));

        Map<String, String> strategyStatusNotNullable = strategyStatus;
        if(strategyStatusNotNullable == null) {
            strategyStatusNotNullable = new HashMap<>();
        }

        for (Map.Entry<String, String> param : strategyStatusNotNullable.entrySet()) {
            values.add(new IOReplyLogger.DataPrintableValue(param.getValue(), param.getKey().length()));
        }
        return values;
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
