package org.astrobrains.urlshortener.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Rest api's exception handler
    /*
    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(BusinessException ex, HttpServletRequest request) {
        var errorCode = ex.getErrorCodes();
        var response = ExceptionResponse.builder()
                .success(Boolean.FALSE)
                .status(errorCode.getStatus().value())
                .description(ex.getMessage())
                .businessErrorCode(errorCode.getCode())
                .businessErrorDescription(errorCode.getDescription())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .build();
        log.error("BusinessException: {}", ex.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
    */

    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.error("BusinessException at {}: {}", request.getRequestURI(), ex.getMessage()); // log full details

        ModelAndView mav = new ModelAndView("error/custom-error");
        mav.addObject("title", "Oops! Something went wrong");
        mav.addObject("message", ex.getMessage()); // controlled message
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        ModelAndView mav = new ModelAndView("error/custom-error");
        mav.addObject("title", "Unexpected Error");
        mav.addObject("message", "We’re sorry, something went wrong. Please try again later.");
        return mav;
    }
}
