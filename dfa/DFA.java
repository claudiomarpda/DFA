package dfa;

/**
 * DFA class works along with State class to simulate Deterministic Finite
 * Automata.
 */
import java.util.Scanner;

/**
 * Provides instantiations for single automata and for the operations union,
 * intersection and complement.
 */
public final class DFA {

    private static enum Operation { // For compound automata.
        UNION, INTERSECTION, COMPLEMENT
    };

    private final String alphabet; // Recognizable input.
    private final State[] states; // All transitions.
    private final DFA innerDFA; // For compound automata.
    private final Operation op; // For compound automata.

    // Single automata instance.
    DFA(String alphabet, State[] states) {
        this.alphabet = alphabet;
        this.states = states;
        this.op = null; // No operation.
        this.innerDFA = null;
    }
    
    // Union or Intersection.
    private DFA(DFA inner, DFA outer, Operation op) {
        innerDFA = inner;
        this.op = op;
        this.alphabet = outer.alphabet;
        this.states = outer.states;
    }

    // Complement.
    private DFA(DFA inner, Operation op) {
        innerDFA = inner;
        this.alphabet = innerDFA.alphabet;
        this.op = op;
        states = null;
    }

    public static DFA union(DFA inner, DFA outer) {
        return new DFA(inner, outer, Operation.UNION);
    }

    public static DFA intersection(DFA inner, DFA outer) {
        return new DFA(inner, outer, Operation.INTERSECTION);
    }

    public static DFA complement(DFA inner) {
        return new DFA(inner, Operation.COMPLEMENT);
    }

    /**
     * Method used by the user for string checking.
     *
     * @param s is the string to be verified against the automata.
     * @return true if string is accepted, false otherwise.
     */
    public boolean check(String s) {
        if (op == null && innerDFA == null) {
            return checkSingle(s);
        } else {
            return checkOperation(s);
        }
    }

    /**
     * Recursive method for compound automata.
     *
     * @param s is the string to be verified against the automata.
     * @return true if string is accepted, false otherwise.
     */
    private boolean checkOperation(String s) {
        if (innerDFA == null) {
            return checkSingle(s);
        } else if (op == Operation.UNION) {
            return checkSingle(s) || innerDFA.checkOperation(s);
        } else if (op == Operation.INTERSECTION) {
            return checkSingle(s) && innerDFA.checkOperation(s);
        } else { // Operation.COMPLEMENT
            return !(innerDFA.checkOperation(s));
        }
    }

    /**
     * Used for single automata.
     *
     * @param s is the string to be verified against the automata.
     * @param showTransition displays the current state at each transition.
     * @return true if string is accepted, false otherwise.
     */
    private boolean checkSingle(String s) {
        Scanner input = null;
        int current = 0; // State's index
        int from;
        char c;

        if (s.equals("")) { // Empty input string
            if (states[current].isAcceptance()) { // Ended in a acceptance state
                return true; // String accepted
            }
            return false;
        }
        for (int i = 0; i < s.length(); i++) { // s.length is the number of characters
            c = s.charAt(i); // Current character
            if (!belongsToAlphabet(c)) {
                inputStringError(c); // Terminates the program
            }
            from = alphabet.indexOf(c);
            current = states[current].getTransition(from); // Gets next transition index
        } // End of string entry
        if (input != null) {
            input.close();
        }
        if (states[current].isAcceptance()) { // Ended in a acceptance state
            return true; // String accepted
        }
        return false; // String not accepted. Ended in a not acceptance state
    }
    
    /**
     * Terminates de program.
     * @param c not found when doing transition function.
     */
    private void inputStringError(char c) {
        System.err.println("Input string:");
        System.err.println("Character entry '" + c + "' not defined in automaton alphabet.");
        System.exit(1);
    }

    private boolean belongsToAlphabet(char c) {
        if (!alphabet.contains(String.valueOf(c))) {
            return false;
        }
        return true;
    }

}
