package it.tt;

import it.tt.challenge.ChallengeExecutor;
import it.tt.challenge.example.Challenge2023DataModel;
import it.tt.challenge.example.Challenge2023Solver;

public class ChallengeMainEntrypoint {

    public static final String inputFileName = "00-example.txt";
    public static final String outputFileName = "out-" + inputFileName;

    public static void main(String[] args) {
        ChallengeExecutor.exec(new Challenge2023DataModel(), new Challenge2023Solver());
    }
}
