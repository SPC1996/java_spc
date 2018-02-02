package java_spc.tutorials.custom_networking.tcp;

/**
 * 观看java tutorials的custom networking模块时所写的代码
 */
public class KnockProtocol {
    private static final int WAITING = 0;
    private static final int SENTKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;

    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;

    private String[] clues = {"Turnip", "Little Old Lady", "Atch", "Who", "Who"};
    private String[] answers = {"Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?"};

    public String processInput(String input) {
        String output = null;

        if (state == WAITING) {
            output = "Knock! Knock!";
            state = SENTKNOCK;
        } else if (state == SENTKNOCK) {
            if (input.equalsIgnoreCase("Who's there?")) {
                output = clues[currentJoke];
                state = SENTCLUE;
            } else {
                output = "You're supposed to say \"Who's there?\"!" +
                        "Try again. Knock! Knock!";
            }
        } else if (state == SENTCLUE) {
            if (input.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                output = answers[currentJoke] + " Want another? (y/n)";
                state = ANOTHER;
            } else {
                output = "You're supposed to say \"" +
                        clues[currentJoke] +
                        " who?\"! Try again. Knock! Knock!";
                state = SENTKNOCK;
            }
        } else if (state == ANOTHER) {
            if (input.equalsIgnoreCase("y")) {
                output = "Knock! Knock!";
                if (currentJoke == NUMJOKES - 1) {
                    currentJoke = 0;
                } else {
                    currentJoke++;
                }
                state = SENTKNOCK;
            } else {
                output = "Bye.";
                state = WAITING;
            }
        }
        return output;
    }
}
