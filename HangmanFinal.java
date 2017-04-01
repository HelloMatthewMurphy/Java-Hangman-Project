import java.util.*;
public class HangmanFinal{
	
	//Categories of words and used words in category. Maybe global?? 
	public static String [] Sports = {"hurling" , "football", "swimming", "golf", "rugby", "cricket", "tennis", "racing", "basketball", "skating"};	
	public static String [] History= {"revolution", "war", "", "amend", "compromise", "constitution", "history", "culture", "knowledge", "independance"};
	public static String [] Maths  = {"number", "calculator", "algebra", "calculus", "integer", "fractions", "sets", "decimal", "probability", "boolean"};
	public static String [] TV     = {"tv", "horror", "comedy", "romance", "thriller", "action", "drama", "musical", "fantasy", "adventure"};
	public static String cat1 [];
	public static int tempNum = 0, sportsNum = 0, historyNum = 0, mathsNum = 0, tvNum = 0, wins = 0, losses = 0;
	
	//Asks the player to play and offer different modes
	public static void main(String [] args){
		
		//Asking to play co-op or single player
		Scanner keyboard = new Scanner(System.in);
		int play = validationAndPlay();
		boolean win = false;
		//Playing Single player and Multi player
		while (play == 1 || play == 2){
			win = playGame(play);
			if(win){
				wins++;
				play = validationAndPlay();
				
			}
			else{
				losses++;
				play = validationAndPlay();
			}
		}
		//Exit
		if (play == 3)
			System.out.println("Ok, bye!");
	}
	
	public static boolean playGame(int play){
		//Win graphic
		String WinMessage=	   "*   * " +" ***** " +" *   * " +"  "+ " * * * " +" * " +" **  * "+ " * " +
							"\n*   * "+" *   * "+" *   * " + "  "+" * * * " +" * "+" **  * "+ " * " +
							"\n * *  "+" *   * "+" *   * " + "  "+" * * * " +" * "+" * * * " + " * " +
							"\n  *   "+" *   * "+" *   * "+"  "+" * * * " + " * " + " *  ** " +
							"\n  *   "+" ***** "+" ***** "+"  "+"  * *  " + " * " + " *  ** "+ " * " ;
		//Graphics 
		String [] graphicHangman = {"+--+\n|  |\n|\n|\n|\n|\n|\n+--\n\nWord is:",
										"+--+\n|  |\n|  @\n|\n|\n|\n|\n+--\n\nWord is:",
										"+--+\n|  |\n|  @\n| /\n|\n|\n|\n+--\n\nWord is:",
										"+--+\n|  |\n|  @\n| / \\\n|\n|\n|\n+--\n\nWord is:",
										"+--+\n|  |\n|  @\n| /|\\\n|  |\n|\n|\n+--\n\nWord is:",
										"+--+\n|  |\n|  @\n| /|\\\n|  |\n| /\n|\n+--\n\nWord is:"};
		String [] usedLetters;
		Scanner input = new Scanner(System.in);
		
		//Prevents words from repeating because the array empty
		boolean sportsEmpty = false, historyEmpty = false, mathsEmpty = false, tvEmpty = false;
		
		//Generating word and not repeating
		String word = genWord(play, sportsEmpty, historyEmpty, mathsEmpty, tvEmpty);
		
		//=====>Convert word into dashes in array and create display version with spaces
		String [] dashes = new String [word.length()];
		for(int i = 0; i < word.length(); i++){
			dashes [i] = "-";
		}
		String hiddenWord = createDashes(dashes, word);
		
		//Loops the game and prints each graphic 
		boolean win2 = false, done = false;
		
		for(int i = 0; i < graphicHangman.length && !win2; ){
			System.out.println("You have " + (graphicHangman.length - i) + " left.");
			System.out.println(graphicHangman[i] + hiddenWord + "\nEnter character(or guess the word):");
			String charInput = input.nextLine();
			
			boolean val = valadation(charInput, word);
			//checking if letter is right.
			
			if (val){
				//check if it is in word
				dashes = replace(hiddenWord, charInput, word, dashes);
				hiddenWord = createDashes (dashes, word);
				done = false;
				//Checking if letter is in word
				boolean correctLetter = false;
				if (word.contains(charInput))
					correctLetter = true;
				else{
					correctLetter = false;
					i++;
				}
				//This will check if you have won
				if(correctLetter){
					if (charInput.length() > 1)
						win2 = true;
					else{
						for(int j = 0; j < word.length() && !done; j++ ){
							win2 = false;
							if (dashes [j] != "-")
								win2 = true;
							else
								done = true;
						}
					}
					}
				}
				else{
					i++;
					System.out.println("Only letters are allowed.");
				}
			}
			if (win2){
				System.out.println("Correct!!!!");
				System.out.println(WinMessage);
				cat1 [tempNum] = "";
				return true;
			}
			else{
				System.out.println("+--+\n|  |\n|  @\n| /|\\\n|  |\n| / \\\n|\n+--\n" + "The word was " + word);
				return false;
			}
			
	}
	//This method will create the dashes for the selected random word
	public static String createDashes(String [] dashes, String word){
		String hiddenWord = "";
		for(int i = 0; i < word.length(); i++){
			hiddenWord += dashes[i] + " ";
		}
		return hiddenWord;
	}
	
	//This method will pick the word and stop repeat words from being used. Error when it runs out of words
	//Also will allow a word to be imputed if it is multi player
	public static String genWord(int play, boolean sportsEmpty, boolean historyEmpty, boolean mathsEmpty, boolean tvEmpty){
		String word = "";
		if (play == 1){
			cat1 = chooseCategories(sportsEmpty, historyEmpty, mathsEmpty, tvEmpty);
			for (int i = 0; word.length() == 0 && i < cat1.length; i++){
				int randomNum = (int)(Math.random() * cat1.length);
				word = cat1[randomNum];
				tempNum = randomNum;
			}
			if (word.length() == 0)
				System.out.println("All words in this category used. Please select a different category or you can play this one again."); //THIS!!!!!!!!!!!!!!!!!!!!!!!
		}
		//This is the multi player section
		else{
			for (int i = 0; !word.matches("[a-z]{3,15}") ; i ++){
				if (i > 0)
					System.out.println("Error: Must be 3 to 15 letters, no symbols, spaces, capitals or numbers");
				Scanner userInput = new Scanner(System.in);
				System.out.println("OK Guessing Player, turn around while your friend enters the word to guess!\n");
				System.out.println("Other Player, enter your word(3 to 15 letters, no symbols, spaces or numbers): ");
				word = userInput.next();
				System.out.println("\nMake the size of the window small enough so they cant see the word you entered before telling them to turn around.\n");
			}
		}
		return word;
	}
	// Validates if letter inputted is a letter and checks if it's a word
	public static boolean valadation(String ch, String word){
		int chLength = ch.length();
		ch = ch.toLowerCase();
		word = word.toLowerCase();
		if (chLength == 1){
			String abc = "abcdefghijklmnopqrstuvwxyz";
			int x = abc.indexOf(ch);
			if(x == -1)
				return false;
			else
				return true;
		}
		else if (chLength > 1){
			//This is if they guess a word		
			if(ch.equalsIgnoreCase(word))
				return true;
			else 
				return false;
		}
		else 
			return false;
		
	}
	//Checks menu selections and menu interface
	public static int validationAndPlay(){
		boolean validInput = false;
		int option = 0;
		while (!validInput)
		{
			Scanner keyboard = new Scanner(System.in);
			System.out.println("-------Enter number for selection-------\n\tWins: " + wins + "\tlosses: " + losses + "\n1. Single player\n2. Multi player\n3. Exit");
			String playChoice = keyboard.nextLine(), sPlayer = "1", mPlayer = "2", exit = "3";
			if (playChoice.contentEquals(sPlayer)) {
				validInput=true;
				option = 1;
			}
			else if (playChoice.contentEquals(mPlayer)){
				validInput=true;
				option = 2;
			}
			else if (playChoice.contentEquals(exit)){
				validInput=true;
				option = 3;
			}
			else{
				// Must be invalid input
				System.out.println("Invalid Input");
				validInput=false;
			}
		}
		return option;
	}
	//Picks the categories
	public static String[] chooseCategories(boolean sportsEmpty, boolean historyEmpty, boolean mathsEmpty, boolean tvEmpty){
		String [] choice = Sports;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("------------Choose Category------------");
		boolean correctCat = false;
		while (!correctCat){
			System.out.println("1. Sport\n2. History\n3. Maths\n4. TV");
			String input = keyboard.nextLine(), correctInput [] = {"1", "2", "3", "4"};
			for(int i = 0; i < 4; i++){
				if (input.contentEquals(correctInput[i])){
					int categoryChoice = Integer.parseInt(input);
					if (categoryChoice == 1){
						choice = Sports; 
						if (sportsEmpty == false && sportsNum < 10){
							correctCat = true; 
							sportsNum++;
						}
						else 
							System.out.println("Every word in Sports has been used.");
					}
					else if (categoryChoice == 2){
						choice = History; 
						if (historyEmpty == false && historyNum < 10){
							correctCat = true;
							historyNum++;
						}
						else 
							System.out.println("Every word in History has been used.");
					}
					else if (categoryChoice == 3){
						choice = Maths;
						if (mathsEmpty == false && mathsNum < 10){
							correctCat = true;
							mathsNum++;
						}
						else 
							System.out.println("Every word in Maths has been used.");
					}
					else {
						choice = TV;
						if (tvEmpty == false && tvNum < 10){
							correctCat = true;
							tvNum++;
						}
						else 
							System.out.println("Every word in TV has been used.");
					}
				}
			}
		}
		return choice;
	}
	//Replaces dashes with letters if it is correct
	public static String [] replace(String hiddenWord, String charInput, String word, String [] dashes){
		for(int i = 0; i < word.length(); i++ ){
			charInput = charInput.toLowerCase();
			int x = word.indexOf(charInput, i);
			if(x > -1){
				dashes [x] = charInput;
			}
		}
		return dashes;
	}
	
}