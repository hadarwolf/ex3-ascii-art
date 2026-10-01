package ascii_art;

public class IncorrectFormat extends Exception {

    /**
     * Constructor for the IncorrectFormat class, which is a custom exception.
     * This constructor allows an error message to be passed when the exception is thrown.
     *
     * @param message The error message to be associated with this exception.
     */
    public IncorrectFormat(String message) {
        super(message);
    }
}
