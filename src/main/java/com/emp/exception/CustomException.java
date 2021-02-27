package com.emp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonParseException;

/**
 * This class overrides exceptions in JsonResponse with the values below. Used for ControllerAdvice
 * @author Manish.Madhusoodhan
 *
 */
@ControllerAdvice
public class CustomException {
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ExceptionMessage> EntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ExceptionMessage error = new ExceptionMessage("Not found", ex.getMessage());
        return new ResponseEntity<ExceptionMessage>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ExceptionMessage> ConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ExceptionMessage error = new ExceptionMessage("Try with another email id", ex.getMessage());
        return new ResponseEntity<ExceptionMessage>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(IllegalStateMachineException.class)
    public final ResponseEntity<ExceptionMessage> IllegalStateMachineException(IllegalStateMachineException ex, WebRequest request) {
        ExceptionMessage error = new ExceptionMessage("State Machine transition is invalid", ex.getMessage());
        return new ResponseEntity<ExceptionMessage>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionMessage> IllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ExceptionMessage error = new ExceptionMessage("Not a valid state machine", ex.getMessage());
        return new ResponseEntity<ExceptionMessage>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(JsonParseException.class)
    public final ResponseEntity<ExceptionMessage> JsonParserException(JsonParseException ex, WebRequest request) {
        ExceptionMessage error = new ExceptionMessage("Error parsing JSON", ex.getMessage());
        return new ResponseEntity<ExceptionMessage>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String,String> handleValidationFailure(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getObjectName() + fieldError.getField(),
                       fieldError.getDefaultMessage());
        }
        return errors;
    }
    
}
