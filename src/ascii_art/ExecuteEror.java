package ascii_art;

public class ExecuteEror extends Exception {

    /**
     * Constructor for the ExecuteEror class which is a custom exception.
     * This constructor takes a message as a parameter to describe the error.
     *
     * @param message The error message that will be passed to the Exception class.
     */
    public ExecuteEror(String message) {
        super(message);
    }
}
