/**
 * 
 */
package product_search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

/**
 * @author nikhilbhardwaj01
 *
 */
public class CsvReaderThread implements Runnable {
	private static UserInput userInput;// the user input instance provided by the search processor
	private static List<TShirt> resultSet = Collections.synchronizedList(new ArrayList<>());// a list to store the t
																							// shirt objects that match
																							// the search parameters.
	private static File fileList[];// list of files that need to be read. This list is provided by the search
									// processor.

	public void run() {
		String name = Thread.currentThread().getName();
		int index = Integer.parseInt(name);
		File targetFile = fileList[index];
		this.readFile(targetFile);
	}

	private void readFile(File f) {
		FileReader fr = null;
		try {
			fr = new FileReader(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		CSVReader cr = new CSVReaderBuilder(fr).withCSVParser(parser).withSkipLines(1).build();
		String record[];
		try {
			while ((record = cr.readNext()) != null) {
				if (record.length != 8) {
					// this record may not be entered correctly. so we skip this record.
					continue;
				}
				String id = record[0];
				String name = record[1];
				String color = record[2];
				String gender = record[3];
				String size = record[4];
				String price = record[5];
				float priceNumeric = Float.parseFloat(price);
				String rating = record[6];
				float ratingNumeric = Float.parseFloat(rating);
				String availability = record[7];
				if ((color.equalsIgnoreCase(userInput.getColor()) && size.equalsIgnoreCase(userInput.getSize().name()))
						&& (gender.equalsIgnoreCase(userInput.getGender().name()) || gender.equalsIgnoreCase("U"))) {
					TShirt tShirt = new TShirt(id, name, color, gender, size, priceNumeric, ratingNumeric,
							availability);
					this.addTShirtToResultSet(tShirt);
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

	}

	/*
	 * addTShirtToResultSet(): When a reader thread finds a matching TShirt record,
	 * It writes the matching TShirt object in the result set.
	 */
	synchronized private void addTShirtToResultSet(TShirt tShirt) {
		resultSet.add(tShirt);
	}

	/*
	 * setUserInput(): Using this method, the search processor sets the input
	 * instance for this class.
	 */
	public static void setUserInput(UserInput userInputObject) {
		userInput = userInputObject;
	}

	/*
	 * setFileList(): Using this method, the search processor sets the file list
	 * that needs to be scanned.
	 */
	public static void setFileList(File files[]) {
		fileList = files;
	}

	/*
	 * getResultSet(): Using this method, the search processor gets the list of t
	 * shirts from this class.
	 */
	public static List<TShirt> getResultSet() {
		return resultSet;
	}

	public static void clearCsvReaderData() {
		userInput = null;
		fileList = null;
		resultSet.clear();
	}
}
