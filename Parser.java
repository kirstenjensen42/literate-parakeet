import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * The Parser class. This class takes in Book objects and through various methods will parse them 
 * by letter, word, quote, and such.
 * @author kirstenjensen42
 *
 */
public class Parser {
	
	private Book book;
	
	/**
	 * This is the constructor for the Parser class. It takes a book as an argument.
	 * The book in an instance of the parser class can be parsed in various ways through its 
	 * public methods. 
	 * @param book the Book object to be parsed
	 */
	public Parser( Book book ) {
		this.book = book;
		
	}
	

	public ArrayList<String> getLetters() {
		
		ArrayList<String> letters = new ArrayList<String>();

		// for each element of the Book object
		for ( int b = 0; b < book.getText().size(); b++ ) {
			
			// identify working String as text
			String text = book.getText().get(b);
			
			// convert String to array of chars
			char[] chars = text.toCharArray();
			
			// for each element in the array of chars
			for ( int i = 0; i < chars.length; i++ ) {
				String iChar = String.valueOf(chars[i]);
				
				// if the element is a word character
				if (  iChar.matches("(\\w)")  ) {
					
					// and if it isn't an underscore
					if ( !iChar.equals("_") && iChar.matches("(\\D)") ) {
						
						// add it to the ArrayList
						letters.add(iChar);
					}
				}
			}
		}
		return letters;
	}
	
	
	
	public ArrayList<String> getWords() {
		ArrayList<String> words = new ArrayList<String>();
		Pattern betweenWords = Pattern.compile(" ");
		
		// for each element of the Book object...
		for ( int b = 0; b < book.getText().size(); b++ ) {
			
			// we're going to refer to that element as "text" for a bit to make things simple
			String text = " " + book.getText().get(b) + " ";
			
			// now we'll convert that element (it's a String representing a paragraph) to:
			//  - an array String objects, each element now representing a word
			
			// split at patterns: 
			//  - at least one non-word character
			String[] arrayOfWords = text.split("\\W+ +\\W+|\\W+ +| +\\W| +|");
			
			// for each element in the array of words...
			for ( int i = 0; i < arrayOfWords.length; i++ ) {
				
				// set the current element as the working item iWord
				String iWord = arrayOfWords[i];
				
				// if the element is not a digit
//				String alpha = iWord.substring(0,1);
				if (  !iWord.matches("\\d*")  ) {						
						// add it to the ArrayList
						words.add(iWord);
				}
			}
		}
		return words;

	}
	

}
