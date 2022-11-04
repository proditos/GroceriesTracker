package exception;

/**
 * @author Vladislav Konovalov
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
