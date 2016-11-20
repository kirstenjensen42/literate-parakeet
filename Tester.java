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
				
//		Parser parser = new Parser(book);

				

		Pattern allDoubleQuotes = Pattern.compile("[\"](.*)[\"]");
		Pattern allSingleQuotes = Pattern.compile("[\'](.*)[\']");
		Pattern startsDoubleQuotes = Pattern.compile("[\"](.*)[\"].*");
		Pattern startsSingleQuotes = Pattern.compile("[\'](.*)[\'].*");
		
		// used to select text that is in but not part of a quotation such as "he said" from "'Hello,' he said"
		// (this pattern is such that it does not remove characters or words connected with an apostrophe)
		// the first is used for British style quotations and the second is used for American style quotations
		String notQuotationSingle = "'+\\W+[^']*\\B'+\\b";
//		String notQuotationSingle = "'+\\W+[^']*[^\\w]+'+\\b";

		
//		String notQuotationSingle = "'+\\W+[^']*\\W+'+";
		String notQuotationDouble = "\"+\\W+[^\"]*\\W+\"+";
		
		
		ArrayList<String> quotes = new ArrayList<String>();
		Book instanceBook = new Book(book.getText());
		
		// for every element in the book (each element holds one paragraph as a string)...
		for (int i = 0; i < instanceBook.getText().size(); i++) {
			
			// Most of the books in this program use American style punctuation for quotes, but 
			// not all. This boolean is assumed true but it is set as false if a paragraph appears 
			// that starts and ends with single quotes.
			boolean americanStyleQuotes = true;
			
			// let's refer to the current String as "text"
			String text = instanceBook.getText().get(i);
			
			////// Format: "...." (starts and ends with double quotes)
			if ( text.startsWith("\"") && text.endsWith("\"") ) {
				
				// the presence of a paragraph that starts and ends with double quotes is 
				// used to indicate that the rest of the book will be in American style
				americanStyleQuotes = true;
				
				// compares to pattern set above
				Matcher match = allDoubleQuotes.matcher(text);
				if (match.matches()) {
					// the pattern was set to provide a match that lands in match.group(1)
					text = match.group(1);
					// replace all words that aren't part of one of the quotations with a space
					text = text.replaceAll(notQuotationDouble, " ");
					// add final version with quotation marks as an element in the ArrayList of quotes 
					quotes.add("\"" + text + "\"");	
					// remove element from Book to mark analysis is complete
					instanceBook.getText().remove(i);
					// count i back by one since an element was removed
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
			
			///// Format '....' (starts and ends with single quotation marks)
			} else if (text.startsWith("\'") && text.endsWith("\'")) {
				
				// the presence of a paragraph that starts and ends with single quotes is 
				// used to indicate that the rest of the book will be in British style
				americanStyleQuotes = false;
				Matcher match = allSingleQuotes.matcher(text);
				if (match.matches()) {
					// the pattern was set to provide a match that lands in match.group(1)
					text = match.group(1);
					quotes.add("Test: " + text);
					// replace all words that aren't part of one of the quotations with a space
					text = text.replaceAll(notQuotationSingle, " ");
					// add final version with quotation marks as an element in the ArrayList of quotes 
					quotes.add("'" + text + "'");	
					// remove element from Book to mark analysis is complete
					instanceBook.getText().remove(i);
					// count i back by one since an element was removed
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
				
			/// Format "..."... (starts with double quotes)
			} else if ( text.startsWith("\"") ) {
				
				// if the paragraph opens a quotation and no other quotation appears in the paragraph:
				// the entire paragraph is made into one quotation
				int count = 0;
				for ( int h = 1; h < text.length(); h++ ) {
					if ( text.substring(h,h+1).equalsIgnoreCase("\"") ) {
						count++;
					}
				}
				if ( count == 0 ) {
					quotes.add(text + "\"");
					instanceBook.getText().remove(i);
					i--;
				} else {
				
					Matcher match = startsDoubleQuotes.matcher(text);
					if (match.matches()) {
						// the pattern was set to provide a match that lands in match.group(1)
						text = match.group(1);
						// replace all words that aren't part of one of the quotations with a space
						text = text.replaceAll(notQuotationDouble, " ");
						// add final version with quotation marks as an element in the ArrayList of quotes 
						quotes.add("\"" + text + "\"");	
						// remove element from Book to mark analysis is complete
						instanceBook.getText().remove(i);
						// count i back by one since an element was removed
						i--;
					} else {
						quotes.add("   DIDN'T WORK!");
					}
				}
			/// Format '...'... (starts with single quotes)
			} else if ( text.startsWith("\'") ) {
				
				// if the paragraph opens a quotation and no other quotation appears in the paragraph:
				// the entire paragraph is made into one quotation
				int count = 0;
				for ( int h = 1; h < text.length(); h++ ) {
					if ( text.substring(h,h+1).equalsIgnoreCase("'") ) {
						count++;
					}
				}
				if ( count == 0 ) {
					quotes.add(text + "'");
					instanceBook.getText().remove(i);
					i--;
				} else {
				
					Matcher match = startsSingleQuotes.matcher(text);
					if (match.matches()) {
						// the pattern was set to provide a match that lands in match.group(1)
						text = match.group(1);
						// replace all words that aren't part of one of the quotations with a space
						text = text.replaceAll(notQuotationSingle, " ");
						// add final version with quotation marks as an element in the ArrayList of quotes 
						quotes.add("'" + text + "'");					
						instanceBook.getText().remove(i);
						i--;
					} else {
						quotes.add("   DIDN'T WORK!");
					}
				}
						
			} else if (americanStyleQuotes) {
				// remove the first part of the paragraph all the way up to the beginning of the quotation
				text = text.replaceFirst("[^\"]*\\B\"", "\"");
				// after the non-quotation part is removed the element can be handled in the 
				// same way as one that started with a quotation mark to begin with
				Matcher match = startsDoubleQuotes.matcher(text);
				if (match.matches()) {
					text = match.group(1);
					text = text.replaceAll(notQuotationDouble, " ");
					quotes.add("\"" + text + "\"");					
					instanceBook.getText().remove(i);
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
			} else if (!americanStyleQuotes) {
				// remove the first part of the paragraph all the way up to the beginning of the quotation
				text = text.replaceFirst("[^']*\\B'", "'");
				// after the non-quotation part is removed the element can be handled in the 
				// same way as one that started with a quotation mark to begin with
				Matcher match = startsSingleQuotes.matcher(text);
				if (match.matches()) {
					text = match.group(1);
					text = text.replaceAll(notQuotationSingle, " ");
					quotes.add("'" + text + "'");					
					instanceBook.getText().remove(i);
					i--;
				} else {
					quotes.add("   DIDN'T WORK!");
				}
			}
		}
		
		
				
		System.out.println("Done");
		

		
		
	}

}

