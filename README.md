# Reply Challenge Framework

## Overview

The **Reply Challenge Framework** is designed to facilitate solving Reply Challenges by abstracting away common
boilerplate code. It provides a structured setup so that users can focus exclusively on implementing the logic specific
to the current challenge. The framework takes care of **input parsing**, **challenge execution**, and **result output**,
allowing for an efficient and streamlined development process.

Minimum required **JDK**: `17`

## Input Parsing

To handle input parsing, you need to create a new class that implements the `BaseChallengeDataModel.java` interface.
This class is responsible for defining how the input data is structured and parsed.

### Implementing the `fromParser` Method

Each challenge has different input data formats, so you must implement the `fromParser` method accordingly. This
method should take raw input data and transform it into a structured format suitable for processing within the challenge
solver.

Example:

```java

public class MyChallengeDataModel implements BaseChallengeDataModel<MyChallengeDataModel> {
    private List<String> data;

    @Override
    public void fromParser(IOReplyParser parser) {
        // Use here the IOReplyParser to parse and store input data
    }
}

```

By following this approach, you ensure that input data is consistently structured and easily usable within the challenge
solver.

### The `IOReplyParser` class

The `IOReplyParser` class is designed to facilitate reading input files and extracting individual values.  
It provides a set of utility methods to parse different primitive types, allowing flexibility in handling invalid data.

Key features of the `IOReplyParser` class:

- Supports various primitive type parsing methods, such as `parseString()`, `parseInt()`, `parseIntOrNull()`, etc.
- Allows choosing whether to throw an exception if parsing fails or return `null` instead.
- Uses an internal iterator that moves sequentially through the input data, ensuring that each call retrieves the next
  available value.

## Challenge Solver

The core logic of solving a challenge is handled in a custom class that extends the abstract class
`ChallengeSolver.java`. This class must implement two essential methods: `solve` and `computeScore`.

### Implementing the `solve` Method

The `solve` method is where the main logic of the challenge is executed. It is used to generate a candidate output
solution.

```java

@Override
public List<List<String>> solve(ChallengeSolveData<MyChallengeDataModel> solveData) {
    int iterationIndex = solveData.iterationIndex();
    MyChallengeDataModel model = solveData.model();
    ChallengeResult bestResult = solveData.bestResult();
    ChallengeResult previousResult = solveData.previousResult();

    // Implement solving logic here

    return result;
}
```

#### Understanding `ChallengeSolveData`

The `ChallengeSolveData` object provides essential information required to execute the solving process effectively. It
contains the following attributes:

- **`model` (MyChallengeDataModel)**: Represents the data model associated with the challenge.
- **`iterationIndex` (int)**: Indicates the current 0-based iteration number. This can be useful for iterative or evolutionary
  algorithms where multiple attempts are made to improve the solution.
- **`bestResult` (ChallengeResult)**: Stores the best result obtained so far in the solving process. This can be used as
  a reference to guide the solver towards better solutions.
- **`previousResult` (ChallengeResult)**: Holds the result from the previous iteration. It can be used to compare the
  performance of consecutive iterations and make adjustments accordingly.

Using these attributes, the `solve` method can effectively generate and refine solutions based on the given challenge
constraints and past performance.

### Implementing the `computeScore` Method

The `computeScore` method calculates the performance score based on the challenge's evaluation criteria.

```java

@Override
public long computeScore(List<List<String>> result) {
    // Implement scoring logic here
    return score;
}

```

By implementing these methods, you ensure that the solver correctly processes input data, executes the challenge logic,
and evaluates the output.

## Challenge Setup

### Environment Setup

Create a `.env` file for your local input and output folders, and make sure to add the following two variables:

* `INPUT_FOLDER`
* `OUTPUT_FOLDER`

You can copy `default.env` for convenience.

### The `ChallengeConfig` Class

This class provides a structured way to configure challenges, ensuring that all necessary parameters are defined before
execution. The class is immutable and can only be instantiated using the `ChallengeConfig.Builder` class, which enforces
mandatory parameters and allows customization through optional settings.

### Instantiating a ChallengeConfig

To create a new instance of `ChallengeConfig`, use the `Builder` class. The builder ensures that essential parameters
are provided and allows for optional configurations.

#### Example Setup

```java

ChallengeConfig<MyDataModel, MySolver> config = new ChallengeConfig
        .Builder<>(MyDataModel.class, MySolver.class) //Mandatory
        .setInputFileName(inputFileName) //Mandatory
        .setInputFolder(EnvUtils.get("INPUT_FOLDER"))
        .setOutputFolder(EnvUtils.get("OUTPUT_FOLDER"))
        .setProgression(new SimulatedAnnealingProgressionStrategy(ChallengeType.MAXIMUM, 1_200_000, 0.99f, 10))
        .setIOFileLogs(false)
        .setLogsPartialResultAsTable(true)
        .setLogEveryNIterations(10)
        .build();

```

### ChallengeConfig Parameters

This class allows customization of the challenge setup, consisting of mandatory and optional parameters. The table below
summarizes them:

| **Parameter**              | **Description**                                                                                                                                       | **Default Value**                                                | **Mandatory** |
|----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------|---------------|
| `dataModelClass`           | Defines the type of data model used in the challenge.                                                                                                 | N/A                                                              | ☑️            |
| `solverClass`              | Specifies the solver implementation used to process the challenge.                                                                                    | N/A                                                              | ☑️            |
| `inputFileName`            | The name of the file containing input data.                                                                                                           | N/A                                                              | ☑️            |
| `outputFileName`           | The name of the file where the output results will be stored.                                                                                         | `out-<inputFileName>`                                            |               |
| `inputFolder`              | The directory containing input files.                                                                                                                 | `./`                                                             |               |
| `outputFolder`             | The directory where output files will be saved.                                                                                                       | `./`                                                             |               |
| `progressionStrategy`      | Defines how the challenge progresses over multiple executions.                                                                                        | `new OneShotChallengeProgressionStrategy(ChallengeType.MAXIMUM)` |               |
| `ioFileLogs`               | Enables logging of input and output files for debugging purposes.                                                                                     | `false`                                                          |               |
| `logsPartialResultAsTable` | Displays the challenge results in a tabular view for better readability.                                                                              | `true`                                                           |               |
| `logEveryNIterations`      | Instead of logging every single result, logs only once every 'N' iterations. However, candidate results that might be the solution are always logged. | `1`                                                              |               |

#### Handling Challenge Progression Strategy

The `progressionStrategy` parameter customizes the number of times the challenge runs before producing the final output.
It accepts implementations of the abstract class `ChallengeProgressionStrategy.java`, with some standard implementations
described below:

| **Progression**                         | **Description**                                                                        |
|-----------------------------------------|----------------------------------------------------------------------------------------|
| `OneShotChallengeProgressionStrategy`   | Runs the challenge only once.                                                          |
| `FixedChallengeProgressionStrategy`     | Runs the challenge a fixed number of times.                                            |
| `SimulatedAnnealingProgressionStrategy` | Gradually lowers temperature to reduce randomness and converge to an optimal solution. |

## Summary

To solve a challenge using the **Reply Challenge Framework**, follow these steps:

1. **Create a Data Model Class**: Implement the `BaseChallengeDataModel` interface in a new class. This class is
   responsible for parsing and structuring the input data. Implement the `fromParser` method to define how the raw input
   is converted into structured data.

2. **Develop a Solver Class**: Create a class that extends the `ChallengeSolver` abstract class. Implement the following
   methods:
    - `solve`: Implement the main logic of the challenge to process the input and generate output.
    - `computeScore`: Calculate the performance score based on the challenge's evaluation criteria.

3. **Configure the Challenge Setup**:
    - Create a `.env` file with the `INPUT_FOLDER` and `OUTPUT_FOLDER` variables.
    - Set up the `ChallengeConfig` class using the `ChallengeConfig.Builder`. Use the developed DataModel and Solver
      classes for this setup.

By following these steps, you can effectively parse input, solve the challenge, and evaluate the result!