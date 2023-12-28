package custom_exceptions;

public class InvalidOutputPreference extends Exception {
	public InvalidOutputPreference() {
		super();
	}

	FunctionalExceptionInterface fei = () -> System.out.println("\t\t\t\t Wrong selection! Please enter again.");

	public void getExceptionMessage() {
		fei.displayMessage();
	}
}
