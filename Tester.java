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
		
		FileReader file = new FileReader("Abridged.txt");
		
		Book book = new Book(file.getLines());
		
//		for ( int i = 0; i < book.getText().size(); i++ ) {
//			System.out.println(i + ": " + book.getText().get(i));
//		}
		
		String text = "There was quiet satifaction in the butler's voice. It was even possible, "
				+ "he was reflecting, that this young man might be struck by lightning. If so, " +
				"it was all right with Beach. As far as he was concerned, Nature's awful majesty " +
				"could go to the limit.";
		
		HashMap< String, Integer > occurances = new HashMap< String, Integer >();

//		Pattern pattern = Pattern.compile(".*\\S.*");
		ArrayList<String> letters = new ArrayList<String>();
		char[] chars = text.toCharArray();
		for ( int i = 0; i < chars.length; i++ ) {
			if (   String.valueOf(chars[i]).matches( "(\\w)" )   ) {
				letters.add(String.valueOf(chars[i]));
			} else {
				letters.add("*" + String.valueOf(chars[i]));
			}
		}
		
		for ( int i = 0; i < letters.size(); i++ ) {
			String iKey = letters.get(i);
			if ( occurances.containsKey(iKey) ) {
				occurances.put(iKey, occurances.get(iKey) + 1);
			} else 
				occurances.put(iKey,1);
		}
		
		for ( String key : occurances.keySet() ) {
			System.out.println( key + ": " + occurances.get(key) );
		}
		
	}

}
