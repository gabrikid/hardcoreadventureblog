package pt.sardoalware.gabrikid.hardcoreadventureblog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public  class HABExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HABException.class)
    protected ResponseEntity<String> handleHABException(HABException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}