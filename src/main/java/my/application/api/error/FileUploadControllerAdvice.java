package my.application.api.error;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FileUploadControllerAdvice {

    @ExceptionHandler(NoSuchParameterException.class)
    public ResponseEntity<String> illegalArgument(NoSuchParameterException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
