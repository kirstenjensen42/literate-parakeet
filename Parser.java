import java.util.ArrayList;


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
	
	
	

	public ArrayList<String> letters() {
		
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
					if ( !iChar.equals("_") ) {
						
						// add it to the ArrayList
						letters.add(iChar);
					}
				}
			}
		}
		
		return letters;
		
	}
	
	

}
