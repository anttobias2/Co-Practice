package Scoring;

/**
 * Represents a full house scoreline
 */
public class FullHouseScore extends Scores {

    /**
     * Constructor for a full house line
     */
    public FullHouseScore() 
    {
        super("Full House");
    }

    /**
     * Calculates and sets the score for this line
     */
    public int calculateScore(int[] hand) 
    {
        boolean found3K = false;
        boolean found2K = false;
        int currCount;
        
        for (int dieValue = 1; dieValue <= hand[0]; dieValue++)
        {
            currCount = 0;
            
            for (int i = 0; i < hand.length; i++) 
            {
                if (hand[i] == dieValue)
                    currCount++;
            }
            
            if (currCount == 2)
                found2K = true;
            if (currCount == 3)
                found3K = true;
        }

        return found2K && found3K ? 25 : 0;
    }
}
