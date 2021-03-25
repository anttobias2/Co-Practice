package Scoring;

/**
 * Represents a upper score card scoreline
 */
public class IntsScore extends Scores {
    private int number;

    /**
     * Constructor for an upper scorecard score
     */
    public IntsScore(int num) 
    {
        super(String.valueOf(num));

        number = num;
    }

    /**
     * Calculates and sets the score for this line
     */
    public int calculateScore(int[] hand) 
    {
        int count = 0;
        
        for (int i = 0; i < hand.length; i++) 
        {
            if (hand[i]== number)
                count++;
        }
        return count * number;
    }
}
