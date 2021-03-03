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
	private int numTurns = numSides + 7; // 7 is the number of scores on the lower scorecard
	
	// Play one round of the game of yahtzee
	public void PlayOnce()
	{
		int [] scores = new int[numTurns];
		// Read in values and display to user
		ReadFile();
		
		PrintInfo();
		// Let user change if they would like and display info again
		SetInfo();
		PrintInfo();
		
		int turn = 0;
		while(turn < numTurns)
		{
			// create an array for hand of dice based on values given
			int[] handOfDice = new int[numDice + 1];
			int keepDie = 0;
			int playersTurn = 1;
			
			// create create starting hand
			for(int i = 0; i < numDice; i++)
			{
				handOfDice[i] = RollDie();
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
			CheckHand(handOfDice);
			turn++;
			
			System.out.println("\nHere is you next hand: ");
		}
	}

	public void CheckHand(int dice[])
	{
		// Checks and prints score for integers 1-6
		for(int i = 1; i <= numSides; i++)
		{
			System.out.println(i + "'s Score: " + CheckInts(dice, i));
		}

		// Checks and prints 3 and 4 of a kind scores
		if(CheckOfAKind(dice) >= 3)
		{
			System.out.println("3 of a Kind Score: " + AddTotalDice(dice));
		}
		else
			System.out.println("3 of a Kind Score: 0");
		
		if(CheckOfAKind(dice) >= 4)
		{
			System.out.println("4 of a Kind Score: " + AddTotalDice(dice));
		}
		else
			System.out.println("4 of a Kind Score: 0");


		// Checks and prints Full House score	
		if(CheckFullHouse(dice))
			System.out.println("Full House Score: 25");
		else
			System.out.println("Full House Score: 0");
			
		// Checks and prints Small and Large Straight scores
		if(CheckStraights(dice) >= numDice - 1)
		{
			System.out.println("Small Straight Score: 30");
		}
		else
			System.out.println("Small Straight Score: 0");
		
		if(CheckStraights(dice) == numDice)
		{
			System.out.println("Large Straight Score: 40");
		}
		else
			System.out.println("Large Straight Score: 0");
			
		// Checks and prints yahtzee score
		if(CheckYahtzee(dice))
			System.out.println("Yahtzee Score: 50");
		else
			System.out.println("Yahtzee Score: 0");
			
		// Prints chance score
		System.out.println("Chance Score: " + AddTotalDice(dice));
	}
	
	
	// Generates random number 1-numSides used to roll dice
	public int RollDie()
	{
		Random roll = new Random();
		
		return roll.nextInt(numSides) + 1;
	}
	
	// Displays user's hand of dice
	public void DisplayDiceNums(int dice[])
	{
		for(int i = 0; i < numDice; i++)
		{
			System.out.print(dice[i] + " ");
		}
			
			System.out.println();
	}
	
	// Take input from user to choose which dice to keep and which to reroll 
	public int ChooseDice(int dice[])
	{
		Scanner in = new Scanner(System.in);
		String keepOrReroll = "";
		int keep = 0;
		
		System.out.print("Choose which dice to keep (y/n): ");
		keepOrReroll = in.next();
		
		for(int diePosition = 0; diePosition < numDice; diePosition++)
		{
			if(keepOrReroll.charAt(diePosition) == 'n')
				dice[diePosition] = RollDie();
			else	
				keep++;
		}
		
		return keep; // returns the # of dice kept by user
	}
	
	// Checks user's hand for 3 or 4 of a kind
	public int CheckOfAKind(int dice[])
	{
		int maxCount = 0;
		int currentCount;
		
		// checks the number of dice in hand for each value of the dice sides
		for(int dieValue = 1; dieValue < numSides; dieValue++)
		{
			currentCount = 0;
			
			for(int diePosition = 0; diePosition < numDice; diePosition++)
			{
				if(dice[diePosition] == dieValue)
					currentCount++;
			}
			
			if(currentCount > maxCount)
				maxCount = currentCount;
		}
		
		return maxCount; // return the max # of equivelant dice
	}
	
	// Returns sum of user's hand of dice
	public int AddTotalDice(int dice[])
	{
		int diceSum = 0;
		
		for(int i = 0; i < numDice; i++)
		{
			diceSum = diceSum + dice[i];
		}
		
		return diceSum;
	}
	
	 // Sort dice in ascending order to keep track and check hand easier
	public void SortDice(int dice[])
	{
		boolean swap;
		int temp;
		
		do
		{
			swap = false;
			
			for(int dieNum = 0; dieNum < numDice-1; dieNum++)
			{
				if(dice[dieNum] > dice[dieNum+1])
				{
					temp = dice[dieNum];
					dice[dieNum] = dice[dieNum+1];
					dice[dieNum+1] = temp;
					swap = true;
				}
			}
		} while(swap);
	}
	
	// Checks user's hand for small and large straight
	public int CheckStraights(int dice[])
	{
		int maxStraight = 1;
		int currentStraight = 1;
		
		for(int i = 0; i < numDice - 1; i++)
		{
			// checks if next number is one greater than previous 
			if(dice[i] + 1 == dice[i+1])
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
	public boolean CheckFullHouse(int dice[])
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
				if(dice[diePosition] == dieValue)
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
	public int CheckInts(int dice[], int dieValue)
	{
		int sameNum = 0;
		int score;
		
		for(int diePosition = 0; diePosition < numDice; diePosition++)
		{
			if(dice[diePosition] == dieValue)
				sameNum++;
		}
		
		score = sameNum * dieValue;
		
		return score;
	}
	
	// Check's user's hand for a yahtzee
	public boolean CheckYahtzee(int dice [])
	{
		int numSameValue = 0;
		int dieValue = dice[0];
		
		for(int i = 0; i < numDice; i++)
		{
			if(dice[i] == dieValue)
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
	
	private void ChooseScore(int scoringArr[])
	{
		Scanner in = new Scanner(System.in);
		int score;
		
		System.out.print("Which score would you like to choose for this round?: ");
		score = in.nextInt();
	}
	


	// main function allows user to play as many times as they would like
	public static void main(String args[])
	{
		// creates yahtzee object
		Yahtzee game = new Yahtzee();
		
		// plays one round until user decides to stop
		do
		{
			game.PlayOnce();
		}while(game.PlayAgain());
	
	}
	
	
}
