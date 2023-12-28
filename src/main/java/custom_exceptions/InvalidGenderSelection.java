/**
 * 
 */
package custom_exceptions;

/**
 * @author nikhilbhardwaj01
 *
 */
public class InvalidGenderSelection extends Exception {
	public InvalidGenderSelection() {
		super();
	}

	FunctionalExceptionInterface fei = () -> System.out.println("\t\t\t\t Wrong selection! Please enter again.");

	public void getExceptionMessage() {
		fei.displayMessage();
	}
}
