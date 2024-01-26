import java.util.ArrayList;
import java.util.List;

public class Grammar {

  private List<State> states;
  private List<Character> terminals;
  private State startState;

  public List<State> getStates() {
    return states;
  }

  public void setStates(List<State> states) {
    this.states = states;
  }

  public List<State> removeState(State toRemove) {
    states.remove(toRemove);
    return states;
  }

  public List<Character> getTerminals() {
    return terminals;
  }

  public void setTerminals(List<Character> terminals) {
    this.terminals = terminals;
  }

  public State getStartState() {
    return startState;
  }

  public void setStartState(State startState) {
    this.startState = startState;
  }

  public State getStateWithName(char stateName) {
    for (State state : states) {
      if (state.getNonTerminal() == stateName) {
        return state;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    String str = "";
    for (State state : states) {
      str = str + state.getNonTerminal() + ": ";
      ArrayList<String> derivations = (ArrayList<String>) state.getDerivations();
      for (String derivation : derivations) {
        str = str + derivation;

        if (!(derivations.indexOf(derivation) == (derivations.size() - 1))) {
          str = str + "|";
        }
      }

      str = str + "\n";
    }
    return str;
  }
}
