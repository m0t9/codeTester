package tools.runner.exceptions;

/**
 * Exception that will be thrown if solution run time exceeded max allowed time limit.
 */
public class TimeLimitExceededError extends Exception {
  @Override
  public String getMessage() {
    return "Program run time exceeds time limit!";
  }
}
