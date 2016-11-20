import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;




public class Tester {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FileReader file = new FileReader("alice-in-wonderland.txt");

		Book book = new Book(file.getLines());
				
		Parser parser = new Parser(book);
		
//		ArrayList<String> letters = parser.getLetters();
		
//		ArrayList<String> words = parser.getWords();
		
		ArrayList<String> quotations = parser.getQuotations();		

				
		System.out.println("Done");
		
	}

}

