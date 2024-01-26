# Context-Free Grammar Simplification

This Java project focuses on handling the representation, parsing, and simplification of context-free grammars (CFGs). The project consists of five classes: `Grammar`, `Parser`, `SimplifyCFG`, `SimplifyGrammar`, and `State`. Each class plays a specific role in achieving the goal of simplifying CFGs.

## Classes

### 1. Grammar

The `Grammar` class represents a context-free grammar and includes methods to manage states, terminals, and rules.

```java
public class Grammar {
  // ... implementation details ...
}
```

### 2. Parser

The `Parser` class parses input strings to create a CFG. It extracts information about states, terminals, the start state, and production rules.

```java
public class Parser {
  // ... implementation details ...
}
```

### 3. SimplifyCFG

The `SimplifyCFG` class simplifies CFGs by removing epsilon productions and useless states.

```java
public class SimplifyCFG {
  // ... implementation details ...
}
```

### 4. SimplifyGrammar

The `SimplifyGrammar` class utilizes the simplification process, removing epsilon productions and useless states, to simplify a given CFG.

```java
public class SimplifyGrammar {
  // ... implementation details ...
}
```

### 5. State

The `State` class represents a state in a CFG, encapsulating information about the non-terminal symbol and its derivations.

```java
public class State {
  // ... implementation details ...
}
```

## Usage

To use this project, follow these general steps:

1. **Parse Grammar:**
   - Utilize the `Parser` class to parse input strings and create a CFG.

2. **Simplify Grammar:**
   - Use the `SimplifyGrammar` class to simplify the CFG by removing epsilon productions and useless states.

3. **Inspect Simplified Grammar:**
   - Utilize the methods provided by the `Grammar` class to examine the simplified grammar.

## Example

```java
// Example usage of the CFG simplification process
Grammar originalGrammar = new Parser().parseGrammar("V: A,B\nT: a,b\nS: A\nP: A -> aA | bB\nB -> ε");
SimplifyGrammar simplifyGrammar = new SimplifyGrammar();
Grammar simplifiedGrammar = simplifyGrammar.simplify(originalGrammar);
System.out.println("Simplified Grammar:\n" + simplifiedGrammar.toString());
```

## Note

This implementation assumes that the input CFG follows a specific format and structure. Ensure that the input adheres to the expected grammar representation for accurate results.
