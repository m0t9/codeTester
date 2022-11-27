package tools.runner.exceptions;

/**
 * Exception that will be thrown if solution terminated with non-zero error code.
 */
public class RuntimeError extends Exception {
  @Override
  public String getMessage() {
    return "Runtime error occurred!";
  }
}
