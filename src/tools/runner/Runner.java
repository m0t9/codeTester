package tools.runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import tools.runner.exceptions.InvalidGivenTimeLimitException;
import tools.runner.exceptions.RuntimeError;
import tools.runner.exceptions.TimeLimitExceededError;

/**
 * Runner class description that allows to run binary file with custom input data.
 */
public class Runner {
  private static final int MAX_TIME_LIMIT = 5000;
  private static final int MIN_TIME_LIMIT = 1;

  /**
   * Method to run binary file with custom input data.
   *
   * @param pathToExecutable path to executable file
   * @param pathToInput path to input file
   * @param testTimeLimit time limit for execution time in milliseconds
   * @return bytes of executable output data
   * @throws IOException
   * @throws InterruptedException
   * @throws InvalidGivenTimeLimitException
   * @throws TimeLimitExceededError
   * @throws RuntimeError
   */
  @Deprecated
  public byte[] runExecutable(String pathToExecutable, String pathToInput, int testTimeLimit) throws
          IOException, InterruptedException, InvalidGivenTimeLimitException,
          TimeLimitExceededError, RuntimeError {
    if (!isTimeLimitValid(testTimeLimit)) {
      throw new InvalidGivenTimeLimitException();
    }

    var startRun = new ProcessBuilder(pathToExecutable);
    var run = startRun.start();

    run.getOutputStream().write(Files.newInputStream(Paths.get(pathToInput)).readAllBytes());
    run.getOutputStream().flush();

    boolean finished = run.waitFor(testTimeLimit, TimeUnit.MILLISECONDS);
    if (!finished) {
      run.destroy();
      throw new TimeLimitExceededError();
    }

    int exitCode = run.exitValue();
    if (exitCode != 0) {
      throw new RuntimeError();
    }

    return run.getInputStream().readAllBytes();
  }

  public byte[] runExecutable(String pathToExecutable, byte[] inputData, int timeLimit) throws
          IOException, InterruptedException, InvalidGivenTimeLimitException,
          TimeLimitExceededError, RuntimeError {
    timeLimitValidation(timeLimit);

    var startRun = new ProcessBuilder(pathToExecutable);
    var run = startRun.start();

    run.getOutputStream().write(inputData);
    run.getOutputStream().flush();

    if (!run.waitFor(timeLimit, TimeUnit.MILLISECONDS)) {
      run.destroy();
      throw new TimeLimitExceededError();
    }

    if (run.exitValue() != 0) {
      throw new RuntimeError();
    }

    return run.getInputStream().readAllBytes();
  }

  @Deprecated
  private boolean isTimeLimitValid(int timeLimit) {
    return timeLimit <= MAX_TIME_LIMIT && timeLimit >= MIN_TIME_LIMIT;
  }
  private void timeLimitValidation(int timeLimit) throws InvalidGivenTimeLimitException {
    if (timeLimit > MAX_TIME_LIMIT || timeLimit < MIN_TIME_LIMIT) {
      throw new InvalidGivenTimeLimitException();
    }
  }
}
