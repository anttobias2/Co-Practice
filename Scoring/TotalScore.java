package Scoring;

import java.util.ArrayList;

/**
 * Adds the total for all scores counted
 */
public class TotalScore extends Scores {
    ArrayList<Scores> scores;

    
     // Constructor for a line which totals other scores
    public TotalScore(String name, ArrayList<Scores> scoresToSum) {
        super(name);
        scores = scoresToSum;
        setCounted(true);
    }

    
    // Gets total score of the values in the array
    public int calculateScore(int[] h) {
        int sum = 0;
        
        for (Scores line: scores) 
        {
            if (line.isCounted())
                sum += line.getScore();
        }
        
        return sum;
    }
}
