import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the book class. It takes a ArrayList of Strings (such as one parsed by the FileReader) and 
 * uses a method to adjust the ArrayList so that each index contains a String of one complete 
 * paragraph.
 * @author kirstenjensen42
 *
 */
public class Book {
	
	private ArrayList<String> text;
	
	/**
	 * This is the constructor for the Book class. It takes an argument of an ArrayList of Strings 
	 * and calls the getParagraph method.
	 * @param lines
	 */
	public Book( ArrayList<String> lines ) {
		text = lines;
		
		getParagraphs();
		
	}
	
	
	/**
	 * This constructor builds a "sample book"
	 */
//	public Book () {
//	
//		text = new ArrayList<String>();
//		text.add("Chapter 1");
//		text.add("'It was one of those cases where you approve the broad, general principle of an idea " +
//				"but can't help being in a bit of a twitter at the prospect of putting it into practical effect. " + 
//				"I explained this to Jeeves, and he said much the same thing had bothered Hamlet.'");
//		text.add("Chapter 2");
//		text.add("There was quiet satifaction in the butler's voice. It was even possible, "
//				+ "he was reflecting, that this young man might be struck by lightning. If so, " +
//				"it was all right with Beach. As far as he was concerned, Nature's awful majesty " +
//				"could go to the limit.");
//		text.add("Chapter 3");
//		text.add("I mean, imagine how some _unfortunate Master Criminal_ would feel, on coming down " +
//				"to do a murder at the old Grange, if he found that not only was Sherlock Holmes " +
//				"putting in the weekend there, but Hercule Poirot, as well.");
//		text.add("Chapter 4");
//		text.add("I mean, if you're asking a fellow to come out of a room so that you can " +
//				"dismember him with a carving knife, it's absurd to tack a 'sir' on to " +
//				"every sentence. The two things don't go together.");
//		text.add("Chapter 5");
//		text.add("Mix me a b.-and-s., Jeeves. I feel weak.");
//
//		
//		
//	}
	
	/**
	 * The getParagraph method scans each element in the ArrayList and determines paragraph breaks 
	 * by searching for and combining lines with non-white-space characters. Because of the formatting 
	 * of the books paragraphs are always separated by a line with no characters (which is read in 
	 * and set as an empty String by the file reader). After this method combines the text into 
	 * Strings of one full paragraph each it deletes all indexes containing only an empty String. 
	 */
	private void getParagraphs() {
		
		// The following Regex pattern is used to find all lines containing non-white-space characters.
		Pattern pattern = Pattern.compile(".*\\S.*");
		
		int k = 0;
		
		// In this while loop if index k and the following line (k+1) contain any non-white-space 
		// characters they are combined in index k and index k+1 is deleted. As this loop evaluates 
		// k+1 it stops when k is one less than the last element.
		while ( k < text.size()-1 ) {
			
			text.set(k, text.get(k).trim());
			text.set(k+1, text.get(k+1).trim());
			
			Matcher matchk = pattern.matcher(text.get(k));
			
			Matcher matchkPlus1 = pattern.matcher(text.get(k+1));
			
			// If the String at index k and the String after it both have characters the Strings are 
			// combined under index k and the element at index k+1 is removed.
			if ( matchk.matches() && matchkPlus1.matches() ) {
				text.set(k, text.get(k) + " " + text.get(k+1));
				text.remove(k+1);
				
			// Note: k is not advanced as one index has been removed and k+1 is now a new element to 
			// evaluate as k+1
				
			} else {
				// if a line does not contain a non-white-space character nothing is done to it and 
				// k is advanced by one.
				k++;
			}
		}
		
		// The following iterates through the ArrayList one more time, this time removing all empty 
		// lines.
		int j = 0;
		while ( j < text.size() ) {
			if ( text.get(j).equalsIgnoreCase("") ) {
				text.remove(j);
				
				// when a blank line is removed index j will hold the next line, so j is not advanced.
				
			} else {
				// if line is not blank it is left alone and j is advanced by 1.
				j++;
			}
		}
	}
	
	
	
	
	/// GETTER METHODS ///

	/**
	 * The getter method for the ArrayList of Strings containing the text of a book.
	 * @return the text as an index of Strings. Each index holds one complete paragraph.
	 */
	public ArrayList<String> getText() {
		return text;
	}
}
