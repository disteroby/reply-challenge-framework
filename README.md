# Base Reply Challenge Template

## Env setting

Create and `.env` file for your local inputs and outputs folders, and ensure to add the two following variables:
* `INPUT_FOLDER`
* `OUTPUT_FOLDER`

You can copy `default.env` for simplicity.

## Input parsing

Modify the class `ChallengeDataModel.java` to store correctly the parsed input data.


## Challenge Solver

Create a class extending the abstract class `ChallengeSolver.java`.
Ensure to implements both `solve()` and `computeScore(String[][] result)` methods.

