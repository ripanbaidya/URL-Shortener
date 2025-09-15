package org.astrobrains.urlshortener.exception;

public class UserException extends BusinessException {

    public UserException(BusinessErrorCodes errorCodes) {
        super(errorCodes);
    }

    public UserException(BusinessErrorCodes errorCodes, String message) {
        super(errorCodes, message);
    }
}
