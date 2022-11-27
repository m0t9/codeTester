package tools.runner.exceptions;

/**
 * Exception will be thrown if given time limit does obey Runner rules.
 */
public class InvalidGivenTimeLimitException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Given time limit does not obey Runner restrictions!";
  }
}
