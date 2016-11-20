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
	
	/**
	 * This method  evaluates the Book object held by the instance of this Parser and 
	 * returns an ArrayList of Strings, each element holding a single letter character.
	 * 
	 * Every letter in the Book object will appear as many times in the ArrayList as it 
	 * appeared in the Book in the case (uppercase or lowercase) is appeared in the Book.
	 * 
	 * A letter is defined as a character appearing in the English alphabet.
	 * 
	 * A 
	 * @return
	 */
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
	
	
	/**
	 * This method evaluates the Book object held by the instance of this Parser and 
	 * returns an ArrayList of every individual word in the Book object. Each 
	 * word will appear as many times in the ArrayList as it appeared in the Book.
	 * Words found using a String.split() method using a Pattern with a regex expression explained below.
	 * 
	 * The resulting words will consist of: 
	 * 		- letters (lowercase or capital as they appeared in the book)
	 *  	- any punctuation occurring directly between letters (ie with no leading or trailing white-space)
	 * Note 1: any word ordinarily ending with an apostrophe or period 
	 *  	- a word such as "Mr." that ordinarily ends with a period will appear as "Mr"
	 *  	- a word such as "b.-and-s." will appear as "b.-and-s"
	 * 
	 * concerning digits:
	 * 		- the default is to not include digits
	 * 		- this can be changed by changing the boolean "noDigits" below to false
	 * 
	 * @return an ArrayList<String> of every occurrence of a letter in book
	 */
	public ArrayList<String> getWords() {
		
		////////// SET TO TRUE TO NOT INCLUDE DIGITS OR TRUE TO INCLUDE DIGITS //////////
		boolean noDigits = true;
		
		ArrayList<String> words = new ArrayList<String>();
		
		// for each element of the Book object...
		for ( int b = 0; b < book.getText().size(); b++ ) {
			
			// we're going to refer to that element as "text" for this loop
			// we're also adding a space character at the beginning and ending of the String
			// (this makes things more simple for when we look for regex patterns in a minute)
			String text = " " + book.getText().get(b) + " ";
			
			// now we'll convert that element (it's a String representing a paragraph) to:
			//  - an array String objects, each element now representing a word
			// split at patterns: 
			//  - at least one punctuation and at least one space and at least one punctuation
			//  - at least one punctuation and at least one space
			//  - at least one space and at least one punctuation
			String[] arrayOfWords = text.split("\\W+ +\\W+|\\W+ +| +\\W| +|--");
			
			// for each element in the array of words...
			for ( int i = 0; i < arrayOfWords.length; i++ ) {
				
				// we'll call this word iWord for this loop
				String iWord = arrayOfWords[i];
				
				// if the word starts with an underscore remove that underscore
				if ( iWord.startsWith("_") ) {
					iWord = iWord.substring(1);
				}
				// likewise if the word ends with an underscore
				if ( iWord.endsWith("_") ) {
					iWord = iWord.substring(0,iWord.length()-1);
				}
				
				//////////////// DON'T INCLUDE DIGITS //////////////////
				if ( noDigits ) {
					if (  !iWord.matches("\\d*")  ) {						
						// add it to the ArrayList
						words.add(iWord);
					}
				} else {
					words.add(iWord);
				}
			}
		}
		return words;

	}
	
	
	

}
