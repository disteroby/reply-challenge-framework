package it.tt.challenge.example;

import it.tt.challenge.ChallengeSolver;
import it.tt.challenge.core.ChallengeSolveData;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class ChallengeExampleSolver extends ChallengeSolver<ChallengeExampleDataModel> {

    @Override
    protected List<List<String>> solve(ChallengeSolveData<ChallengeExampleDataModel> solveData) {

        if(solveData.previousResult() != null) {
            // Make the thread sleep for 0.05 seconds (50 milliseconds) to simulate a light computation
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted!");
            }
            return solveData.previousResult().result();
        }

        // Dummy results
        List<List<String>> result = List.of(List.of("1", "2"), List.of("3", "4"));

        // Make the thread sleep for 1.5 seconds (1500 milliseconds) to simulate a heavy computation
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted!");
        }

        return result;
    }

    @Override
    protected long computeScore(List<List<String>> result) {
        return new Random().nextInt(1_000_000); //Mock Computation
    }
}