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
		
		FileReader file = new FileReader("quotes.txt");

		Book book = new Book(file.getLines());
				
//		Parser parser = new Parser(book);
				
		Pattern allDoubleQuotes = Pattern.compile("[\"](.*)[\"]");
		Pattern allSingleQuotes = Pattern.compile("[\'](.*)[\']");
		Pattern startsDoubleQuotes = Pattern.compile("[\"](.*)[\"].*");
		Pattern startsSingleQuotes = Pattern.compile("[\'](.*)[\'].*");

		
		
//		String text = book.getText().get(0);
//		System.out.println(text);
//		text = text.replaceFirst("[^']*\\B'", "'");
//		System.out.println(text);
		

		
		ArrayList<String> quotes = new ArrayList<String>();
		
		for (int i = 0; i < book.getText().size(); i++) {
			
			boolean americanStyleQuotes = true;
			
			String text = book.getText().get(i);
			
			////// Format: "...."
			if ( book.getText().get(i).startsWith("\"") && book.getText().get(i).endsWith("\"") ) {
				americanStyleQuotes = true;
				Matcher match = allDoubleQuotes.matcher(text);
				quotes.add("All DQ:" + text);
				if (match.matches()) {
					text = match.group(1);
					quotes.add("   Second match: " + text);
					text = text.replaceAll("[\"](.*)[\"]", " ");
					quotes.add("   After replaceAll: " + text);					
					book.getText().remove(i);
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
			
			///// Format '....'
			} else if (book.getText().get(i).startsWith("\'") && book.getText().get(i).endsWith("\'")) {
				americanStyleQuotes = true;
				Matcher match = allSingleQuotes.matcher(text);
				quotes.add("All SQ:" + text);
				if (match.matches()) {
					text = match.group(1);
					quotes.add("   Second match: " + text);
					text = text.replaceAll("[\'](.*)[\']", " ");
					quotes.add("   After replaceAll: " + text);					
					book.getText().remove(i);
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
				
			/// Format "..."...
			} else if ( book.getText().get(i).startsWith("\"") ) {
				quotes.add("Starts with DQ:" + text);
				Matcher match = startsDoubleQuotes.matcher(text);
				if (match.matches()) {
					text = match.group(1);
					quotes.add("   Second match: " + text);
					text = text.replaceAll("[\"](.*)[\"]", " ");
					quotes.add("   After replaceAll: " + text);					
					book.getText().remove(i);
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
				
				
			} else if ( book.getText().get(i).startsWith("\'") ) {
				quotes.add("Starts with SQ:" + text);
				Matcher match = startsSingleQuotes.matcher(text);
				if (match.matches()) {
					text = match.group(1);
					quotes.add("   Second match: " + text);
					text = text.replaceAll("[\'](.*)[\']", " ");
					quotes.add("   After replaceAll: " + text);					
					book.getText().remove(i);
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
					
			} else if (americanStyleQuotes) {
				quotes.add("DQ in the middle: " + text);
				text = text.replaceFirst("[^\"]*\\B\"", "\"");
				quotes.add("    cut off beginning:" + text);
				Matcher match = startsDoubleQuotes.matcher(text);
				if (match.matches()) {
					text = match.group(1);
					quotes.add("   Second match: " + text);
					text = text.replaceAll("\"+\\W+[^\"]*\\W+\"+", " ");
					quotes.add("   After replaceAll: " + text);					
					book.getText().remove(i);
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
			} else if (!americanStyleQuotes) {
				quotes.add("SQ in the middle: " + text);
				text = text.replaceFirst("[^']*\\B'", "'");
				quotes.add("    cut off beginning:" + text);
				Matcher match = startsSingleQuotes.matcher(text);
				if (match.matches()) {
					text = match.group(1);
					quotes.add("   Second match: " + text);
					text = text.replaceAll("'+\\W+[^']*\\W+'+", " ");
					quotes.add("   After replaceAll: " + text);					
					book.getText().remove(i);
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
			}
		}
		
		for (int k=0; k<quotes.size(); k++) {
			System.out.println(quotes.get(k));
		}
		
		
				
		System.out.println("Done");
		

		
		
	}

}













//String text = "There was quiet satifaction in the butler's voice. It was even possible, "
//		+ "he was reflecting, that this young man might be struck by lightning. If so, " +
//		"it was all right with Beach. As far as he was concerned, Nature's awful majesty " +
//		"could go to the limit.";
//
//HashMap< String, Integer > occurances = new HashMap< String, Integer >();
//
//ArrayList<String> letters = new ArrayList<String>();
//char[] chars = text.toCharArray();
//for ( int i = 0; i < chars.length; i++ ) {
//	if (   String.valueOf(chars[i]).matches( "(\\w)" )   ) {
//		letters.add(String.valueOf(chars[i]));
//	} else {
//		letters.add("*" + String.valueOf(chars[i]));
//	}
//}
//
//for ( int i = 0; i < letters.size(); i++ ) {
//	String iKey = letters.get(i);
//	if ( occurances.containsKey(iKey) ) {
//		occurances.put(iKey, occurances.get(iKey) + 1);
//	} else 
//		occurances.put(iKey,1);
//}
//
////for ( String key : occurances.keySet() ) {
////	System.out.println( key + ": " + occurances.get(key) );
////}

