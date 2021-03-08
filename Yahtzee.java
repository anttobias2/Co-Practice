/*
This program allows user to play one round of lizard spock yahtzee and prints out scorecard
CPSC 223-01
Homework #1
@author Anthony Tobias
@version v1.1.1 2/21/2021
*/

import java.util.*;
import java.io.*;

public class Yahtzee
{
	// variables used throughout code to play game	
	private int numSides;
	private int numDice;
	private int numRolls;
	private int numTurns;
	private int bottomScore;
	private int topScore;	
	
	// make an array for the Players saved scores
	ArrayList<Integer> usersScores = new ArrayList<>();
		
	// Play one round of the game of yahtzee
	public void PlayOnce()
	{
		// number of turns in the game is the sides of die plus 7 bottom scores
		numTurns = numSides + 7;
		
		// Read in values and display to user
		ReadFile();
		// Create array to store scores for a single round
		ArrayList<Integer> roundScores = new ArrayList<>();

		PrintInfo();
		// Let user change if they would like and display info again
		SetInfo();
		PrintInfo();
		
		
		// put zeros in both arrays and make them long enough to hold all scores
		for(int i = 0; i < numTurns; i++)
		{
			roundScores.add(0);
			usersScores.add(0);
		}
		
		System.out.println(usersScores.size());
		System.out.println(roundScores.size());
		
		int turn = 0;
		while(turn < numTurns)
		{
			System.out.println("Here is your hand: ");
			// create an array for hand of dice based on values given
			//int[] handOfDice = new int[numDice + 1];
			ArrayList<Integer> handOfDice = new ArrayList<>();
			int keepDie = 0;
			int playersTurn = 1;
			
			// create create starting hand
			for(int i = 0; i < numDice; i++)
			{
				handOfDice.add(i,RollDie());
			}
			
			// play until user wants to keep all dice or used all rolls
			while(playersTurn < numRolls && keepDie != numDice)
			{
				// Sort and display dice after each roll
				SortDice(handOfDice);
				DisplayDiceNums(handOfDice);
				keepDie = ChooseDice(handOfDice);
				playersTurn++; // increment which turn player is on
			}
			
			// One final sort and display of final hand to user
			SortDice(handOfDice);
			System.out.println("Here is you final hand:");
			DisplayDiceNums(handOfDice);
			// check and display scores for each section of the game for user's hand of dice
			CheckHand(handOfDice, roundScores);
			ChooseScore(roundScores);
			DisplayScore();
			turn++;
			
			
		}
		
		DisplayScore();
	}

	public void CheckHand(ArrayList<Integer> dice, ArrayList<Integer> roundScores)
	{
		// Checks and prints score for integers 1-6
		for(int i = 1; i <= numSides; i++)
		{
			System.out.println(i + ": " + i + "'s Score: " + CheckInts(dice, i));
			roundScores.set(i-1, CheckInts(dice, i));
		}

		// Checks and prints 3 and 4 of a kind scores
		if(CheckOfAKind(dice) >= 3)
		{
			System.out.println((numSides + 1) + ": " + "3 of a Kind Score: " + AddTotalDice(dice));
			roundScores.set((numSides), AddTotalDice(dice));
		}
		
		else
		{
			System.out.println((numSides + 1) + ": " + "3 of a Kind Score: 0");
			roundScores.set((numSides), 0);
		}
		
		if(CheckOfAKind(dice) >= 4)
		{
			System.out.println((numSides + 2) + ": " + "4 of a Kind Score: " + AddTotalDice(dice));
			roundScores.set((numSides + 1), AddTotalDice(dice));
		}
		else
		{
			System.out.println((numSides + 2) + ": " + "4 of a Kind Score: 0");
			roundScores.set((numSides + 1), 0);
		}

		// Checks and prints Full House score	
		if(CheckFullHouse(dice))
		{
			System.out.println((numSides + 3) + ": " + "Full House Score: 25");
			roundScores.set((numSides + 2), 25);
		}
		else
		{
			System.out.println((numSides + 3) + ": " + "Full House Score: 0");
			roundScores.set((numSides + 2), 0);
		}
			
		// Checks and prints Small and Large Straight scores
		if(CheckStraights(dice) >= numDice - 1)
		{
			System.out.println((numSides + 4) + ": " + "Small Straight Score: 30");
			roundScores.set((numSides + 3), 30);
		}
		else
		{
			System.out.println((numSides + 4) + ": " + "Small Straight Score: 0");
			roundScores.set((numSides + 3), 0);
		}
		
		if(CheckStraights(dice) == numDice)
		{
			System.out.println((numSides + 5) + ": " + "Large Straight Score: 40");
			roundScores.set((numSides + 4), 40);
		}
		else
		{
			System.out.println((numSides + 5) + ": " + "Large Straight Score: 0");
			roundScores.set((numSides + 4), 0);
		}
			
		// Checks and prints yahtzee score
		if(CheckYahtzee(dice))
		{
			System.out.println((numSides + 6) + ": " + "Yahtzee Score: 50");
			roundScores.set((numSides + 5), 50);
		}
		else
		{
			System.out.println((numSides + 6) + ": " + "Yahtzee Score: 0");
			roundScores.set((numSides + 5), 0);
		}
		
		// Prints chance score
		System.out.println((numSides + 7) + ": Chance Score: " + AddTotalDice(dice));
		roundScores.set((numSides + 6), AddTotalDice(dice));
		
	}
	
	
	// Generates random number 1-numSides used to roll dice
	public int RollDie()
	{
		Random roll = new Random();
		
		return roll.nextInt(numSides) + 1;
	}
	
	// Displays user's hand of dice
	public void DisplayDiceNums(ArrayList<Integer> dice)
	{
		for(int i = 0; i < numDice; i++)
		{
			System.out.print(dice.get(i) + " ");
		}
			
			System.out.println();
	}
	
	// Take input from user to choose which dice to keep and which to reroll 
	public int ChooseDice(ArrayList<Integer> dice)
	{
		Scanner in = new Scanner(System.in);
		String keepOrReroll = "";
		int keep = 0;
		
		System.out.print("Choose which dice to keep (y/n): ");
		keepOrReroll = in.next();
		
		for(int diePosition = 0; diePosition < numDice; diePosition++)
		{
			if(keepOrReroll.charAt(diePosition) == 'n')
				dice.set(diePosition, RollDie());
			else	
				keep++;
		}
		
		return keep; // returns the # of dice kept by user
	}
	
	// Checks user's hand for 3 or 4 of a kind
	public int CheckOfAKind(ArrayList<Integer> dice)
	{
		int maxCount = 0;
		int currentCount;
		
		// checks the number of dice in hand for each value of the dice sides
		for(int dieValue = 1; dieValue <= numSides; dieValue++)
		{
			currentCount = 0;
			
			for(int diePosition = 0; diePosition < numDice; diePosition++)
			{
				if(dice.get(diePosition) == dieValue)
					currentCount++;
			}
			
			if(currentCount > maxCount)
				maxCount = currentCount;
		}
		
		return maxCount; // return the max # of equivelant dice
	}
	
	// Returns sum of user's hand of dice
	public int AddTotalDice(ArrayList<Integer> dice)
	{
		int diceSum = 0;
		
		for(int i = 0; i < numDice; i++)
		{
			diceSum = diceSum + dice.get(i);
		}
		
		return diceSum;
	}
	
	 // Sort dice in ascending order to keep track and check hand easier
	public void SortDice(ArrayList<Integer> dice)
	{
		boolean swap;
		int temp;
		
		do
		{
			swap = false;
			
			for(int dieNum = 0; dieNum < numDice-1; dieNum++)
			{
				if(dice.get(dieNum) > dice.get(dieNum+1))
				{
					temp = dice.get(dieNum);
					dice.set(dieNum, dice.get(dieNum+1));
					dice.set(dieNum+1, temp);
					swap = true;
				}
			}
		} while(swap);
	}
	
	// Checks user's hand for small and large straight
	public int CheckStraights(ArrayList<Integer> dice)
	{
		int maxStraight = 1;
		int currentStraight = 1;
		
		for(int i = 0; i < numDice - 1; i++)
		{
			// checks if next number is one greater than previous 
			if(dice.get(i) + 1 == dice.get(i+1))
				currentStraight++;
			// if last 2 numbers are the same, length stays the same
			else
				currentStraight = currentStraight;
	
			if(currentStraight > maxStraight)
				maxStraight = currentStraight;
		}
		
		return maxStraight; // returns the length of longest straight in hand
	}
	
	// Checks user's hand for full house
	public boolean CheckFullHouse(ArrayList<Integer> dice)
	{
		boolean fullHouse = false;
		boolean found2ofAKind = false;
		boolean found3ofAKind = false;
		int currentCount;
		
		// similar algorithm to CheckOfAKind
		for(int dieValue = 0; dieValue <= numSides; dieValue++)
		{
			currentCount = 0;
			
			for(int diePosition = 0; diePosition < numDice; diePosition++)
			{
				if(dice.get(diePosition) == dieValue)
					currentCount++;
			}
			
			if(currentCount == 2)
				found2ofAKind = true;
				
			if(currentCount == 3)
				found3ofAKind = true;
		}
		// must have 2 of one value and 3 of another to be true
		if(found2ofAKind && found3ofAKind)
			fullHouse = true;
			
		return fullHouse; // returns boolean for fullhouse
	}

	// Returns the score of a single die value in user's hand
	public int CheckInts(ArrayList<Integer> dice, int dieValue)
	{
		int sameNum = 0;
		int score;
		
		for(int diePosition = 0; diePosition < numDice; diePosition++)
		{
			if(dice.get(diePosition) == dieValue)
				sameNum++;
		}
		
		score = sameNum * dieValue;
		
		return score;
	}
	
	// Check's user's hand for a yahtzee
	public boolean CheckYahtzee(ArrayList<Integer> dice)
	{
		int numSameValue = 0;
		int dieValue = dice.get(0);
		
		for(int i = 0; i < numDice; i++)
		{
			if(dice.get(i) == dieValue)
				numSameValue++;
		}
		
		// all die values in hand must be the same to be true
		if(numSameValue == numDice)
			return true;
		else
			return false;
	}
	
	// Recieves input if user would like to play again
	public boolean PlayAgain()
	{
		Scanner in = new Scanner(System.in);
		char playAgain;
		
		System.out.print("Would you like to play again? (y/n): ");
		playAgain = in.next().charAt(0);
		
		if(playAgain == 'y' || playAgain == 'Y')
			return true;
		else
			return false;
	}
	
	// Sets info for the game
	public void SetInfo()
	{
		if(ChangeInfo())
		{
			setNumSides();
			setNumDice();
			setNumRolls();
		}
	}
	
	// sets user's number of die sides based on user input
	private int setNumSides()
	{
		int numSides;
		
		Scanner readInfo = new Scanner(System.in);
		System.out.print("Enter number of sides per die: ");
		numSides = readInfo.nextInt();
		this.numSides = numSides;
		
		return numSides;
	}	
	
	// sets number of dice in user's hand based on user input
	private int setNumDice()
	{
		int numDice;
		
		Scanner readInfo = new Scanner(System.in);
		System.out.print("Enter number of dice in hand: ");
		numDice = readInfo.nextInt();
		this.numDice = numDice;
		
		return numDice;
	}
	
	// sets number of rolls per turn based on user input
	private int setNumRolls()
	{
		int numRolls;
		
		Scanner readInfo = new Scanner(System.in);
		System.out.print("Enter number of rolls per turn: ");
		numRolls = readInfo.nextInt();
		this.numRolls = numRolls;
		
		return numRolls;
	}
	
	
	// prints info that will be used for the game of yahtzee
	public void PrintInfo()
	{
		System.out.println("Here is your info for the game:");
		System.out.println("Number of sides on each die: " + this.numSides);
		System.out.println("Number of dice in hand: " + this.numDice);
		System.out.println("Number of rolls per turn: " + this.numRolls);
	}
	
	// asks user if they would like to change the info used for the game
	public boolean ChangeInfo()
	{
		Scanner in = new Scanner(System.in);
		char change;
		
		System.out.print("Do you want to change these numbers?: ");
		change = in.next().charAt(0);
		
		if(change == 'y' || change == 'Y')
			return true;
		else 
			return false;
	}
	
	// reads in file at the start and sets base info for the game
	public void ReadFile()
	{
		try
		{
			String fileName = System.getProperty("user.dir") + "/yahtzeeConfig.txt";
			Scanner readNums = new Scanner(new File(fileName));
			
			this.numSides = readNums.nextInt();
			this.numDice = readNums.nextInt();
			this.numRolls = readNums.nextInt();
		}
		
		catch(IOException e)
		{System.out.println(e.getMessage());}
	}
	
	// Choose which score to keep after each round
	private void ChooseScore(ArrayList<Integer> scoringArray)
	{
		Scanner in = new Scanner(System.in);
		int score;
		
		System.out.print("\nWhich score would you like to choose for this round?: ");
		score = in.nextInt();
		
		// Players cannot pick the same score twice
		while(usersScores.get(score - 1) != 0)
		{
			System.out.print("You already have a score for this section. Choose again: ");
			score = in.nextInt();
		}
		usersScores.set(score - 1, scoringArray.get(score-1)); 
		
	}
	
	// Introduction to the game
	public void PrintIntroduction()
	{
		System.out.println("After each round choose which scoring number (Displayed on left side of screen) you would like to keep for your score. You may only keep each score once besides a yahtzee.\n");
	}
	
	// Calculate the total of the top scorecard
	private int CalcTop()
	{
		int totalTop = 0;
		
		for(int i = 0; i < numSides; i++)
			totalTop += usersScores.get(i);
		return totalTop;
	}
	
	// Calculate the total of the bottom scorecard
	private int CalcBottom()
	{
		int totalBottom = 0;
		
		for(int i = numSides; i < numSides + 7; i++)
			totalBottom += usersScores.get(i);
			
		if(totalBottom >= 63)
			totalBottom += 35;
			
		return totalBottom;
	}
	
	// Display the scores of the top and bottom sections of the scorecard
	private void DisplayScore()
	{
		int topScore = CalcTop();
		int bottomScore = CalcBottom();
		int totalScore = topScore + bottomScore;
		
		System.out.println("\nTop Scorecard Total: " + topScore);
		System.out.println("Bottom Scorecard Total: " + bottomScore);
		System.out.println("Total: " + totalScore + "\n");
	}
	// main function allows user to play as many times as they would like
	public static void main(String args[])
	{
		// creates yahtzee object
		Yahtzee game = new Yahtzee();
		
		game.PrintIntroduction();
		// plays one round until user decides to stop
		do
		{
			game.PlayOnce();
		}while(game.PlayAgain());
	
	}
	
	
}
