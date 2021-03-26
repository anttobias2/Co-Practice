import Scoring.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Scorecard for Yahtzee
 */

public class Scorecard {
    private ArrayList<Scores> lines;
    
    /**
     * Constructs a new ScoreCard
     * @param sideCount number of sides on each dice, determines length of upper section
     */
    public Scorecard(int sideCount) {
        lines = new ArrayList<>();
        for (int i = 1; i <= sideCount; i++) {
            lines.add(new IntsScore(i));
        }
        lines.add(new TotalScore("Upper", new ArrayList<>(lines.subList(0, sideCount)))); //lines[sideCount]
        lines.add(new BonusScore(lines.get(sideCount))); //lines[sideCount + 1]
        Scores totalUpper = new TotalScore("Total Upper", new ArrayList<>(lines.subList(sideCount, sideCount + 1)));
        lines.add(totalUpper); //lines[sideCount + 2]
        lines.add(new OfAKindScore(3, sideCount)); //lines[sideCount + 3]
        lines.add(new OfAKindScore(4, sideCount)); //lines[sideCount + 4]
        lines.add(new FullHouseScore()); //lines[sideCount + 5]
        lines.add(new StraightsScore(4)); //lines[sideCount + 6]
        lines.add(new StraightsScore(5)); //lines[sideCount + 7]
        lines.add(new YahtzeeScore()); //lines[sideCount + 8]
        lines.add(new ChanceScore()); //lines[sideCount + 9]
        Scores totalLower = new TotalScore("Lower", new ArrayList<>(lines.subList(sideCount + 3, sideCount + 10)));
        lines.add(totalLower); //lines[sideCount + 10]
        lines.add(new TotalScore("TOTAL SCORE", new ArrayList<>(Arrays.asList(totalUpper, totalLower)))); //lines[sideCount + 11]
    }

    /**
     * Gets an array of all the score values
     *
     * @return int array of the scores
     */
    public int[] getScores() {
        int[] res = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            res[i] = lines.get(i).getScore();
        }
        
        return res;
    }

    /**
     * Prints the ScoreCard line by line using stored scores
     */
    public void print() {
        for (Scores score: lines) {
            System.out.println(score.getName() + ' ' + score.getScore());
        }
    }


    /**
     * Calculates the current total score of the scorecard.
     * @return int
     */
    public int totalScore() {
        int total = 0;
        for (Scores score: lines) {
            if (score.isCounted()) {
                total += score.getScore();
            }
        }
        return total;
    }

    /**
     * Sets the selected score line to be a specified value
     * @param index score line to set score of
     * @param value score to enter into specified score line
     * @return boolean; false if the selected index is already chosen
     * @deprecated
     */
     @Deprecated
    public boolean scoreLine(int index, int value) {
        // Check if the index will apply to upper or lower section
        if (index >= lines.size()) 
        {
            if (lines.get(index - lines.size()).isCounted()) {
                return false;
            } 
            else 
                lines.get(index - lines.size()).setScore(value);
        } 
        else 
        {
            if (lines.get(index).isCounted())
                return false;
            else
                lines.get(index).setScore(value);
        }
        return true;
    }

    /**
     * Scores a line of the scorecard
     * @param index line to be scored
     * @param hand hand of dice to base scoring off of
     * @return boolean true if line was not already scored
     */
    public boolean scoreLine(int index, Dice hand) {
        return lines.get(index).score(hand.getDice());
    }

    /**
     * Determines if all scores are counted
     * @return boolean
     */
    public boolean isFull() {
        for(Scores s: lines) {
            if (!s.isCounted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the number of scores
     * @return int
     */
    public int size() {
        return lines.size();
    }

    /**
     * Getter for a specific score in the scorecard
     * @param index which score to get
     * @return Score
     */
    public Scores getScoreAtPosition(int index) {
        return lines.get(index);
    }

    /**
     * Gets the scoreCards total score
     * @return int
     */
    public int getScore() {
        return lines.get(lines.size() - 1).getScore();
    }

    /**
     * Revalidates the score card to represent counted values
     */
    public void revalidate() {
        for (Scores score: lines) {
            if (!score.isCounted()){
                score.setScore(0);
            }
        }
    }
}
