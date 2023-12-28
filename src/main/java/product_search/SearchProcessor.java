/**
 * 
 */
package product_search;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nikhilbhardwaj01
 *
 */
public class SearchProcessor {
	private UserInput userInput;
	private String csvDirectoryPath;

	SearchProcessor(UserInput userInput, String csvDirectoryPath) {
		this.userInput = userInput;
		this.csvDirectoryPath = csvDirectoryPath;
	}

	/*
	 * process(): THIS METHOD CREATES AND STARTS THREADS THAT WILL READ FILES IN THE
	 * SPECIFIED DIRECTORY. DESCRIPTION: IT CREATES AS MANY READER-THREADS AS MANY
	 * THE FILES ARE THERE. IT ASSIGNS ONE FILE TO EVERY THREAD.
	 */
	public void process() {
		File dir = new File(this.csvDirectoryPath);
		File fileList[] = dir.listFiles();
		CsvReaderThread.setFileList(fileList);
		CsvReaderThread.setUserInput(this.userInput);
		Thread threads[] = new Thread[fileList.length];
		for (int i = 0; i < threads.length; i++) {
			CsvReaderThread crt = new CsvReaderThread();
			threads[i] = new Thread(crt);
			threads[i].setName("" + i);// this is for identification purpose. Naming is done in this way so that the
										// thread can be mapped to a file.[thread with name 'i' gets the file at index
										// 'i' in the file array]
		}
		for (Thread t : threads) {
			t.start();
		}
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * getResult(): The reader threads write the matching TShirt objects in a list.
	 * This list is accessed by the search processor through 'getResultSet' method
	 * of CsvReaderThread class. and then this list is returned.
	 */
	public List<TShirt> getResult() {
		return CsvReaderThread.getResultSet();
	}

	/*
	 * sortByPreference(): sorts the list of t shirt based on the provided output
	 * preference. SORTING IS BASED ON BETTER FIRST. 1. P: sorting based on price. T
	 * shirts with lower price appears first. 2. R: sorting based on rating. T
	 * shirts with higher rating appear first. 3. PR: sort based on price and in
	 * case of tie, use rating as sort parameter 4. RP: sort based on rating and in
	 * case of tie, use price as sort parameter
	 */
	public List<TShirt> sortByPreference(List<TShirt> list) {
		List<TShirt> sorted;
		switch (userInput.getOutputPreference()) {
		case P: {
			Comparator<TShirt> byPrice = Comparator.comparing(TShirt::getShirtPrice);
			sorted = list.stream().sorted(byPrice).collect(Collectors.toList());
			return sorted;
		}
		case R: {
			Comparator<TShirt> byRating = Comparator.comparing(TShirt::getShirtRating);
			sorted = list.stream().sorted(byRating.reversed()).collect(Collectors.toList());
			return sorted;
		}
		case PR: {
			Comparator<TShirt> byPrice = Comparator.comparing(TShirt::getShirtPrice);
			Comparator<TShirt> byRating = Comparator.comparing(TShirt::getShirtRating);
			Comparator<TShirt> byPriceAndRating = byPrice.thenComparing(byRating.reversed());
			sorted = list.stream().sorted(byPriceAndRating).collect(Collectors.toList());
			return sorted;
		}
		case RP: {
			Comparator<TShirt> byPrice = Comparator.comparing(TShirt::getShirtPrice);
			Comparator<TShirt> byRating = Comparator.comparing(TShirt::getShirtRating);
			Comparator<TShirt> byRatingAndPrice = byRating.reversed().thenComparing(byPrice);
			sorted = list.stream().sorted(byRatingAndPrice).collect(Collectors.toList());
			return sorted;
		}
		default: {
			return list;
		}
		}
	}

	public void refreshSearchProcessor() {
		CsvReaderThread.clearCsvReaderData();
		this.userInput = null;
		this.csvDirectoryPath = null;
	}
}
