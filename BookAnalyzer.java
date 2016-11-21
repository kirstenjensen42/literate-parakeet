import java.util.ArrayList;
import java.util.HashMap;

/**
 * The BookAnalyzer class is the boss. It tells everyone else to do all the work. It does a little 
 * work itself too.
 * @author kirstenjensen42
 *
 */
public class BookAnalyzer {
	
	private Book book;
	private Parser parser;
	
	private ArrayList<String> letters;
	private ArrayList<String> words;
	private ArrayList<String> quotes;
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	private HashMap<Integer, Winner> topTen = new HashMap<Integer, Winner>();

	
	public BookAnalyzer( Book book ) {
		this.book = book;
		parser = new Parser( book );
		
		letters = parser.getLetters();
		words = parser.getWords();
		quotes = parser.getQuotes();
		
	}
	
	public String topTenWords() {
		countOccurrences(words);
		getTopTen();
		String topWords = "Place, Word, Number of Occurences";
		
		for ( int i = 1; i <=topTen.size(); i++ ) {
			topWords = topWords + "\n" + i + ": " + 
					topTen.get(i).getItem() + ", " + topTen.get(i).getOccurrences();
		}
		return topWords + "\n";
	}
	
	public String topTenLetters() {
		countOccurrences(letters);
		getTopTen();
		String topLetters = "Place, Letter, Number of Occurences";
		
		for ( int i = 1; i <=topTen.size(); i++ ) {
			topLetters = topLetters + "\n" + i + ": " + 
					topTen.get(i).getItem() + ", " + topTen.get(i).getOccurrences();
		}
		return topLetters + "\n";
	}
	
	public void countLetters() {
		data.clear();
		
		for ( int i = 0; i < letters.size(); i++ ) {
			letters.set(i, letters.get(i).toLowerCase());
			if ( !data.containsKey(letters.get(i)) ) {
				data.put(letters.get(i), 1);
			} else {
				data.put(letters.get(i), data.get(letters.get(i)) + 1);
			}
		}
	}
	
	public void countOccurrences( ArrayList<String> toBeCounted ) {
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
	
	public void getTopTen() {		
		topTen.clear();
		
		for (int i = 1; i <= 10; i++) {
			data.remove(findLargest(i));
		}
	}
	
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
	
	
	
	
	
	
	
	
	

}
