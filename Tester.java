import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;




public class Tester {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FileReader file = new FileReader("Abridged.txt");
		
		Book book = new Book(file.getLines());
		
		for ( int i = 0; i < book.getText().size(); i++ ) {
			System.out.println(i + ": " + book.getText().get(i));
		}
		
		
		
	}

}
