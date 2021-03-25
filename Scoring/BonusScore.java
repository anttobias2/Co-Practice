package Scoring;
/**
 * Represents a bonus scoreline
 */
public class BonusScore extends Scores {
    Scores scoreToWatch;

    /**
     * Constructor for the bonus score line
     */
    public BonusScore(Scores upperScore) {
        super("Bonus");
        scoreToWatch = upperScore;
        setCounted(true);
    }

    /**
     * Calculates and sets the score for this line
     */
    public int calculateScore(int[] h) {
        return scoreToWatch.getScore() >= 63 ? 35 : 0;
    }

    @Override
    public void updateScore(int[] hand) {
        setScore(calculateScore(hand));
    }
}
