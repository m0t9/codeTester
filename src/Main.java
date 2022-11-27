import tools.languages.Language;
import tools.testing.Tester;

public class Main {
  public static void main(String[] args) {
    Tester tester = new Tester();
    var src = "path\\to\\source\\file";
    Language language = Language.CPP; // Language of given source code
    var tests = "path\\to\\tests\\folder";
    int tl = 1000; // Time limit in ms for one test

    var verdicts = tester.testSolution(src, language, tests, tl);
    for (String verdict : verdicts) {
      System.out.println(verdict);
    }
  }
}
