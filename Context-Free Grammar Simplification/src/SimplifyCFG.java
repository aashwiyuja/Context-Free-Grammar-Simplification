import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimplifyCFG {

  public static void main(String[] args) throws FileNotFoundException {
    String input;
    String tempState = "V: ";
    String tempTerminals = "T: ";
    String tempStart = "S: ";
    String tempRules = "P: \n";
    Scanner s = new Scanner(new File("src/Test_4.txt"));
    while (s.hasNextLine()) {
      String stateHolder;
      stateHolder = s.next();
      tempRules += stateHolder + "\n";
      String[] states = stateHolder.split("-");
      tempState += states[0] + ',';
    }
    for (int i = 0; i < tempRules.length(); i++) {
      String tempStr = String.valueOf(tempRules.charAt(i));
      char tempChar = tempRules.charAt(i);
      if (!tempTerminals.contains(tempStr) && Character.isLowerCase(tempChar)) {
        tempTerminals += tempChar + ",";
      }
    }
    tempState = tempState.substring(0, tempState.length() - 1);
    tempTerminals = tempTerminals.substring(0, tempTerminals.length() - 1);
    tempRules = tempRules.substring(0, tempRules.length() - 1);
    tempStart += String.valueOf(tempState.charAt(3));
    input = tempState + "\n" + tempTerminals + "\n" + tempStart + "\n" + tempRules + "\n";

    try {
      Grammar parsedGrammar = new Parser().parseGrammar(input);
      if (parsedGrammar != null) {
        SimplifyGrammar simplifyGrammar = new SimplifyGrammar();
        Grammar simplifiedGrammar = simplifyGrammar.simplify(parsedGrammar);
        System.out.println("Below are the simplified rules : \n");
        System.out.println(simplifiedGrammar.toString());
      } else {
        System.out.println("Incorrect Input!");
      }
    } catch (Exception exp) {
    }
  }
}
