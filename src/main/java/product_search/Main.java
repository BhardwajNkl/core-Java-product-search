/**
 * 
 */
package product_search;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import custom_exceptions.InvalidGenderSelection;
import custom_exceptions.InvalidOutputPreference;
import custom_exceptions.InvalidSizeSelection;

/**
 * @author nikhilbhardwaj01
 *
 */
public class Main {
	public static void main(String args[]) {
		String response = "y"; // to know whether the user wants to enter other car details.
		Scanner sc = new Scanner(System.in); // Scanner object for reading inputs
		System.out.println();
		System.out.println("\t\t\t======================= Product Search Program =======================");
		System.out.println();

		do {
			start();
			System.out.print("\t\t\t Do you want to search for another T Shirt(Y/N)? ");
			response = sc.next();
		} while (response.equalsIgnoreCase("y"));
		System.out.println();
		System.out.println();
		System.out.println("\t\t\t================================ Thank You ================================");
		System.out.println();

		sc.close();
	}

	static void start() {
		UserInput userInputObject = getInput();// getting an input instance
		String csvDirectoryPath = getCsvDirectory();// getting the directory path where the CSV files have been placed.
		SearchProcessor searchProcessor = new SearchProcessor(userInputObject, csvDirectoryPath);// Getting a search
																									// processor
																									// instance for the
																									// provided input
																									// instance and the
																									// CSV files
																									// directory
		searchProcessor.process();// This method of the search processor is responsible for creating threads that
									// will read the CSV files assigned to them.
		List<TShirt> resultList = searchProcessor.getResult();// this method of the search processor returns the list of
																// TShirts that matched the user's input.
		List<TShirt> sorted = searchProcessor.sortByPreference(resultList);// This method of search processor sorts the
																			// result T shirt list based on price or
																			// rating or both.
		displayOutput(sorted);// this method displays the final sorted result to the users.
		searchProcessor.refreshSearchProcessor();
	}

	/*
	 * getInput(): THIS METHOD GETS THE INPUT FROM USER.
	 * 
	 */
	static UserInput getInput() {
		Scanner sc = new Scanner(System.in);
		String color = "";
		String size = "";
		String gender = "";
		String outputPreference = "";
		System.out.println("\t\t\t\t\t\t||Enter Product Detail|| ");
		System.out.println();
		System.out.print("\t\t\t\t Color>> ");
		color = sc.nextLine();

		boolean validSizeSelection = false;// This variable has been used for exception handling purpose. As long as it
											// is false, we prompt the user to enter valid input for 'size'.
		while (!validSizeSelection) {

			System.out.print("\t\t\t\t Size(XXS/XS/S/M/L/XL/XXL/XXXL)>> ");
			size = sc.nextLine();
			size = size.toUpperCase();
			try {
				for (SizeType st : SizeType.values()) {
					if (st.name().equals(size)) {
						validSizeSelection = true;
						break;
					}
				}

				if (!validSizeSelection) {
					throw new InvalidSizeSelection();// custom exception
				}

			} catch (InvalidSizeSelection iss) {
				iss.getExceptionMessage();
			}
		}

		boolean validGenderSelection = false;// This variable has been used for exception handling purpose. As long as
												// it is false, we prompt the user to enter allowed input for 'gender'.
		while (!validGenderSelection) {

			System.out.print("\t\t\t\t Gender(F/M)>> ");// F for 'female' and M for 'male'. TShirts That have unisex as
														// gender recommendation will be considered for both male and
														// female.
			gender = sc.nextLine();
			gender = gender.toUpperCase();
			try {
				for (GenderType gt : GenderType.values()) {
					if (gt.name().equals(gender)) {
						validGenderSelection = true;
						break;
					}
				}

				if (!validGenderSelection) {
					throw new InvalidGenderSelection();// custom exception
				}

			} catch (InvalidGenderSelection igs) {
				igs.getExceptionMessage();
			}
		}

		boolean validOutputPreferenceSelection = false; // This variable has been used for exception handling purpose.
														// As long as it is false, we prompt the user to enter allowed
														// input for 'Output Preference'.

		while (!validOutputPreferenceSelection) {

			System.out.print("\t\t\t\t Output Preference(P/R/PR/RP)>> ");// P for 'sort based on price'. R for 'sort
																			// based on Rating'. PR for 'Sort based on
																			// Price and in case of tie, sort based on
																			// rating'. RP for 'sort based on rating and
																			// in case of tie, sort based on price'.
																			// NOTE: price based sorting is increasing
																			// while rating based sorting is decreasing.
			outputPreference = sc.nextLine();
			outputPreference = outputPreference.toUpperCase();
			try {
				for (OutputPreferenceType opt : OutputPreferenceType.values()) {
					if (opt.name().equals(outputPreference)) {
						validOutputPreferenceSelection = true;
						break;
					}
				}

				if (!validOutputPreferenceSelection) {
					throw new InvalidOutputPreference(); // custom exception
				}

			} catch (InvalidOutputPreference iop) {
				iop.getExceptionMessage();
			}
		}

		UserInput userInputObject = new UserInput(color, SizeType.valueOf(size), GenderType.valueOf(gender),
				OutputPreferenceType.valueOf(outputPreference));// creating the input instance
		// sc.close();
		return userInputObject;
	}

	/*
	 * getCSVDirectory(): This method returns the absolute path of the folder that
	 * contains CSV files. DESCRIPTION: 1. BY DEFAULT THE CSV FILES ARE PRESENT IN
	 * THE PROJECTS ROOT DIRECTORY. SO, IF THE USER IS RUNNING THE JAR FROM PROJECTS
	 * ROOT FOLDER, THERE WILL BE NO PROBLEM. 2. IF THE USER IS RUNNING THE JAR FROM
	 * THE TARGET FOLDER(GENERATED BY MAVEN), THEN WE NEED TO GO ONE STEP BACK IN
	 * DIRECTORY STRUCTURE TO GET FILE PATH. 3. THE ABOVE TWO CASES HAVE BEEN
	 * CONSIDERED AS DEFAULT USE CASE. IN CASE, A USER WANTS TO DOWNLOAD THE JAR
	 * FILE ONLY, THEN HE/SHE NEEDS TO ENSURE THAT THE FOLDER OF CSV FILES IS NAMED
	 * AS 'CSV Files' AND THIS FOLDER SHOULD BE PRESENT IN THE SAME DIRECTORY FROM
	 * WHERE THE USER IS RUNNING THE PROGRAM.
	 */
	static String getCsvDirectory() {
		String currentPath = "";
		try {
			currentPath = new java.io.File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (currentPath.substring(currentPath.length() - 6, currentPath.length()).equalsIgnoreCase("target")) {
			currentPath = currentPath.substring(0, currentPath.length() - 6);
		}

		String csvDirectoryPath = currentPath + "\\CSV Files";
		return csvDirectoryPath;
	}

	/*
	 * displayOutput(): Method to display the list of t shirts that matched the
	 * search.
	 */
	static void displayOutput(List<TShirt> list) {

		System.out.println();
		System.out.println("\t\t\t\t ############### " + list.size() + " Matching Products Found ################");
		System.out.println();
		int productNumber = 1;
		for (TShirt t : list) {
			System.out.println("\t\t\t\t\t Result " + productNumber + ":");
			System.out.println();
			productNumber++;
			System.out.println("\t\t\t\t\t\t ID: " + t.getShirtId());
			System.out.println("\t\t\t\t\t\t Name: " + t.getShirtName());
			System.out.println("\t\t\t\t\t\t Size: " + t.getShirtSize());
			System.out.println("\t\t\t\t\t\t Color: " + t.getShirtColor());
			System.out.println("\t\t\t\t\t\t Gender Recommendation: " + t.getShirtGender());
			System.out.println("\t\t\t\t\t\t Price: " + t.getShirtPrice());
			System.out.println("\t\t\t\t\t\t Rating: " + t.getShirtRating());
			System.out.println("\t\t\t\t\t\t Availability: " + t.getShirtAvailability());
			System.out.println();
		}
		// if no matching t shirt found then show appropriate message
		if (list.size() == 0) {
			System.out.println("\t\t\t\t\t\t     No Record Found ");
			System.out.println();
		}
		System.out.println();
		System.out.println("\t\t\t\t ############################################################");
		System.out.println();
	}
}