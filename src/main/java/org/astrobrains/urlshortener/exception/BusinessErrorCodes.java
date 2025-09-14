package org.astrobrains.urlshortener.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCodes {
    // generic: 0 - 99
    // --- 0xx Generic ---
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No Code"),
    UNKNOWN_ERROR(1, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error"),
    VALIDATION_ERROR(2, HttpStatus.BAD_REQUEST, "Validation failed"),
    NOT_FOUND(3, HttpStatus.NOT_FOUND, "Not found"),
    INTERNAL_SERVER_ERROR(4, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),

    // url related: 100 - 199
    URL_NOT_FOUND(100, HttpStatus.NOT_FOUND, "URL not found"),
    URL_ALREADY_EXISTS(101, HttpStatus.CONFLICT, "URL already exists"),
    URL_EXPIRED(102, HttpStatus.GONE, "URL expired"),
    URL_INVALID(103, HttpStatus.BAD_REQUEST, "URL invalid"),
    URL_PRIVATE(104, HttpStatus.FORBIDDEN, "URL private"),
    URL_PUBLIC(105, HttpStatus.OK, "URL public"),
    URL_SHORT_KEY_INVALID(106, HttpStatus.BAD_REQUEST, "URL short key invalid"),
    URL_SHORT_KEY_LENGTH_INVALID(107, HttpStatus.BAD_REQUEST, "URL short key length invalid"),
    URL_SHORT_KEY_LENGTH_TOO_SHORT(108, HttpStatus.BAD_REQUEST, "URL short key length too short"),
    URL_SHORT_KEY_LENGTH_TOO_LONG(109, HttpStatus.BAD_REQUEST, "URL short key length too long"),
    URL_SHORT_KEY_ALREADY_EXISTS(110, HttpStatus.CONFLICT, "URL short key already exists"),
    URL_SHORT_KEY_NOT_FOUND(111, HttpStatus.NOT_FOUND, "URL short key not found"),
    URL_SHORT_KEY_TOO_LONG(112, HttpStatus.BAD_REQUEST, "URL short key too long"),
    URL_SHORT_KEY_TOO_SHORT(113, HttpStatus.BAD_REQUEST, "URL short key too short"),

    ;

    @Getter
    private final Integer code;

    @Getter
    private final HttpStatus status;

    @Getter
    private final String description;

    BusinessErrorCodes(Integer code, HttpStatus status, String description) {
        this.code = code;
        this.status = status;
        this.description = description;
    }
}