package tools.languages.exceptions;

/**
 * Exception will be thrown if tester does not support given language.
 */
public class InvalidLanguageError extends Exception {
  @Override
  public String getMessage() {
    return "Given programming language is invalid!";
  }
}
