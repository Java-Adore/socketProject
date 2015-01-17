import java.util.Scanner;

public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Scanner scanner = new Scanner(System.in);

	private String message;

	private boolean exit;

	public CustomException(String message, boolean exit) {
		super();
		this.message = message;
		this.exit = exit;
	}

	public void displayException() {
		System.out.println(String.format("\n %s \n", message));
		if (exit) {
			System.out.println("Press any key to shutdown");
			scanner.next();
			System.exit(0);
		}

	}

	public CustomException() {
		super();
	}

}
