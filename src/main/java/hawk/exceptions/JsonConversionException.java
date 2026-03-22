package hawk.exceptions;

public class JsonConversionException extends RuntimeException {

    public JsonConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}