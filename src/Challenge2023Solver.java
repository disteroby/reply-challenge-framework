public class Challenge2023Solver extends ChallengeSolver {

    public Challenge2023Solver(ChallengeDataModel challengeDataModel) {
        super(challengeDataModel);
    }

    @Override
    protected String[][] solve() {
        int rows = model.components.length;
        int cols = model.components[0].length;

        String[] row1 = new String[]{
                String.valueOf(model.C),
                String.valueOf(model.R),
                String.valueOf(model.S)
        };
        String[] row2 = model.snakesLength.stream()
                .map(String::valueOf)
                .toArray(String[]::new);

        String[][] strMatrix = new String[rows + 2][cols];
        strMatrix[0] = row1;
        strMatrix[1] = row2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                strMatrix[i + 2][j] = (model.components[i][j] != null) ?
                        String.valueOf(model.components[i][j]) :
                        "*";
            }
        }

        return strMatrix;
    }

    @Override
    public long computeScore(String[][] result) {
        return 0;
    }

    @Override
    public String toString() {
        return "Challenge2023Solver[" +
                "challengeData=" + model + ']';
    }
}