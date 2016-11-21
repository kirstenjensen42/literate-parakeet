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
			"10. I provided my own file\n";
	
	static String bookTitle = "";
	static String theFile = "";
	static boolean awaitingChoice = true;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.print(welcome);
		Scanner in = new Scanner(System.in);
		while ( bookTitle.equals("") ) {
			System.out.println("Enter your choice: ");
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
				System.out.print("That is not a valid option. ");
			} 
			} 


		FileReader file = new FileReader(theFile);
		
		System.out.println("You have chosen " + bookTitle);
		
		Book book = new Book(file.getLines());
		
		BookAnalyzer analyze = new BookAnalyzer(book);
		
		System.out.print(analyze.topTenWords());
		System.out.print(analyze.topTenLetters());

				
		System.out.println("Done");
		
	}

}

