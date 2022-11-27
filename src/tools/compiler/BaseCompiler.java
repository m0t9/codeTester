package tools.compiler;

import java.io.IOException;
import tools.compiler.exceptions.CompilationError;
import tools.compiler.exceptions.CompilationTimeLimitException;

/**
 * Compiler class that defines main options for derived classes.
 */
public class BaseCompiler {
  protected final String EXECUTABLE = "solution";
  protected final int MAX_COMPILATION_TIME_LIMIT = 5000;

  /**
   * Method to compile given source file.
   *
   * @param pathToSource path to source file
   * @param pathToOut    path to directory where binary file should be placed
   * @throws IOException          if compiler not found
   * @throws CompilationError     if something went wrong during compilation
   * @throws InterruptedException if compilation process was interrupted
   */
  public void compile(String pathToSource, String pathToOut) throws
          IOException, CompilationError, InterruptedException, CompilationTimeLimitException {
  }
}
