package man;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Hang {
	
	private static int lives = 6;
	private static String[] image = new String[] {"(^_^)","  |","|","--|","--|--"," /"," / \\"};
	private static String[] parts = new String[] {"Head","Torso","Arm","Arm","Leg","Leg"};
	private static String[] word;
	private static String[] hiddenWord;
	private static ArrayList<String> guessed = new ArrayList<String>();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	static void run() {
		System.out.println("Welcome to Hangman!");
		prompt();
	}
	
	public static void prompt() {
		String input = "";
		System.out.print("Enter in a puzzle for someone else to solve. It can only have 1 to 8 words. ");
		
		do {
			try {
				input = reader.readLine();
				int userNum = Integer.parseInt(input);
				if (userNum != 0 || userNum == 0) {
					input = "";
					System.out.println("Only letters please.");
				}
			}catch(IOException e) {
				
			}catch(NumberFormatException e) {
				
			}
			if (input.equals(" ")) {
				System.out.print("Please enter in a word or words. ");
				input = "";
			}
			if (input.contains("  ")) {
				System.out.println("Seperate words with only one space please.");
				input = "";
			}
			if (!validInput(input)) {
				input = "";
				System.out.print("Please enter in a puzzle that has 1 to 8 words. ");
			}
			else {
				hiddenWord = new String[input.length()];
				word = new String[input.length()];
				for (int i = 0; i < hiddenWord.length; i++) {
					if (input.charAt(i) != '.' && input.charAt(i) != ',' && input.charAt(i) != '/' && input.charAt(i) != '-' && 
							input.charAt(i) != '\'' && input.charAt(i) != '\\' && input.charAt(i) != '!' && input.charAt(i) != '?' &&
							input.charAt(i) != ':' && input.charAt(i) != ' ') {
					hiddenWord[i] = "_";
					word[i] = input.charAt(i) + "";
					}
					else {
						word[i] = input.charAt(i) + "";
						hiddenWord[i] = input.charAt(i) + "";
					}
				}
			}
		}while(input.isEmpty());
		System.out.println("");
		display(false);
	}
	
	public static boolean validInput(String input) {
		int words = input.length() - input.replace(" ", "").length() + 1;
		if (words >= 1 && words <= 8 && !input.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void display(boolean finished) {
		String body = "Current body parts: ";
		String letters = "Letters Guessed: ";
		String currentPuzzle = "";
		
		for (int i = 0; i < hiddenWord.length; i++) {
			if (hiddenWord.length - i == 2)
				currentPuzzle += hiddenWord[i] + " ";
			else
				currentPuzzle += hiddenWord[i] + " ";
		}
		
		currentPuzzle = currentPuzzle.replace(" ,", ",");
		currentPuzzle = currentPuzzle.replace(" \' ", "\'");
		currentPuzzle = currentPuzzle.replace(" .", ".");
		currentPuzzle = currentPuzzle.replace(" !", "!");
		currentPuzzle = currentPuzzle.replace(" / ", "/");
		currentPuzzle = currentPuzzle.replace(" ?", "?");
		currentPuzzle = currentPuzzle.replace(" -", "-");
		currentPuzzle = currentPuzzle.replace("   ", "  ");
		
		for (int i = 0; i < 6 - lives ; i++) {
				body += parts[i] + ", ";
		}
		
		if (lives == 5) {
			System.out.println(image[0]);
		}
		else if (lives == 4) {
			System.out.println(image[0]);
			System.out.println(image[2]);
			System.out.println(image[1]);
		}
		else if (lives == 3) {
			System.out.println(image[0]);
			System.out.println(image[3]);
			System.out.println(image[1]);
		}
		else if (lives == 2) {
			System.out.println(image[0]);
			System.out.println(image[4]);
			System.out.println(image[1]);
		}
		else if (lives == 1) {
			System.out.println(image[0]);
			System.out.println(image[4]);
			System.out.println(image[1]);
			System.out.println(image[5]);
		}
		else if (lives == 0) {
			System.out.println(image[0]);
			System.out.println(image[4]);
			System.out.println(image[1]);
			System.out.println(image[6]);
		}
		
		System.out.println("");
		
		for (int i = 0; i < guessed.size(); i++) {
			letters += guessed.get(i) + ", ";
		}
		
		System.out.println(body);
		System.out.println("Puzzle:" + currentPuzzle);
		System.out.println(letters);
		System.out.println("");
		
		if (!finished) {
		if (forced()) {
			System.out.println("You have to solve because there is only 1 more letter.");
			solve();
		}
		else {
		System.out.println("Okay player, you can guess a letter, solve the puzzle, or give up (Type the number).\n1. Guess\n2. Solve\n3. Give Up ");
			newRound();
		}
		}
	}
	
	public static void newRound() {
		
		String input = "";
		int menu = 0;
		do {
			try {
				input = reader.readLine();
				menu = Integer.parseInt(input);
			}catch(IOException e) {
				
			}catch(NumberFormatException e) {
				System.out.print("Enter in a number. ");
			}
			
			if (menu < 1 || menu > 3) {
				input = "";
				System.out.print("The number has to be between 1 and 3. ");
			}
		}while(input.isEmpty());
		
		switch (menu) {
		case 1:
			guess();
			break;
		case 2:
			solve();
			break;
		case 3:
			lose();
			break;
		default:
			System.out.println("SOMETHING IS BROKEN!");
			break;
		}
	}
	
	public static boolean forced() {
		String wordd = "";
		for (int i = 0; i < hiddenWord.length; i++) {
			wordd += hiddenWord[i];
		}
		int diff = hiddenWord.length - wordd.replace("_", "").length();
		if (diff == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void guess() {
		String input = "";
		System.out.print("Enter in a single letter. ");
		
		do {
			try {
				input = reader.readLine().replaceAll(" ", "");
				int userNum = Integer.parseInt(input);
				if (userNum != 0 || userNum == 0) {
					input = "";
					System.out.print("Only letters please. ");
				}
			}catch(IOException e) {
				
			}catch(NumberFormatException e) {
				
			}
			
			if (input.isEmpty()) {
				System.out.print("Please enter in a letter. ");
			}
		}while(input.isEmpty());
		
		if (!guessed.contains(input)) {
		guessed.add(input);
		int count = 0;
		int number = 0;
		for (int i = 0; i < word.length; i++) {
			if (word[i].equalsIgnoreCase(input)) {
				hiddenWord[i] = word[i];
				number++;
			}
			else {
				count++;
			}
		}
		System.out.println("Wow! There were " + number + " " + input + "'s!");
		boolean win = true;
		for (int i = 0; i < hiddenWord.length; i++) {
			if (!hiddenWord[i].equalsIgnoreCase(word[i])) {
				win = false;
			}
		}
		if (win) {
			win();
		}
		if (word.length - count  <= 0) {
		lives--;
		if (lives <= -1)
			lose();
		else
			System.out.println("Oops, the puzzle doesn't have that letter.");
		}
		System.out.println("");
		display(false);
		}
		else {
			System.out.println("You can't guess " + input + ", because you already guessed that letter.");
			guess();
		}
	}
	
	public static void solve() {
		String input = "";
		System.out.print("Oh ho! Tough guy! What do you think the solution is? ");
		
		do {
			try {
				input = reader.readLine();
				int userNum = Integer.parseInt(input);
				if (userNum != 0 || userNum == 0) {
					input = "";
					System.out.print("Only letters please. ");
				}
			}catch(IOException e) {
				
			}catch(NumberFormatException e) {
				
			}
			
			if (input.length() != word.length) {
				input = "";
				System.out.println("You don't even have the right amount of letters.");
			}
			if (input.isEmpty()) {
				System.out.print("Please enter in a letter. ");
			}
		}while(input.isEmpty());
		
		String guessWord = "";
		for (int i = 0; i < word.length; i++) {
			guessWord += word[i];
		}
		if (guessWord.equalsIgnoreCase(input))
			win();
		else
			lose();
	}
	
	public static void win() {
		String wordd = "";
		for (int i = 0; i < word.length; i++) {
			wordd += word[i];
			hiddenWord[i] = word[i];
		}
		System.out.println("You win! The word was: " + wordd);
		display(true);
		playAgain();
	}
	
	public static void lose() {
		lives = 0;
		String wordd = "";
		for (int i = 0; i < word.length; i++) {
			wordd += word[i];
		}
		System.out.println("");
		System.out.println("You lost... The word was: " + wordd);
		display(true);
		playAgain();
	}
	
	public static void playAgain() {
		int answer = 0;
		System.out.println("Would you like to play again?(Type in the number)\n1. Yes\n2. No");
		do {
			try {
				String input = reader.readLine();
				answer = Integer.parseInt(input);
			}catch(IOException e) {
				
			}catch(NumberFormatException e) {
				System.out.print("Enter in a valid number please. ");
			}
			
			if (answer < 1 || answer > 2) {
				answer = 0;
				System.out.print("The number has to be either 1 or 2. ");
			}
		}while(answer == 0);
		
		switch (answer) {
		case 1:
			lives = 6;
			guessed.clear();
			prompt();
			break;
		case 2:
			System.out.println("Thanks for making me not use any more processing power.");
			System.out.print("Closing application...");
			break;
		default:
			System.out.println("SOMETHING BROKE!");
			break;
		}
	}
}
