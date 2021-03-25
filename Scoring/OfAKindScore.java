package Scoring;

/**
 * Represents a number of a kind scoreline
 */
public class OfAKindScore extends Scores {
    int requirement;
    int sides;

    /*
     * Constructor for a line which checks if theres a certain number of dice
     */
    public OfAKindScore(int num, int sides) 
    {
        super(num + " of a kind");

        requirement = num;
        this.sides = sides;
    }

    /**
     * Calculates and sets the score for this line
     */
    public int calculateScore(int[] hand)
    {
        int maxCount = 0;
        int currentCount;
        
        for (int dieValue = 1; dieValue <= sides; dieValue++) 
        {
            currentCount = 0;
            
            for (int i = 0; i < hand.length; i++) 
            {
                if (hand[i] == dieValue)
                    currentCount++;
                
                if (currentCount > maxCount) 
                    maxCount = currentCount;
            }
        }
        
        int total = 0;
        
        for (int i : hand)
            total += i;
            
        return (maxCount >= requirement) ? total : 0;
    }
}
