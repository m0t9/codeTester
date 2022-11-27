package tools.testing;

/**
 * List of available invocation verdicts.
 */
public enum Verdicts {
  PASS("ok", "Successful pass"),
  WRONG_ANSWER("wa", "Wrong answer"),
  TIME_LIMIT_EXCEEDED("tl", "Time limit exceeded"),
  RUNTIME_ERROR("re", "Runtime error"),
  COMPILATION_ERROR("ce", "Compilation error"),
  COMPILATION_TIME_LIMIT("ctl", "Compilation time limit exceeded"),
  TESTING_FAILED("fail", "Testing failed");

  private final String notation;
  private final String text;

  Verdicts(String notation, String text) {
    this.notation = notation;
    this.text = text;
  }

  public String getNotation() {
    return notation;
  }

  public String getText() {
    return text;
  }
}
