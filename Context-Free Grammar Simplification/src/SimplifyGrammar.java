import java.util.ArrayList;
import java.util.List;

public class SimplifyGrammar {

  private void removeEpsilon(Grammar cfg) {

    List<State> nullables = new ArrayList<>();
    ArrayList<State> statesInGrammar = (ArrayList<State>) cfg.getStates();

    boolean isNullable;

    for (State state : statesInGrammar) {
      isNullable = false;
      for (String derivation : state.getDerivations()) {
        if (derivation.equals("0") || derivation.isEmpty()) {
          isNullable = true;
        }
      }
      if (isNullable) {
        nullables.add(state);
      }
    }
    boolean newNullableAdded = false;
    @SuppressWarnings("unchecked")
    ArrayList<State> nonNullableStates =
        (ArrayList<State>) ((ArrayList<State>) cfg.getStates()).clone();
    nonNullableStates.removeAll(nullables);
    while (newNullableAdded) {
      int nullableVariableCount;
      for (State state : nonNullableStates) {
        for (String derivation : state.getDerivations()) {
          nullableVariableCount = 0;
          char[] splitDerivation = derivation.toCharArray();
          for (char variable : splitDerivation) {
            for (State nullableState : nullables) {
              if (nullableState.getNonTerminal() == variable) {
                nullableVariableCount++;
              }
            }
          }
          if (nullableVariableCount == derivation.length()) {
            nullables.add(state);
            nonNullableStates.remove(state);
            newNullableAdded = true;
          }
        }
      }
    }
    ArrayList<String> derivations;
    for (State state : statesInGrammar) {
      derivations = (ArrayList<String>) state.getDerivations();
      ArrayList<String> derivationsToAdd = new ArrayList<>();
      ArrayList<String> derivationsToRemove = new ArrayList<>();

      for (String derivation : derivations) {
        for (State nullableState : nullables) {
          if (derivation.contains(((Character) nullableState.getNonTerminal()).toString())
              && derivation.length() != 1) {
            char[] derivationToChar = derivation.toCharArray();
            char[] newDerivationToAdd = new char[derivation.length() - 1];
            int index = 0;
            for (char ch : derivationToChar) {
              if (ch != nullableState.getNonTerminal()) {
                newDerivationToAdd[index] = ch;
                index++;
              }
            }
            String newDerivation = new String(newDerivationToAdd);
            derivationsToAdd.add(newDerivation);
          } else if (derivation.equals("0") || derivation.isEmpty()) {
            derivationsToRemove.add(derivation);
          }
        }
      }

      derivations.addAll(derivationsToAdd);
      derivations.removeAll(derivationsToRemove);
    }
  }

  private void removeUselessStates(Grammar cfg) {

    ArrayList<Character> unproductive = new ArrayList<>();
    ArrayList<Character> productive = new ArrayList<>();

    for (State state : cfg.getStates()) {
      unproductive.add(state.getNonTerminal());
    }

    for (Character term : cfg.getTerminals()) {
      productive.add(term);
    }

    int temp = 0;
    boolean placeHolder = true;

    while (placeHolder && temp < 10) {
      placeHolder = false;
      for (State state1 : cfg.getStates()) {

        for (String deriv : state1.getDerivations()) {

          char[] derived = deriv.toCharArray();
          int safeVerify = 0;

          for (char ch : derived) {

            if (productive.contains(ch)) {
              safeVerify++;
            }
          }
          if (safeVerify == deriv.length()) {
            if (!productive.contains(state1.getNonTerminal())) {
              productive.add(state1.getNonTerminal());
              int index = unproductive.indexOf(state1.getNonTerminal());
              unproductive.remove(index);
              placeHolder = true;
            }
          }
        }
      }
      temp++;
    }
    boolean toDelete = false;
    for (State ridDeriv : cfg.getStates()) {
      ArrayList<String> toRemove = new ArrayList<>();
      for (String d : ridDeriv.getDerivations()) {
        char[] derivation = d.toCharArray();
        toDelete = false;

        for (char c : derivation) {
          if (unproductive.contains(c)) toDelete = true;
        }
        if (toDelete) toRemove.add(d);
      }
      ridDeriv.getDerivations().removeAll(toRemove);
    }

    ArrayList<State> newStates = new ArrayList<>();

    for (char productiveState : productive) {
      if (Character.isUpperCase(productiveState))
        for (State checkState : cfg.getStates()) {
          if (checkState.getNonTerminal() == productiveState) {
            newStates.add(checkState);
          }
        }
    }
    cfg.setStates(newStates);
    ArrayList<State> reachableStates = new ArrayList<State>();
    reachableStates.add(cfg.getStartState());

    for (State currentState : cfg.getStates()) {
      if (reachableStates.contains(currentState)) {
        for (String currentDerivation : currentState.getDerivations()) {
          char[] splitDerivation = currentDerivation.toCharArray();
          for (char currentChar : splitDerivation) {
            if (Character.isUpperCase(currentChar)) {
              State reachableState = cfg.getStateWithName(currentChar);
              if (!(reachableStates.contains(reachableState))) {
                reachableStates.add(reachableState);
              }
            }
          }
        }
      }
    }
    cfg.setStates(reachableStates);
    ArrayList<Character> newTerminals = new ArrayList<>();
    for (State s : cfg.getStates()) {
      for (String deriv : s.getDerivations()) {

        char[] derived = deriv.toCharArray();
        for (char ch : derived) {
          if (Character.isLowerCase(ch) && !newTerminals.contains(ch)) newTerminals.add(ch);
        }
      }
    }
    cfg.setTerminals(newTerminals);
  }

  public Grammar simplify(Grammar cfg) {
    removeEpsilon(cfg);
    removeUselessStates(cfg);
    return cfg;
  }
}
