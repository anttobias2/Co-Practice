import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
	 This class holds an array of dice
	 Can roll, return and sort dice
 */
public class Dice {

    private ArrayList<Integer> handOfDice;
    private int numDice = 5;
    private int numSides = 6;

    // Constructor with no parameters creates 5 6-sided dice
    public Dice() {
        handOfDice = new ArrayList<>();
        
        for (int i = 0; i < numDice; i++) {
            handOfDice.add(1);
        }
    }

	// Constructs player hand with variable dice count and dice sides
    public Dice(int diceCount, int diceSides) {
        this.numDice = diceCount;
        this.numSides = diceSides;
        handOfDice = new ArrayList<>();
        
        for (int i = 0; i < numDice; i++) {
            handOfDice.add(1);
        }
    }

    // Returns the size of the hand array
    public int size() {
        return handOfDice.size();
    }

    // Rolls the selected dice given a boolean array of which dice to keep
    public void roll(boolean[] keep) {
        for (int dieNumber = 0; dieNumber < handOfDice.size(); dieNumber++) {
            // roll the die if you choose not to keep it
            if (!keep[dieNumber]) {
                handOfDice.set(dieNumber, (int) ((Math.random() * 6) + 1));
            }
        }
    }

    // Rolls all the dice in hand
    public void roll() {
        for (int die : handOfDice) {
            die = (int) ((Math.random() * 6) + 1);
        }
    }

    // Sort the hand from least to greatest
    public void sortHand() {
        boolean swap;
        Integer temp;
        
        do {
            swap = false;
            for (int count = 0; count < (handOfDice.size() - 1); count++) {
                if (handOfDice.get(count) > handOfDice.get(count + 1)) {
                    temp = handOfDice.get(count);
                    handOfDice.set(count, handOfDice.get(count + 1));
                    handOfDice .set(count + 1, temp);
                    swap = true;
                }
            }
        } while (swap);
    }

    // Returns a specific die
    public Integer getDiceAtPosition(int position) {
        return handOfDice.get(position);
    }

    // visual representation of hand
    public JPanel getVisual() {
        JPanel view = new JPanel();
        view.setLayout(new GridLayout(1,0));
        
        for (int dice: handOfDice) {
            // view.add(dice.getVisual());
        }
        return view;
    }

	// returns the number of sides of the dice
    public int getSides() {
        return numSides;
    }

	// returns the array of die values
    public int[] getDice() {
        int[] arr = new int[handOfDice.size()];
        for(int i = 0; i < handOfDice.size(); i++) {
            if (handOfDice.get(i) != null) {
                arr[i] = handOfDice.get(i);
            }
        }
        return arr;
    }

}
