package tools.compiler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import tools.compiler.exceptions.CompilationError;
import tools.compiler.exceptions.CompilationTimeLimitException;


// TODO: Fix compilation
/**
 * Implementation of C language compiler.
 */
public class CCompiler extends BaseCompiler {
  /**
   * Method to compile C source file.
   *
   * @param pathToSource path to C source file
   * @param pathToOut    path to directory where source file should be placed
   * @throws IOException          if C compiler (gcc) not found
   * @throws CompilationError     if something went wrong during source code compilation
   * @throws InterruptedException if compilation process was interrupted
   */
  @Override
  public void compile(String pathToSource, String pathToOut)
          throws IOException, CompilationError, InterruptedException, CompilationTimeLimitException {
    var compilationBuilder = new ProcessBuilder("gcc", pathToSource,
            "-o", String.format("%s\\%s", pathToOut, EXECUTABLE));

    var compilation = compilationBuilder.start();
    if (!compilation.waitFor(MAX_COMPILATION_TIME_LIMIT, TimeUnit.MILLISECONDS)) {
      throw new CompilationTimeLimitException();
    }
    int exitCode = compilation.exitValue();
    if (exitCode != 0) {
      throw new CompilationError();
    }
  }
}
