
/**
 * This is the Winner class. It is used by BookAnalyzer as the value of a HashMap. It holds data 
 * for a String and the number of occurrences it had.
 * @author kirstenjensen42
 *
 */
public class Winner {
	
	int occurrences;
	String item;
	
	public Winner(int occurrences, String item) {
		this.occurrences = occurrences;
		this.item = item;
		
		
	}

	
	/**
	 * The getter method for occurences
	 * @return the occurrences
	 */
	public int getOccurrences() {
		return occurrences;
	}

	/**
	 * The getter method for the String item
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

}
