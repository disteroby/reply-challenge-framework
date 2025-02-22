# Reply Challenge Framework

## Overview

The **Reply Challenge Framework** is designed to facilitate solving Reply Challenges by abstracting away common
boilerplate code. It provides a structured setup so that users can focus exclusively on implementing the logic specific
to the current challenge. The framework takes care of **input parsing**, **challenge execution**, and **result output**,
allowing
for an efficient and streamlined development process.

## Input Parsing

To handle input parsing, you need to create a new class that implements the `BaseChallengeDataModel.java` interface.
This class is responsible for defining how the input data is structured and parsed.

### Implementing the `fromParser` Method

Each challenge has different input data formats, so you must implement the `fromParser` method accordingly. This method
should take raw input data and transform it into a structured format suitable for processing within the challenge
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

## Challenge Solver

The core logic of solving a challenge is handled in a custom class that extends the abstract class
`ChallengeSolver.java`. This class must implement three essential methods: `fromDataModel`,
`solve`, and `computeScore`.

### Implementing the `fromDataModel` Method

This method is responsible for receiving the parsed data model and preparing it for use in the solver. It ensures that
the solver has access to structured input data, creating a new instance for the custom solver class.

```java

@Override
public ChallengeSolver<MyChallengeDataModel> fromDataModel(MyChallengeDataModel challengeDataModel, ChallengeProgression progression) {
    MyChallengeSolver myChallengeSolver = new MyChallengeSolver();
    myChallengeSolver.model = challengeDataModel;
    myChallengeSolver.progression = progression;
    return myChallengeSolver;
}
```

### Implementing the `solve` Method

The `solve` method is where the main logic of the challenge is executed. It takes in structured input data and processes
it to generate the correct output.

```java

@Override
public List<List<String>> solve(ChallengeResult bestResult) {
    // Implement solving logic here
    return List.of();
}
```

### Implementing the `computeScore` Method

The `computeScore` method calculates the performance score based on the challenge's evaluation criteria.

```java

@Override
public int computeScore(OutputData outputData) {
    // Implement scoring logic here
    return 0;
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
        .Builder<>(MyDataModel.class, MySolver.class)
        .setInputFileName("inputFileName.txt")
        .setOutputFileName("outputFileName.txt")
        .setInputFolder(EnvUtils.get("INPUT_FOLDER"))
        .setOutputFolder(EnvUtils.get("OUTPUT_FOLDER"))
        .setProgression(new OneShotChallengeProgression())
        .setEnableMatrixLogger(true)
        .build();
```

### ChallengeConfig Parameters

This class allows customization of the challenge setup, consisting of mandatory and optional parameters.

#### Mandatory Parameters

- **`dataModelClass`** *(Class<DATA_MODEL>)*: Defines the type of data model used in the challenge.
- **`solverClass`** *(Class<SOLVER>)*: Specifies the solver implementation used to process the challenge.
- **`inputFileName`** *(String)*: The name of the file containing input data.

If any of these parameters are missing, an `IllegalArgumentException` will be thrown.

#### Optional Parameters

- **`outputFileName`** *(String, default: `out-<inputFileName>`)*: The name of the file where the output results will be
  stored.
- **`inputFolder`** *(String, default: `./`)*: The directory containing input files.
- **`outputFolder`** *(String, default: `./`)*: The directory where output files will be saved.
- **`progression`** *(ChallengeProgression, default: `OneShotChallengeProgression`)*: Defines how the challenge
  progresses over multiple executions.
- **`enableMatrixLogger`** *(boolean, default: `true`)*: Enables logging of input and output matrices for debugging
  purposes.

### Handling Challenge Progression

The `progression` parameter allows customizing how many times the challenge runs before generating the final output. It
accepts implementations of `ChallengeProgression`, such as:

- **`OneShotChallengeProgression`**: Runs the challenge only once.
- **`FixedChallengeProgression`**: Runs the challenge a fixed number of times.

## Summary

- Use `ChallengeConfig.Builder` to construct a configuration.
- Ensure mandatory parameters (`dataModelClass`, `solverClass`, `inputFileName`) are provided.
- Customize optional parameters based on challenge requirements.
- Implement `ChallengeSolver` methods to process input, execute logic, and compute scores.
- Handle progression strategies using `ChallengeProgression` implementations.
- Enable logging if matrix output is needed for debugging.

This structured approach ensures a consistent and flexible way to manage challenge configurations efficiently.

