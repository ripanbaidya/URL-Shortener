package org.astrobrains.urlshortener.exception;

public class ShortUrlException extends BusinessException {

    public ShortUrlException(BusinessErrorCodes errorCodes) {
        super(errorCodes);
    }

    public ShortUrlException(BusinessErrorCodes errorCodes, String message) {
        super(errorCodes, message);
    }
}
