package com.strong.news.configuration;

import com.strong.news.exception.NullEntityReferenceException;
import com.strong.news.exception.UnacceptableParameterValueException;
import com.strong.news.model.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class StoreExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NullEntityReferenceException.class, UnacceptableParameterValueException.class})
    public ResponseEntity<ErrorResponse> handleNullEntityReferenceException(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler({NoSuchElementException.class, EntityNotFoundException.class, UsernameNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleNoSuchElementException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}
