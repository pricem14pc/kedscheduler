package uk.gov.ons.rrm.kedsched.dao.exception;

/**
 * A generic exception that gets thrown when there is an error in the DAO layer.
 * @author thomas3
 *
 */
public final class DAOException extends Exception {

    /** The serial version Id. **/
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs the exception with the underlying cause.
     * @param t
     *          The underlying cause.
     */
    public DAOException(final Throwable t) {
        super(t);
    }
    
    /**
     * Constructs the exception with a message and the underlying cause.
     * @param message
     *          The message.
     * @param t
     *          The underlying cause.
     */
    public DAOException(final String message, final Throwable t) {
        super(message, t);
    }

}
