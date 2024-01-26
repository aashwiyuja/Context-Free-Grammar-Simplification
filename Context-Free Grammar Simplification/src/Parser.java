import java.util.ArrayList;
import java.util.List;

public class Parser {

  private Grammar parsedGrammar;

  public Grammar parseGrammar(String input) {

    parsedGrammar = new Grammar();

    boolean temp = true;

    String[] line = input.split("\n");

    for (String str : line) {
      if (temp) {

        if (str.startsWith("V:")) {
          states((str.split(":")[1]).trim());
        } else if (str.startsWith("T:")) {
          terminals(str.split(":")[1].trim());
        } else if (str.startsWith("S:")) {
          startState(str.split(":")[1].trim());
        } else if (str.startsWith("P:")) {
          temp = false;
        }
      } else {
        parseRules(str.trim());
      }
    }

    return parsedGrammar;
  }

  public void states(String newStates) {

    String[] splitStates = newStates.split(",");
    List<State> statesToAdd = new ArrayList<>();

    for (String state : splitStates) {
      state = state.trim();
      State newState = new State(state.charAt(0));
      statesToAdd.add(newState);
    }

    parsedGrammar.setStates(statesToAdd);
  }

  public void terminals(String terminals) {

    String[] splitTerminals = terminals.split(",");
    List<Character> terminalsToAdd = new ArrayList<>();

    for (String terminal : splitTerminals) {
      terminal = terminal.trim();
      terminalsToAdd.add(terminal.charAt(0));
    }

    parsedGrammar.setTerminals(terminalsToAdd);
  }

  public void startState(String startState) {
    parsedGrammar.setStartState(parsedGrammar.getStateWithName(startState.trim().charAt(0)));
  }

  public void parseRules(String rulesToParse) {

    State stateToEdit = parsedGrammar.getStateWithName(rulesToParse.charAt(0));
    List<String> derivationsToAdd = new ArrayList<>();
    String[] unparsedDerivations = rulesToParse.split("-")[1].trim().split("\\|");

    for (String derivation : unparsedDerivations) {
      derivation = derivation.trim();
      derivationsToAdd.add(derivation);
    }

    stateToEdit.setDerivations(derivationsToAdd);
  }
}
