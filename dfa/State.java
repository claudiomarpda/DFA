package dfa;

// State is an attribute of DFA class.
public final class State {

    private final boolean acceptance; // Acceptance.
    private final int[] transitions; // All transitions.
    private final int index; // qi
    private final String alphabet; // Recognizable input.

    State(int index, boolean acceptance, String alphabet) {
        this.alphabet = alphabet;
        this.acceptance = acceptance;
        this.transitions = new int[alphabet.length()];
        this.index = index;
    }

    /**
     *
     * @return whether it is acceptance state.
     */
    boolean isAcceptance() {
        return acceptance;
    }

    /**
     * Sets the transition when building the automaton.
     *
     * @param from current state.
     * @param to next state after transition.
     */
    void setTransition(int from, int to) {
        transitions[from] = to;
    }

    /**
     * Gets index of f(from)->to (transition function)
     *
     * @param from current state.
     * @return the index of the state after transition.
     */
    int getTransition(int from) {
        int to = transitions[from];
        return to;
    }

    @Override
    public String toString() {
        if (transitions == null) {
            return "Null transition.";
        }
        String s = "q" + index + ", F: " + isAcceptance();
        for (int i = 0; i < alphabet.length(); i++) {
            s += ", f(" + alphabet.charAt(i) + "): q" + transitions[i];
        }
        return s;
    }
}
