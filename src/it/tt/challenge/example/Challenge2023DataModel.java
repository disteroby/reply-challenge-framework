package it.tt.challenge.example;

import it.tt.challenge.core.BaseChallengeDataModel;
import it.tt.utils.IOReplyParser;

import java.util.ArrayList;
import java.util.List;

public class Challenge2023DataModel implements BaseChallengeDataModel<Challenge2023DataModel> {
    public int C; //Columns
    public int R; //Rows
    public int S; //Snakes number
    public List<Integer> snakesLength;
    public Integer[][] components;

    @Override
    public Challenge2023DataModel fromParser(IOReplyParser parser) {
        Challenge2023DataModel challenge2023DataModel = new Challenge2023DataModel();
        challenge2023DataModel.C = parser.parseInt();
        challenge2023DataModel.R = parser.parseInt();
        challenge2023DataModel.S = parser.parseInt();

        challenge2023DataModel.snakesLength = new ArrayList<>();
        for (int i = 0; i < challenge2023DataModel.S; i++) {
            challenge2023DataModel.snakesLength.add(parser.parseInt());
        }

        challenge2023DataModel.components = new Integer[challenge2023DataModel.R][challenge2023DataModel.C];
        for (int r = 0; r < challenge2023DataModel.R; r++) {
            for (int c = 0; c < challenge2023DataModel.C; c++) {
                challenge2023DataModel.components[r][c] = parser.parseIntOrNull();
            }
        }

        return challenge2023DataModel;
    }
}