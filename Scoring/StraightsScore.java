package Scoring;

import java.util.Arrays;

/**
 * Checks and represents scoreline for straights
 */
public class StraightsScore extends Scores {
    int len;

    /**
     * Constructor for a straight on numbers on the dice
     * @param length length of the straight required to score
     */
    public StraightsScore(int length) {
        super(length + " sized straight");

        len = length;
    }

    /**
     * Calculates and sets the score for this line
     */
    public int calculateScore(int[] unsortedHand) {
        int[] hand = unsortedHand;
        int tally = 0;
        int score = 0;
        
        Arrays.sort(hand);
        
        for (int i = 0; i < hand.length-1; i++) 
        {
            if (hand[i+1] == hand[i]+1) 
                tally++;
            else if (hand[i+1] != hand[i])
            	tally = 0;
            
            
            if (tally == 3 && len==4)
                score = 30;
            if (tally == 4 && len==5)
                score = 40;
        }
        
        return score;
    }
}
