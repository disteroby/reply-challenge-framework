# Base Reply Challenge Framework

## Environment Setup

Create a `.env` file for your local input and output folders, and make sure to add the following two variables:
* `INPUT_FOLDER`
* `OUTPUT_FOLDER`

You can copy `default.env` for convenience.

## Input Parsing

Modify the `it.tt.challenge.example.Challenge2023DataModel.java` class to correctly store the parsed input data.

## Challenge Solver

Create a class that extends the abstract class `it.tt.challenge.core.ChallengeSolver.java`.
Make sure to implement both the `solve()` and `computeScore(String[][] result)` methods.
