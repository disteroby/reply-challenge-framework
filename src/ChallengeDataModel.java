import utils.IOReplyParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChallengeDataModel {
    public int C; //Columns
    public int R; //Rows
    public int S; //Snakes number
    public List<Integer> snakesLength;
    public Integer[][] components;

    public ChallengeDataModel() {
    }

    public static ChallengeDataModel fromParser(IOReplyParser parser) {
        ChallengeDataModel challengeDataModel = new ChallengeDataModel();
        challengeDataModel.C = parser.parseInt();
        challengeDataModel.R = parser.parseInt();
        challengeDataModel.S = parser.parseInt();

        challengeDataModel.snakesLength = new ArrayList<>();
        for (int i = 0; i < challengeDataModel.S; i++) {
            challengeDataModel.snakesLength.add(parser.parseInt());
        }

        challengeDataModel.components = new Integer[challengeDataModel.R][challengeDataModel.C];
        for (int r = 0; r < challengeDataModel.R; r++) {
            for (int c = 0; c < challengeDataModel.C; c++) {
                challengeDataModel.components[r][c] = parser.parseIntOrNull();
            }
        }

        return challengeDataModel;
    }

    @Override
    public String toString() {
        return "ChallengeData{" + "Columns = " + C + ", Rows = " + R + ", Snake numbers =" + S + ", snakesLength=" + Arrays.toString(snakesLength.toArray()) + ", components=" + Arrays.toString(Arrays.stream(components).map(Arrays::toString).toArray()) + '}';
    }
}