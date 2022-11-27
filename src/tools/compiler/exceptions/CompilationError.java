package tools.compiler.exceptions;

/**
 * This exception will be thrown if compiler fails file compilation.
 */
public class CompilationError extends Exception {
  @Override
  public String getMessage() {
    return "Compilation failed!";
  }
}
