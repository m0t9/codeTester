package tools.testing;

import tools.compiler.BaseCompiler;
import tools.compiler.CppCompiler;
import tools.compiler.exceptions.CompilationError;
import tools.compiler.exceptions.CompilationTimeLimitException;
import tools.languages.Language;
import tools.runner.Runner;
import tools.runner.exceptions.RuntimeError;
import tools.runner.exceptions.TimeLimitExceededError;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// TODO: Refactor and document
public class Tester {
  private final Map<Language, BaseCompiler> compilers;
  private final Runner runner;
  private final String ENVIRONMENT = "testing";
  private final String EXECUTABLE = "solution";

  public Tester() {
    compilers = new HashMap<Language, BaseCompiler>();
    // compilers.put(Language.C, new CCompiler());
    compilers.put(Language.CPP, new CppCompiler());
    runner = new Runner();
  }

  private String getFilename(String pathToSource) {
    var pathParts = pathToSource.split("\\\\");
    var fileParts = pathParts[pathParts.length - 1].split("\\.");
    return fileParts[0];
  }

  private String getFileExtension(String pathToSource) {
    var pathParts = pathToSource.split("\\\\");
    var fileParts = pathParts[pathParts.length - 1].split("\\.");
    return fileParts[fileParts.length - 1];
  }

  private void compileSolution(String pathToSource, Language sourceLanguage)
          throws CompilationError, IOException, InterruptedException, CompilationTimeLimitException {
    var sourceCompiler = compilers.get(sourceLanguage);
    sourceCompiler.compile(pathToSource, ENVIRONMENT);
  }

  private Verdicts runTestcase(String pathToExecutable, String inputPath,
                               String outputPath, int timeLimit) {
    try {
      var solutionAnswer = runner.runExecutable(pathToExecutable, inputPath, timeLimit);
      var testdataAnswer = Files.newInputStream(Paths.get(outputPath)).readAllBytes();
      if (Arrays.equals(solutionAnswer, testdataAnswer)) {
        return Verdicts.PASS;
      }
      return Verdicts.WRONG_ANSWER;
    } catch (TimeLimitExceededError e) {
      return Verdicts.TIME_LIMIT_EXCEEDED;
    } catch (IOException | InterruptedException e) {
      return Verdicts.TESTING_FAILED;
    } catch (RuntimeError e) {
      return Verdicts.RUNTIME_ERROR;
    }
  }

  private boolean isInputData(File file) {
    return getFileExtension(file.getAbsolutePath()).equals("in");
  }

  public ArrayList<String> testSolution(String pathToSource, Language language, String pathToTests, int timeLimit) {
    var protocol = new ArrayList<String>();
    int testCount = 0;
    int passedTestCount = 0;
    try {
      compileSolution(pathToSource, language);
      File testsDir = new File(pathToTests);
      for (File file : Objects.requireNonNull(testsDir.listFiles())) {
        if (!isInputData(file)) {
          continue;
        }
        testCount++;

        var inputFilename = file.getAbsolutePath();
        var outputFilename = String.format("%s\\%s.out",
                testsDir.getAbsolutePath(), getFilename(file.getName()));
        var verdict = runTestcase(String.format("%s\\%s",
                        ENVIRONMENT, EXECUTABLE),
                inputFilename, outputFilename, timeLimit);

        if (verdict == Verdicts.PASS) {
          passedTestCount++;
        }
        protocol.add(String.format("Verdict on test \"%s\" is %s.", file.getName(), verdict.getNotation()));
      }
      protocol.add(String.format("Solution successfully passed %d/%d tests.", passedTestCount, testCount));
    } catch (CompilationError e) {
      protocol.add(Verdicts.COMPILATION_ERROR.getText());
    } catch (IOException | InterruptedException e) {
      protocol.add(Verdicts.TESTING_FAILED.getText());
    } catch (CompilationTimeLimitException e) {
      protocol.add(Verdicts.TIME_LIMIT_EXCEEDED.getText());
    }
    return protocol;
  }
}
