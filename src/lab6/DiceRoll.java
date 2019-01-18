package lab6;
import java.util.Scanner;
import java.util.Random;

public class DiceRoll {
	static Scanner scnr = new Scanner(System.in);
	public static void main(String[] args) {
		boolean retry = true;
		header();		
		int sides = userInput();
		
		while(retry) {		
		printResults(sides);
		retry = retry();
		}
		
		exit();
		scnr.close();
	}

	private static void header() {
		System.out.println("Welcome to the Grand Circus Casino!");
	}
	
	private static int userInput() {
		boolean valid = false;
		String input = "";
		while (!valid) {
			System.out.print("\nHow many sides should each die have? ");
			input = scnr.nextLine();
			valid = validate(input);			
		}
		return Integer.parseInt(input);
	}
	
	public static boolean validate(String input) {
		String maxInt = Integer.toString(Integer.MAX_VALUE);
		
		if (input.isEmpty()) {
			System.out.println("Maybe check your numlock and try again...");
			return false;
		}
		
		if (input.charAt(0) == '-') {
			System.out.println("Try to stay POSITIVE and try again...");
			return false;
		}
		
		for (int i = 0; i < input.length(); i++) {
			int x = input.charAt(i);
			if (!(x >= 48 && x <= 57)) {
				System.out.println("Need a number, try again...");
				return false;
			}
		}
		
		if (input.length() > String.valueOf(Integer.MAX_VALUE - 2).length()) {
			System.out.println("The maximum number of sides you can have is " + (Integer.MAX_VALUE - 2) + ", try again...");
			return false;
		} else {
			if (input.length() == String.valueOf(Integer.MAX_VALUE).length()) {
				for (int i = 0; i < input.length(); i++) {
					if (input.charAt(i) < maxInt.charAt(i)) {
						break;
					} else if (input.charAt(i) > maxInt.charAt(i)) {
						System.out.println("Close! However, the max value of an integer = " + Integer.MAX_VALUE + ", try again...");
						return false;
					}
				}
			}	
		}
		
		if (Integer.parseInt(input) < 4) {
			System.out.println("Dice are 3-dimensional solids, think about it and try again...");
			return false;
		} else {
			return true;
		}
	}
	
	private static int generateRandomDieRoll(int sides) {
		Random roll = new Random();
		return roll.nextInt((sides - 1) + 1) + 1;
	}
	
	private static void printResults(int sides) {
		int die1 = generateRandomDieRoll(sides);
		int die2 = generateRandomDieRoll(sides);
		
		System.out.println("\nRoll 1:");
		if (die1 == 1 && die2 == 1) {
			System.out.println("SNAKE EYES!... CRAP :(");
		} else if (die1 == 6 && die2 == 6) {
			System.out.println("BOX CARS!... CRAP :(");
		} else if (die1 + die2 == 3) {
			System.out.println("Crap :(");
		} else {
			System.out.println(die1 + "\n" + die2);
		}
	}
	
	private static boolean retry() {
		char first = ' ';
		System.out.print("Roll Again? (y/n) ");
		if (scnr.hasNextLine()) {
			first = scnr.next().charAt(0);
		}
		
		while(first != 'y' && first != 'Y' && first != 'n' && first != 'N') {
			System.out.println("What was that?... type 'y' to continue or 'n' to exit");
			if (scnr.hasNext()) {
				first = scnr.next().charAt(0);
			}
		}		
		if (first == 'y' || first == 'Y') {
			scnr.nextLine();
			return true;
		}
		else {
			return false;
		}
	}
	
	private static void exit() {
		System.out.println("Goodbye!");
	}
}
