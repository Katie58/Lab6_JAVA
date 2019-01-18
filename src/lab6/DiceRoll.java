package lab6;
import java.util.Scanner;
import java.util.Random;

public class DiceRoll {
	static Scanner scnr = new Scanner(System.in);
	static String userName;
	static int sides;
	static int game = 0;
	static boolean comeOut = true;
	static int rolls = 0;
	static int money = 100;
	static int bet;
	static int comeLine = 0;
	
	public static void main(String[] args) {
		boolean retry = true;
		welcome();		
		
		while(retry) {	
		rolls = 0;
		comeOut = true;
		bet = userInput();
		rollDice();
		if (money == 0) {
			System.out.println("You have $" + money + "... GAME OVER! Please come back after you withdraw more money from the ATM...");
			retry = false;
		} else {
			retry = retry();
		}		
		game++;
		}		
		exit();
		scnr.close();
	}

	private static void welcome() {
		System.out.println("Enter your name: ");
		userName = scnr.nextLine();
		sides = userInput();
		if (sides == 6) {
			System.out.println("\nWelcome " + userName + ", to the Grand Circus Casino!\nYou have $" + money);
		} else {
			System.out.println("\nWelcome to " + userName + "'s Crap Shoot!\nYou have $" + money);
		}
	}
	
	private static int userInput() {
		boolean valid = false;
		String input = "";
		while (!valid) {
			if (game == 0) {
				userDie();
			} else {
				userBet();
			}
			input = scnr.nextLine();
			valid = validate(input);			
		}
		return Integer.parseInt(input);
	}
	
	private static void userDie() {
		System.out.print("\nCasino Craps uses 2 dice with 6 sides.\nHow many sides should each of your die have? ");
	}
	
	private static void userBet() {
		System.out.print("\nPlace your bet: ");
	}
	
	private static boolean validate(String input) {
		String maxInt = Integer.toString(Integer.MAX_VALUE);
		String maxMoney = Integer.toString(money);
		
		if (input.isEmpty()) {
			System.out.println("Maybe check your numlock and try again...");
			return false;
		}
				
		if (input.charAt(0) == '-') {
			System.out.println("Try to stay POSITIVE and try again...");
			return false;
		}
		
		if (game != 0 && input.indexOf('.') >= 0) {
			System.out.println("Do I look like a coin machine? The minimum bet is $1, try again...");
			return false;
		}		
		
		for (int i = 0; i < input.length(); i++) {
			int x = input.charAt(i);
			if (!(x >= 48 && x <= 57)) {
				System.out.println("Need a number, try again...");
				return false;
			}
		}
		
		if (game == 0) {
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
				game++;
				return true;
			}
		} else {
			if (input.length() > String.valueOf(money).length()) {
				System.out.println("The maximum bet available is " + money + ", try again...");
				return false;
			} else {
				if (input.length() == String.valueOf(money).length()) {
					for (int i = 0; i < input.length(); i++) {
						if (input.charAt(i) < maxMoney.charAt(i)) {
							break;
						} else if (input.charAt(i) > maxMoney.charAt(i)) {
							System.out.println("The maximum bet available is " + money + ", try again...");
							return false;
						}
					}
				}	
			}
			
			if (Integer.parseInt(input) == 0) {
				System.out.println("There are no free handouts here, try again...");
				return false;
			} else {
				return true;
			}
		}
	}
	
	private static int generateRandomDieRoll() {
		Random roll = new Random();
		return roll.nextInt((sides - 1) + 1) + 1;
	}
	
	private static void rollDice() {
		int die1 = generateRandomDieRoll();
		int die2 = generateRandomDieRoll();
		rolls++;
		
		System.out.println("Roll " + rolls + ":");
		System.out.println(die1 + "\n" + die2);
		if ((die1 + die2 == 7) || (die1 + die2 == 11)) {
			if (comeOut) {
				System.out.println("You rolled " + (die1 + die2) + "! You're a NATURAL!");
				money += bet * 2;
				rollAgain();			
			} else if (die1 + die2 == 7) {
				System.out.println("You rolled " + (die1 + die2) + " SEVEN OUT!... :(");
				money -= bet;				
			} else {
				System.out.println("You rolled " + (die1 + die2) + " you lose... :(");
				money -= bet;
				rollAgain();
			}
		} else if (die1 == 1 && die2 == 1) {
			System.out.println("SNAKE EYES!... CRAP you lose :(");
			money -= bet;
			rollAgain();
		} else if (die1 == 6 && die2 == 6) {
			System.out.println("BOX CARS!... CRAP you lose :(");
			money -= bet;
			rollAgain();
		} else if (die1 + die2 == 3) {
			System.out.println("CRAP you lose :(");
			money -= bet;
			rollAgain();
		} else {
			if (comeOut) {
				comeLine = die1 + die2;
				System.out.println("Point marked at " + comeLine);
				rollAgain();
			} else if (die1 + die2 == comeLine) {
				System.out.println("You rolled " + (die1 + die2) + " you win $" + bet);
				money += bet;
			} else {
				System.out.println("You rolled " + (die1 + die2) + " you lose... :(");
				money -= bet;
			}
			comeLine = 0;
			//System.out.println("Dice are loaded, perhaps you should try again...");
		}
	}
	
	private static void rollAgain() {
		comeOut = false;
		if (money != 0) {
			System.out.println("You now have $" + money + "\nENTER any key to roll again");
			scnr.next();
			rollDice();
		}
	}
	
	private static boolean retry() {
		char first = ' ';
		System.out.print("You now have $" + money + "\nPlay Again? (y/n) ");
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
		System.out.println("Goodbye " + userName + "!");
	}
}
