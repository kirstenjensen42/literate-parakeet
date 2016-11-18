import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This is the FileReader class used to read in the data file that is used by the rest of the program.
 * @author kirstenjensen42
 *
 */
public class FileReader {
	
	private String fileName;
	private ArrayList<String> lines;
	private Scanner scan;
	
	/**
	 * The constructor for FileReader. Pass it the name of the file.
	 * @param File the file to be read in.
	 */
	public FileReader(String file) {
		
		fileName = file;
		lines = new ArrayList<String>();
		scan = null;
		
		readFile();
		
	}
	
	/**
	 * This method will read in the entire contents of the file and store it line by line in an ArrayList.
	 */
	public void readFile() {

		try {
			
			File inputFile = new File( fileName );
			
			scan = new Scanner( inputFile );
			
			while ( scan.hasNextLine() ) {
				String line = scan.nextLine();
				lines.add(line);
			}
			
		} catch ( FileNotFoundException fnfe ){
			
			System.out.println("File not found.");
			
		} finally {
			if ( scan != null ) {
				scan.close();
			}
		}
		
		
	}



	/**
	 * Getter method for the ArrayList of Strings created from the file.
	 * @return the lines array list
	 */
	public ArrayList<String> getLines() {
		return lines;
	}

}