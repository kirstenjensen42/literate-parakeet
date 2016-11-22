import java.util.ArrayList;
import java.util.HashMap;

/**
 * The BookAnalyzer class is the boss. It tells everyone else to do all the work. It does some 
 * work itself.
 * The BookAnalyzer class passes a book to the parser class to get compiled data (such as words, letters, 
 * and quotations) in the form of ArrayLists. It computes answers to the questions the program addresses 
 * and passes the answers back as a String when its public methods are called.
 * @author kirstenjensen42
 *
 */
public class BookAnalyzer {
	
	// used for the method that finds commonly used proper nouns. All proper nouns appearing more than 
	// this many times are given in a list.
	private final int CUT_OFF = 4;
	// used to calculate 10 ten, or top otherwise if you want to change it
	private final int TOP = 10;
	
	private Book book;
	private Parser parser;
	
	private FileReader file = new FileReader("stop-list.txt");
	private ArrayList<String> stopList = file.getLines();
	private ArrayList<String> letters;
	private ArrayList<String> words;
	private ArrayList<String> quotes;
	private ArrayList<String> properNouns;
	
	// data and topTen are reused after being cleared and refilled for each separate evaluation
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	private HashMap<Integer, Winner> topTen = new HashMap<Integer, Winner>();

	
	/**
	 * This is the constructor for BookAnalyzer. It takes an argument of a Book object. It instanciates 
	 * a Parser object passing it that Book and uses the Parser to populate it the data ArrayLists 
	 * it will analyze.
	 * @param book
	 */
	public BookAnalyzer( Book book ) {
		this.book = book;
		parser = new Parser( book );
		
		letters = parser.getLetters();
		words = parser.getWords();
		quotes = parser.getQuotes();
		properNouns = parser.getProperNouns();
		
		// I've added these to the stopList because they appear frequently in the prologue &ct and are 
		// not relevant to the actual book texts.
		stopList.add("Project Gutenberg");
		stopList.add("Archive Foundation");
		stopList.add("United States");
		stopList.add("Gutenberg-tm");
		stopList.add("License");
		stopList.add("Foundation");
		
	}
	
	/**
	 * This method is used to get the top 10 most used letters
	 * @return a String listing the top 10 letters with frequency of occurrence
	 */
	public String topTenLetters() {
		countOccurrences(letters);
		getTopTen();
		String list = "Top 10 Most Used Letters" + listTenWithFrequency();
		return list;
	}
	
	/**
	 * This method is used to get the actual top 10 words
	 * @return a String listing the top 10 words with frequency of occurrence
	 */
	public String topTenWords() {
		countOccurrences(words);
		getTopTen();
		String list = "Top 10 Most Used Words" + listTenWithFrequency();
		return list;
	}
	
	/**
	 * This method is used to get the top 10 words after the words on the stop list have been removed.
	 * @return a String listing the top 10 non-stop-list words with frequency of occurrence
	 */
	public String topTenWordsWithStopList() {
		takeOutStopList();
		getTopTen();
		String list = "Top 10 Most Used Words Excluding Stop List" + listTenWithFrequency();		
		return list;
	}
	
	/**
	 * This method is used to get the top 10 longest quotes
	 * @return a String listing the top 10 longest quotes from the book
	 */
	public String longestQuotes() {
		countLengths(quotes);
		getTopTen();
		String list = "Ten longest quotations:" + listTen();
		return list;	
	}
	
	/**
	 * This method is used to get the top 10 shortest quotes
	 * @return a String listing the top 10 shortest quotes from the book
	 */
	public String shortestQuotes() {
		countLengths(quotes);
		getBottomTen();
		String list = "Ten shortest quotations:" + listTen();
		return list;
	}
	
	
	/**
	 * This method is used to get the frequently occurring proper nouns in the book
	 * 
	 * A list is returned of proper nouns occurring more times than the value assigned to CUT_OFF
	 * 
	 * Words appearing on the stop list (to which I added some at the top) are removed.
	 * 
	 * Note: the stop list is not removed without regarding case, so most of the stop list words are 
	 * not removed at this point
	 * 
	 * @return a list of proper nouns occurring more times than the CUT_OFF defined above
	 */
	public String getCharacters() {
		data.clear();
		for ( int i = 0; i < properNouns.size(); i++ ) {
			if ( !data.containsKey(properNouns.get(i)) ) {
				data.put(properNouns.get(i), 1);
			} else {
				data.put(properNouns.get(i), data.get(properNouns.get(i)) + 1);
			}
		}
		
		for ( int i = 0; i < stopList.size(); i++ ) {
			data.remove(stopList.get(i));
		}
		
		properNouns.clear();
		
		for ( String key : data.keySet() ) {
			if ( data.get(key) > CUT_OFF ) {
				properNouns.add(key);
			}
		}
		
		String characters = "Frequently Occurring Characters (and other proper nouns)";
		
		for ( int i = 0; i <=properNouns.size()-1; i++ ) {
			characters = characters + "\n" + properNouns.get(i);
		}
		return characters + "\n";
	}
	
	
	/**
	 * This method turns an ArrayList into a String
	 * @return the ten items contained in the ArrayList
	 */
	private String listTen() {
		String list = "";
		for ( int i = 1; i <=topTen.size(); i++ ) {
			list = list + "\n" + i + ": " + 
					topTen.get(i).getItem();
		}
		return list + "\n";
	}
	
	/**
	 * This method turns an ArrayList into a String
	 * @return the ten items contained in the Arraylist and the frequency they occurred in the book
	 */
	private String listTenWithFrequency() {
		String list = "";
		for ( int i = 1; i <=topTen.size(); i++ ) {
			list = list + "\n" + i + ": \"" + 
					topTen.get(i).getItem() + "\" occurred " + topTen.get(i).getOccurrences() + " times.";
		}
		return list + "\n";
	}
	
	/**
	 * This method counts the length of each String in an ArrayList of Strings
	 * @param toBeCounted a HashMap mapping a String (the key) to the number of times it occurred (the value)
	 */
	private void countLengths( ArrayList<String> toBeCounted ) {
		data.clear();
		for ( int i = 0; i < toBeCounted.size(); i++ ) {
			data.put(toBeCounted.get(i), toBeCounted.get(i).length());
		}
	}
	
	/**
	 * This method removes the stop list words from an ArrayList of words
	 */
	private void takeOutStopList() {
		countOccurrences(words);
		for ( int i = 0; i < stopList.size(); i++ ) {
			data.remove(stopList.get(i));
			
		}
	}
	
	/**
	 * This method takes an ArrayList of Strings and turns it into a HashMap with the value being the 
	 * count of how many times the String occurs.
	 * @param toBeCounted a HashMap of Strings and the number of times they occurred in the ArrayList
	 */
	private void countOccurrences( ArrayList<String> toBeCounted ) {
		data.clear();
		for ( int i = 0; i < toBeCounted.size(); i++ ) {
			toBeCounted.set(i, toBeCounted.get(i).toLowerCase());
			if ( !data.containsKey(toBeCounted.get(i)) ) {
				data.put(toBeCounted.get(i), 1);
			} else {
				data.put(toBeCounted.get(i), data.get(toBeCounted.get(i)) + 1);
			}
		}
	}
	
	/**
	 * This method iterates through the data HashMap 10 times (or whatever value TOP holds), 
	 * passing it to the findLargest method  removing the returned largest key before each new iteration. 
	 * 
	 * Two separate methods are used because HashMaps don't listen to remove() while they are in a loop.
	 * 
	 * As findLargest() is run it populates the topTen HashMap, which is cleared for that use at the 
	 * beginning of this method.
	 * 
	 * Note: if there is a tie for the last place the first element in the HashMap to hold to value will be 
	 * given the place.
	 */
	private void getTopTen() {		
		topTen.clear();
		
		for (int i = 1; i <= TOP; i++) {
			data.remove(findLargest(i));
		}
	}
	
	/**
	 * This is a selection sort to find the largest. It is called by getTopTen 10 times. It populates 
	 * the topTen HashMap with one key per call.
	 * 
	 * It updates the topTen list with its selection from each iteration.
	 * @param i an integer representing place value passed by the for loop in getTopTen
	 * @return the key selected after comparing all values
	 */
	private String findLargest(int i) {
		int largest = 0;
		String item = "";
		for ( String key : data.keySet() ) {
			if ( largest < data.get(key) ) {
				largest = data.get(key);
				item = key;
			}
			Winner winner = new Winner(largest, item);
			topTen.put(i, winner);
		}
		return item;
	}
	
	/**
	 * This method iterates through the data HashMap 10 times (or whatever value TOP holds), 
	 * passing it to the findSmallest method removing the returned smallest key before each new iteration. 
	 * 
	 * Two separate methods are used because HashMaps don't listen to remove() while they are in a loop.
	 * 
	 * As findSmallest() is run it populates the topTen HashMap, which is cleared for that use at the 
	 * beginning of this method.
	 * 
	 *The Winner class holds a String item and the number of occurrences
	 * 
	 * Note: if there is a tie for the last place the first element in the HashMap to hold to value will be 
	 * given the place.
	 */
	private void getBottomTen() {		
		topTen.clear();
		
		for (int i = 1; i <= TOP; i++) {
			data.remove(findSmallest(i));
		}
	}
	
	/**
	 * This is a selection sort to find the smallest value. It is called by getTopTen 10 times. 
	 * It populates the topTen HashMap with one key per call.
	 * 
	 * It updates the topTen list with its selection from each iteration.
	 * 
	 * The Winner class holds a String item and the number of occurrences
	 * 
	 * @param i an integer representing place value passed by the for loop in getTopTen
	 * @return the key selected after comparing all values
	 */
	private String findSmallest(int i) {
		int smallest = 100000;
		String item = "";
		for ( String key : data.keySet() ) {
			if ( smallest > data.get(key) ) {
				smallest = data.get(key);
				item = key;
			}
			Winner winner = new Winner(smallest, item);
			topTen.put(i, winner);
		}
		return item;
	}
	
	

}
