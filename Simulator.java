/**
 * @See README.txt and INPUT_TEMPLATE.txt at dfa_config for more details.
 */
import dfa.Builder;
import dfa.DFA;

public final class Simulator {

    public static void main(String[] args) {
        boolean isAcceptance;

        DFA a1 = Builder.buildAutomaton("exactly_2a.txt");
        DFA a2 = Builder.buildAutomaton("exactly_2b.txt");

        DFA a3 = Builder.buildAutomaton("least_2a.txt");
        DFA a4 = Builder.buildAutomaton("least_2b.txt");

        DFA a5 = Builder.buildAutomaton("even.txt");

        DFA b1 = DFA.intersection(a1, a2);
        DFA b2 = DFA.intersection(b1, a3);
        DFA b3 = DFA.intersection(b2, a4);

        isAcceptance = a1.check("aa");
        //isAcceptance = b1.check("aabb");
        //isAcceptance = b3.check("aabb");

        DFA b4 = DFA.complement(b3);
        //isAcceptance = b4.check("aabb");

        DFA b5 = DFA.intersection(b4, a5);
        //isAcceptance = b5.check("aabb");

        if (isAcceptance) {
            System.out.println("\nString accepted!");
        } else {
            System.out.println("\nString rejected!");
        }
    }
}
