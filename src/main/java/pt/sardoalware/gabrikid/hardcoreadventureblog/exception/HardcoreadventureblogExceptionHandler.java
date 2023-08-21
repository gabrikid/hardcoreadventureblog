package pt.sardoalware.gabrikid.hardcoreadventureblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pt.sardoalware.gabrikid.hardcoreadventureblog.dto.ErrorDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HardcoreadventureblogExceptionHandler {

    @ExceptionHandler(AuthorNotFoundException.class)
    protected ResponseEntity<ErrorDto> handleAuthorNotFoundException(AuthorNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected ResponseEntity<ErrorDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    protected ResponseEntity<ErrorDto> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // convert List<FieldError> to Map<String, List<String>>, like:
        // {"email": ["cannot be blank", "must be valid address"]}
        Map<String, List<String>> fieldErrorsMessages = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                FieldError::getField,
                                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(fieldErrorsMessages));
    }

}