package org.astrobrains.urlshortener.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final BusinessErrorCodes errorCodes;

    public BusinessException(BusinessErrorCodes errorCodes) {
        super(errorCodes.getDescription());
        this.errorCodes = errorCodes;
    }

    public BusinessException(BusinessErrorCodes errorCodes, String message) {
        super(message);
        this.errorCodes = errorCodes;
    }
}
