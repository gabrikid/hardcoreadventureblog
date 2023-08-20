package pt.sardoalware.gabrikid.hardcoreadventureblog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.ErrorDto;

@ControllerAdvice
public  class HABExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HABException.class)
    protected ResponseEntity<ErrorDto> handleHABException(HABException e) {
        return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
    }

}