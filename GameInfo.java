import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Contains config data and can r/w config file
 */
public class GameInfo {
    private final String filePath = System.getProperty("user.dir") + "/yahtzeeConfig.txt";
    private int sides = 6;
    private int dice = 5;
    private int rolls = 3;

    // Retrieves the current configuration values from the config file
    public GameInfo() {
        int[] config = importConfigFile(filePath);
        sides = config[0];
        dice = config[1];
        rolls = config[2];
    }

    /**
     * Reads the config file into an int[]
     */
    private static int[] importConfigFile(String filePath) {
        int numParams = 5;
        int[] intArr = new int[numParams];
        File inFile = new File(filePath);
        try {
            Scanner config = new Scanner(inFile);
            for(int i = 0; i < numParams; i++) {
                intArr[i] = Integer.parseInt(config.next());
            }
        } catch (Exception e) {
            System.out.println("There was an error in reading the config file: " + filePath);
        }
        return  intArr;
    }

    public int getSides() {
        return sides;
    }

    public int getDice() {
        return dice;
    }

    public int getRolls() {
        return rolls;
    }

}
