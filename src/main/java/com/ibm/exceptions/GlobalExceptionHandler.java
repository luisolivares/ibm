package com.ibm.exceptions;

import com.ibm.domain.person.model.response.ResponseApiDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApiDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        ResponseApiDTO responseApiDTO = new ResponseApiDTO(null , apiError);
        return new ResponseEntity<>(responseApiDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ResponseApiDTO> handleConstraintViolation(final ConstraintViolationException ex) {
        log.info(ex.getClass().getName());
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        ResponseApiDTO responseApiDTO = new ResponseApiDTO(null , apiError);
        return new ResponseEntity<>(responseApiDTO, new HttpHeaders(), apiError.status());
    }

    // 404
    @ExceptionHandler({NoHandlerFoundException.class})
    protected ResponseEntity<ResponseApiDTO> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
        log.info(ex.getClass().getName());
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), Arrays.asList(error));
        ResponseApiDTO responseApiDTO = new ResponseApiDTO(null , apiError);
        return new ResponseEntity<>(responseApiDTO, new HttpHeaders(), apiError.status());
    }

    // 405
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<ResponseApiDTO> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex) {
        log.info(ex.getClass().getName());
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" Method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), Arrays.asList(builder.toString()));
        ResponseApiDTO responseApiDTO = new ResponseApiDTO(null , apiError);
        return new ResponseEntity<>(responseApiDTO, new HttpHeaders(), apiError.status());
    }

    // 415
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<ResponseApiDTO> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex) {
        log.info(ex.getClass().getName());
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" Media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
        final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), Arrays.asList(builder.substring(0, builder.length() - 2)));
        ResponseApiDTO responseApiDTO = new ResponseApiDTO(null , apiError);
        return new ResponseEntity<>(responseApiDTO, new HttpHeaders(), apiError.status());
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseApiDTO> handleAll(final Exception ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), Arrays.asList("error occurred"));
        ResponseApiDTO responseApiDTO = new ResponseApiDTO(null , apiError);
        return new ResponseEntity<>(responseApiDTO, new HttpHeaders(), apiError.status());
    }
}