package tools.compiler.exceptions;

/**
 * Exception will be thrown if compilation time will be exceeded.
 */
public class CompilationTimeLimitException extends Exception {
  @Override
  public String getMessage() {
    return "Compilation time exceeded time limit!";
  }
}
