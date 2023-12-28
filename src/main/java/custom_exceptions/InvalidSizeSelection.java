/**
 * 
 */
package custom_exceptions;

/**
 * @author nikhilbhardwaj01
 *
 */
public class InvalidSizeSelection extends Exception {

	public InvalidSizeSelection() {
		super();
	}

	FunctionalExceptionInterface fei = () -> System.out.println("\t\t\t\t Wrong selection! Please enter again.");

	public void getExceptionMessage() {
		fei.displayMessage();
	}

}
