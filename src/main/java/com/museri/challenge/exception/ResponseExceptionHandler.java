package com.museri.challenge.exception;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestController
@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler (Exception.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    public final ExceptionResponse handleAll(Exception ex) {
        return buildExceptionResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler (BadRequestException.class)
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    public final ExceptionResponse handlerBadRequestException(BadRequestException ex) {
        return buildExceptionResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (UniqueEmailException.class)
    @ResponseStatus (HttpStatus.CONFLICT)
    public final ExceptionResponse handleUniqueEmailException(UniqueEmailException ex) {
        return buildExceptionResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler (EntityNotFoundException.class)
    @ResponseStatus (code = HttpStatus.NOT_FOUND)
    public final ExceptionResponse handleEntityNotFoundException(EntityNotFoundException exception) {
        return buildExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    @ResponseStatus (org.springframework.http.HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildExceptionResponse(ex, HttpStatus.BAD_REQUEST, "Validation errors: " + errors);
    }


    @ExceptionHandler (MalformedJwtException.class)
    @ResponseStatus (code = HttpStatus.BAD_REQUEST)
    public final ExceptionResponse handleEntityMalformedJwtException(MalformedJwtException exception) {
        return buildExceptionResponse(exception, HttpStatus.BAD_REQUEST, "Invalid Token");
    }


    private ExceptionResponse buildExceptionResponse(Exception ex, HttpStatus httpStatus) {
        log.debug("ERROR: " + ex.getClass().getName(), ex);
        log.error("ERROR: " + ex.getClass().getName() + ". Message: " + ex.getMessage());
        return new ExceptionResponse(ex.getMessage(), httpStatus);
    }

    private ExceptionResponse buildExceptionResponse(Exception ex, HttpStatus httpStatus, String message) {
        log.debug("ERROR: " + ex.getClass().getName(), ex);
        log.error("ERROR: " + ex.getClass().getName() + ". Message: " + ex.getMessage());
        return new ExceptionResponse(message, httpStatus);
    }
}
