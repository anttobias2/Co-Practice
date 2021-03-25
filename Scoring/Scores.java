package Scoring;

import javax.swing.*;
import java.awt.*;


public abstract class Scores {
    private int value;
    private boolean counted;
    private String name;
    public abstract int calculateScore(int[] h);

    // Constructor for an empty and unspecified score
    public Scores() {
        value = 0;
        counted = false;
        name = "";
    }

    // Constructor for score with specified name
    public Scores(String title) {
        value = 0;
        counted = false;
        name = title;
    }

    // Set score to a specific value
    public void setScore(int val) {
        value = val;
    }

    // Returns value of a score
    public int getScore() {
        return value;
    }

    // returns if score has been set or not
    public boolean isCounted() {
        return counted;
    }

    // Score based of given hand
    public boolean score(int[] hand) {
        if (counted) 
        	return false;
        else 
        {
            value = calculateScore(hand);
            counted = true;
            return true;
        }
    }

    // Returns the string of the name of the scoring line
    public String getName() {
        return name;
    }
    

    public void setCounted(boolean val) {
        counted = val;
    }

    // Updates score with possible scores based on hand
    public void updateScore(int[] hand) {
        setScore(calculateScore(hand));
    }
}
