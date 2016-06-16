package dfa;

/**
 * *
 * Builds automaton from txt file description.
 *
 * @See README.txt and INPUT_TEMPLATE.txt at dfa_config for more details.
 */
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

// Provides only a public method 'buildAutomaton' which returns a DFA instance.
public final class Builder {

    private static final String FOLDER_CONFIG = "dfa_config\\"; // Txt path.
    private static Scanner input; // File reading.

    /**
     * No instances allowed.
     */
    private Builder() {

    }

    /**
     * Terminates de program.
     *
     * @param c not found in alphabet in the file config.
     */
    private static void alphabetConfigError(char c) {
        System.err.println("Config file:");
        System.err.println("Character '" + c + "' at some transition not defined in alphabet.");
        System.exit(1);
    }

    /**
     * @param c character to be searched in alphabet.
     * @param alphabet all characters belonging to alphabet.
     * @return true if belongs to alphabet, false otherwise.
     */
    private static boolean belongsToAlphabet(char c, String alphabet) {
        if (!alphabet.contains(String.valueOf(c))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param fileName txt to be read.
     * @return a DFA instance.
     */
    public static DFA buildAutomaton(String fileName) {
        String alphabet;
        int numberOfStates;
        State states[];

        try {
            input = new Scanner(Paths.get(FOLDER_CONFIG + fileName));
        } catch (IOException ex) {
            Logger.getLogger(DFA.class.getName()).log(Level.SEVERE, null, ex);
        }

        alphabet = input.next(); // First txt line
        numberOfStates = Integer.valueOf(input.next()); // Second txt line
        states = new State[numberOfStates];
        String[] s; // For splitted data config

        int i;
        for (i = 0; i < numberOfStates; i++) { // Each txt line is a state config
            s = input.next().split(","); // Config for each state
            if (s[1].equals("0")) { // Is acceptance state?
                states[i] = new State(i, false, alphabet); // Ins't acceptance
            } else {
                states[i] = new State(i, true, alphabet); // Is acceptance
            }

            for (int k = 2; k < s.length; k = k + 2) { // Sets index of each possible state transition
                String entryTypes = s[k];
                for (int j = 0; j < entryTypes.length(); j++) { // Sets transition to each character
                    char c = entryTypes.charAt(j);
                    if (!belongsToAlphabet(c, alphabet)) {
                        alphabetConfigError(c); // Terminates the program
                    }
                    int from = alphabet.indexOf(c); // Char at the j position of entryTypes
                    int to = Integer.valueOf(s[k + 1]); // The transition function f(c)
                    states[i].setTransition(from, to);
                }
            }
        }
        input.close();
        return new DFA(alphabet, states);
    }
}
