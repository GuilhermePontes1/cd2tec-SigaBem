package guilherme.com.cd2tecsigabem.controller.exceptions;

import guilherme.com.cd2tecsigabem.service.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundHandler(ResourceNotFoundException e, HttpServletRequest req) {
        StandardError error = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setError("Resource not found");
        error.setMessage(e.getMessage());
        error.setStatus(status.value());
        error.setPath(req.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

}
