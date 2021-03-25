package Scoring;

/**
 * Represents a Yahtzee scoreline
 */
public class YahtzeeScore extends Scores {
    /**
     * Constructor for the yahtzee score line
     */
    public YahtzeeScore() 
    {
        super("YAHTZEE");
    }

    /**
     * Calculates and sets the score for this line
     */
    public int calculateScore(int[] hand) 
    {
        int firstScore = hand[0];
        
        for (int i = 1; i < hand.length; i++) 
        {
            if (hand[i] != firstScore)
                return 0;
        }
        return 50;
    }
}
