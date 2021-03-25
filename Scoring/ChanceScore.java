package Scoring;

/*
 * Represents and calculates chance score line
 */
public class ChanceScore extends Scores {

    // Constructor sets name of score to chance using Scores constructor
    public ChanceScore() {
        super("Chance");
    }

    /**
     * Calculates and sets the score for chance scoring line
     */
    public int calculateScore(int[] hand) {
        int total = 0;
        for (int i : hand)
            total += i;
        return total;
    }
}
