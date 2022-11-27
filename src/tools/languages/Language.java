package tools.languages;

import tools.languages.exceptions.InvalidLanguageError;

/**
 * Available programming languages.
 */
public enum Language {
  C,
  CPP;

  /**
   * Method that allows to parse string to corresponding programming Language.
   *
   * @param language string that contains programming language mention
   * @return corresponding language instance
   * @throws InvalidLanguageError if language string has no corresponding language
   */
  public static Language parse(String language) throws InvalidLanguageError {
    return switch (language) {
      case "c" -> C;
      case "cpp" -> CPP;
      default -> throw new InvalidLanguageError();
    };
  }
}
