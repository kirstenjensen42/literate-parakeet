import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * The Parser class. This class takes in Book objects and through various methods will parse them 
 * by letter, word, quote, and such.
 * @author kirstenjensen42
 *
 */
public class Parser {
		
	private Book book;
	
	private ArrayList<String> quotes;
	
	/*
	 * The following patterns and Strings are used to match regular expression patterns in the text.
	 * All patterns are done twice, once for texts that use double quotation marks as the 
	 * primary token for dialogue, and once for text that use single quotation marks as such.
	 * (This is subsequently referred to as American (double) and British (single) style.)
	 */ 
	private Pattern allDoubleQuotes = Pattern.compile("[\"](.*)[\"]");
	private Pattern allSingleQuotes = Pattern.compile("[\'](.*)[\']");
	private Pattern startsDoubleQuotes = Pattern.compile("[\"](.*)[\"].*");
	private Pattern startsSingleQuotes = Pattern.compile("[\'](.*)[\'].*");

	// used to select text that is in but not part of a quotation such as "he said" from "'Hello,' he said"
	// (this pattern is such that it does not remove characters or words connected with an apostrophe)
	private String notQuotationDouble = "\"+\\W+[^\"]*\\B\"+\\b";
	private String notQuotationSingle = "'+\\W+[^']*\\B'+\\b";	
	
	private String leadingNonQuoteAmerican = "[^\"]*\\B\"";
	private String leadingNonQuoteBritish = "[^']*\\B'";
	
	
	
	
	/**
	 * CONSTRUCTOR
	 * 
	 * This is the constructor for the Parser class. It takes a book as an argument.
	 * The book in an instance of the parser class can be parsed in various ways through its 
	 * public methods. 
	 * @param book the Book object to be parsed
	 */
	public Parser( Book book ) {
		this.book = book;
		
	}
	
	/** 
	 * Get the PROPER NOUNS
	 * 
	 * This method offers an approximation of most of the main characters in the book by finding 
	 * words that begin with capital letters and do not follow a ". ". This removes words that 
	 * are capitalized because they begin as sentence.
	 * 
	 * Note: this also removes names that always follow a title abbreviation with a period, which means 
	 * Mr. Wooster will not be matched. There is a loss of some characters appearing at all on the list 
	 * because of this.
	 * @return
	 */
	public ArrayList<String> getProperNouns() {
		ArrayList<String> properNouns = new ArrayList<String>();
		Pattern capitalWord = Pattern.compile("[^\\W] ([A-Z]{1}[a-z]+ +[A-Z]*[a-z]*)");
		
		for ( int b = 0; b < book.getText().size(); b++ ) {
			Matcher match = capitalWord.matcher(book.getText().get(b));
			while (match.find()) {
				String check = match.group(1).replaceAll(" +[a-z]+\\w*\\W*", "");
				properNouns.add(check);
			}
		}
		return properNouns;
	}
	
	
	/**
	 * Get all the LETTERS!
	 * 
	 * This method  evaluates the Book object held by the instance of this Parser and 
	 * returns an ArrayList of Strings, each element holding a single letter character.
	 * 
	 * Every letter in the Book object will appear as many times in the ArrayList as it 
	 * appeared in the Book in the case (uppercase or lowercase) is appeared in the Book.
	 * 
	 * A letter is defined as a character appearing in the English alphabet.
	 * 
	 * @return an ArrayList of Strings, each String containing one letter character.
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
	 * Get all the WORDS!
	 * 
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
	
	
	/**
	 * Get all the QUOTES!
	 * 
	 * 
	 * 
	 * @return an ArrayList of Strings with each String containing one quote.
	 */
	public ArrayList<String> getQuotes() {
		

//		Pattern allDoubleQuotes = Pattern.compile("[\"](.*)[\"]");
//		Pattern allSingleQuotes = Pattern.compile("[\'](.*)[\']");
//		Pattern startsDoubleQuotes = Pattern.compile("[\"](.*)[\"].*");
//		Pattern startsSingleQuotes = Pattern.compile("[\'](.*)[\'].*");
//		String notQuotationDouble = "\"+\\W+[^\"]*\\B\"+\\b";
//		String notQuotationSingle = "'+\\W+[^']*\\B'+\\b";		
		
		quotes = new ArrayList<String>();
		
		// for every element in the book (ie for every paragraph)...
		for (int i = 0; i < book.getText().size(); i++) {
			
			/*
			 * Most of the books in this program use American style punctuation for quotes, but 
			 * not all. This boolean is assumed true but it is set as false if a paragraph 
			 * appears that starts and ends with single quotes.
			 */
			boolean americanStyleQuotes = true;

			// let's refer to the current String as "text"
			String text = book.getText().get(i);
			
			
			////// AMERICAN QUOTATIONS AT START & END
			////// if paragraph format is: "...." (starts and ends with double quotes)
			if ( text.startsWith("\"") && text.endsWith("\"") ) {
				
				americanStyleQuotes = true;
				
				Matcher match = allDoubleQuotes.matcher(text);
				if (match.matches()) {
					// the patterns are set to provide the desired match in match.group(1)
					text = match.group(1);
					touchUpAndAddAmerican(text, quotes);
				} else {
					// do nothing
				}
				
			////// BRITISH QUOTATIONS AT START & END		
			///// if paragraph format is: '....' (starts and ends with single quotation marks)	
			} else if (text.startsWith("\'") && text.endsWith("\'")) {
				
				americanStyleQuotes = false;
				
				Matcher match = allSingleQuotes.matcher(text);
				if (match.matches()) {
					// the patterns are set to provide the desired match in match.group(1)
					text = match.group(1);
					touchUpAndAddBritish(text, quotes);
				} else {
					// do nothing
				}
				
				
			////// AMERICAN QUOTATIONS AT START
			/// if paragraph format is: "..."... (starts with double quotes)	
			}  else if ( text.startsWith("\"") ) {
				
				/*
				 * The following counter, for loop, and if statement determine instances where a paragraph
				 * begins with a quotation mark and include no other quotation marks.
				 * This program handles such cases my taking them as a single, entire quote.
				 */
				int count = 0;
				for ( int h = 1; h < text.length(); h++ ) {
					if ( text.substring(h,h+1).equalsIgnoreCase("\"") ) {
						count++;
					}
				}
				if ( count == 0 ) {
					quotes.add(text + "\"");
					
				// For regular cases when a quotation is opened and closed in the paragraph:	
				} else {
					Matcher match = startsDoubleQuotes.matcher(text);
					if (match.matches()) {
						// the patterns are set to provide the desired match in match.group(1)
						text = match.group(1);
						touchUpAndAddAmerican(text, quotes);	
					} else {
						// do nothing
					}
				}
				
			////// BRITISH QUOTATIONS AT START
			/// if paragraph format is: '...'... (starts with single quotes)					
			} else if ( text.startsWith("\'") ) {
				
				
				/*
				 * The following counter, for loop, and if statement determine instances where a paragraph
				 * begins with a quotation mark and include no other quotation marks.
				 * This program handles such cases my taking them as a single, entire quote.
				 * 
				 * A particular note for British style quotations: apostrophes within a word will not 
				 * be parsed, but if an apostrophe falls at the end of a word it will be treated as a 
				 * quotation mark.
				 */
				int count = 0;
				for ( int h = 1; h < text.length()-1; h++ ) {
					if ( text.substring(h,h+2).equalsIgnoreCase(" '") || text.substring(h,h+2).equalsIgnoreCase("' ") ) {
						count++;
					}
				}
				if ( count == 0 ) {
					quotes.add(text + "'");
					
				// For regular cases when a quotation is opened and closed in the paragraph:	
				} else {
					Matcher match = startsSingleQuotes.matcher(text);
					if (match.matches()) {
						// the patterns are set to provide the desired match in match.group(1)
						text = match.group(1);
						touchUpAndAddBritish(text, quotes);	
					} else {
						// do nothing
					}
				}
			
			/*
			 * AMERICAN QUOTATIONS WITHIN
			 * if paragraph format is: ...."....".... (contains double quotes within it)
			 *
			 * The getQuotes method uses the presence of paragraphs in the text that start and 
			 * end with double or single quotes to set the boolean americanStyleQuotes.
			 * 
			 * True is the default setting.
			 * 
			 * (NOTE: In some fringe cases this setting could be incorrect for the text being scanned 
			 * and the quotations from that paragraph would not be pulled correctly, but cases 
			 * will be few and the total effect of those cases will be confined to the parsing 
			 * of only one paragraph.)
			 * 
			 */
			} else if (americanStyleQuotes) {
				
				
				// remove the first part of the paragraph all the way up to the beginning of the quotation
				text = text.replaceFirst(leadingNonQuoteAmerican, "\"");
				
				// after the non-quotation part is removed the element can be handled in the 
				// same way as one that started with a quotation mark to begin with
				Matcher match = startsDoubleQuotes.matcher(text);
				if (match.matches()) {
					// the patterns are set to provide the desired match in match.group(1)
					text = match.group(1);
					touchUpAndAddAmerican(text, quotes);	
				} else {
					// do nothing
				}

				
			// BRITISH QUOTATIONS WITHIN	
			// if paragraph format is: ....'....'.... (contains single quotes within it)
			} else if (!americanStyleQuotes) {
				// remove the first part of the paragraph all the way up to the beginning of the quotation
				text = text.replaceFirst(leadingNonQuoteBritish, "'");
				
				// after the non-quotation part is removed the element can be handled in the 
				// same way as one that started with a quotation mark to begin with
				Matcher match = startsSingleQuotes.matcher(text);
				if (match.matches()) {
					// the patterns are set to provide the desired match in match.group(1)
					text = match.group(1);
					touchUpAndAddBritish(text, quotes);	
				} else {
					// do nothing
				}	
			}
		}
		return quotes;
	}
	
	
	// These steps are performed frequently above
	private void touchUpAndAddAmerican(String text, ArrayList<String> quotes) {
		text = text.replaceAll(notQuotationDouble, " ");
		quotes.add("\"" + text + "\"");	
	}
	private void touchUpAndAddBritish(String text, ArrayList<String> quotes) {
		text = text.replaceAll(notQuotationSingle, " ");
		quotes.add("'" + text + "'");	
	}
	
	
	
	

}
