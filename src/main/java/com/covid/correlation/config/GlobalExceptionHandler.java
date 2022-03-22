package com.covid.correlation.config;

import com.covid.correlation.exception.AbstractException;
import com.covid.correlation.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AbstractException.class})
    @ResponseBody
    protected ResponseEntity<String> handleAbstractException(AbstractException ex,
                                                                             WebRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<String>(ex.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServerException.class})
    @ResponseBody
    protected ResponseEntity<String> handleServerException(AbstractException ex,
                                                                             WebRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<String>(ex.toString(), HttpStatus.FORBIDDEN);
    }


}
