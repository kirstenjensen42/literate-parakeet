import java.util.InputMismatchException;
import java.util.Scanner;

public class Tester {
	
	
	static String welcome = "Welcome to Literate Parakeet. " +
			"You can analyze the following books:\n" +
			"1. Alice in Wonderland\n" +
			"2. The Christmas Carol\n" +
			"3. The Adventures of Huckleberry Finn\n" +
			"4. Les Mis\n" +
			"5. Metamorposis\n" +
			"6. My Man Jeeves\n" +
			"7. Pride and Prejudice\n" +
			"8. A Tale of Two Cities\n" +
			"9. Tom Sawyer\n" +
			"10. I provided my own file\n" +
			"===============================\n";
	
	static String tasks = "1. Find the top ten most frequently occurring letters\n" +
			"2. Find the top ten most frequently occurring words\n" +
			"3. Find the top ten most frequently occurring words with stop list words removed\n" +
			"4. Find the 10 longest quotes\n" +
			"5. Find the 10 shortest quotes\n" +
			"6. Get a list of frequently occurring characters\n" +
			"7. I'm done.";
	
	static String askAgain = "\nWould you like to analyze more about this book?\n" + 
			"\nEnter your choice or enter 8 to see the options again: ";
	
	static String bookTitle = "";
	static String theFile = "";
	static boolean awaitingChoice = true;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.print(welcome);
		Scanner in = new Scanner(System.in);
		
		// User selects which book to examine
		while ( bookTitle.equals("") ) {
			System.out.println("Enter your choice: ");
			
			try {
				int choice = in.nextInt();
				
				if ( choice == 1 ) {
					theFile = "alice-in-wonderland.txt";
					bookTitle = "Alice in Wonderland";
				} else if ( choice == 2 ) {
					theFile = "christmas-carol.txt";
					bookTitle = "The Christmas Carol";
				} else if ( choice == 3 ) {
					theFile = "huck-finn.txt";
					bookTitle = "Huckleberry Finn";
				} else if ( choice == 4 ) {
					theFile = "les-mis.txt";
					bookTitle = "Les Mis";
				} else if ( choice == 5 ) {
					theFile = "metamorphosis.txt";
					bookTitle = "Metamorphosis";
				} else if ( choice == 6 ) {
					theFile = "my-man-jeeves.txt";
					bookTitle = "My Man Jeeves";
				} else if ( choice == 7 ) {
					theFile = "pride-prejudice.txt";
					bookTitle = "Pride and Prejudice";
				} else if ( choice == 8 ) {
					theFile = "tale-of-two-cities.txt";
					bookTitle = "A Tale of Two Cities";
				} else if ( choice == 9 ) {
					theFile = "tom-sawyer.txt";
					bookTitle = "Tom Sawyer";
				} else if ( choice == 10 ) {
					System.out.println("Your file must be located in the top folder of this java project. " +
							"Please enter the name of your file (including the extention): " );
					theFile = (in.next());
					bookTitle = "your own book contained in file " + theFile;
				} else {
					System.out.println("That is not a valid option. ");
				}
			} catch (InputMismatchException ime) {
				System.out.println("That is not a valid entry.");
				return;
			}
			} 
		

		FileReader file = new FileReader(theFile);		
		System.out.println("You have chosen " + bookTitle + "\n===============================\n");
		System.out.println("(One moment please...)");
		
		Book book = new Book(file.getLines());
		BookAnalyzer analyze = new BookAnalyzer(book);
		
		// User chooses how to examine the book
		System.out.println("How would you like to analyze this book?\n" + tasks);
		System.out.println("Enter your choice: ");
		while ( in.hasNext() ) {
			int task = in.nextInt();
			
			if ( task == 1 ) {
				System.out.print("\nOne moment please...\n");
				System.out.println(analyze.topTenLetters());
				System.out.print(askAgain);
			} else if ( task == 2 ) {
				System.out.print("\nOne moment please...\n");
				System.out.print(analyze.topTenWords());
				System.out.print(askAgain);
			} else if ( task == 3 ) {
				System.out.print("\nOne moment please...\n");
				System.out.print(analyze.topTenWordsWithStopList());
				System.out.print(askAgain);
			} else if ( task == 4 ) {
				System.out.print("\nOne moment please...\n");
				System.out.print(analyze.longestQuotes());
				System.out.print(askAgain);
			} else if ( task == 5 ) {
				System.out.print("\nOne moment please...\n");
				System.out.print(analyze.shortestQuotes());
				System.out.print(askAgain);
			} else if ( task == 6 ) {
				System.out.print("\nOne moment please...\n");
				System.out.print(analyze.getCharacters());
				System.out.print(askAgain);
			} else if ( task == 7 ) {
				break;
			} else if ( task == 8 ) {
				System.out.print(tasks);
				System.out.println("\nEnter your choice: ");
			} else {
				System.out.print("\nThat is not a valid responce.\n");
				System.out.print(askAgain);
			}
		}	

		
		System.out.println("\nGo read a book and have a great day!");
		
	}

}

