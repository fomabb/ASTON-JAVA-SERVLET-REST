package org.iase24.nikolay.kirilyuk.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.iase24.nikolay.kirilyuk.model.out.ErrorRestOut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final List<Class<? extends Exception>> EXCEPTION_STATUS_400 = List.of(
            MethodArgumentNotValidException.class,
            IOException.class
    );

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Exception handler catch exception : {}", ex.getMessage());
        return handleExceptionInternal(
                ex,
                createErrorRestOut(ex),
                new HttpHeaders(),
                defineStatus(ex),
                request
        );
    }

    private HttpStatusCode defineStatus(Exception exception) {
        if (EXCEPTION_STATUS_400.contains(exception.getClass())) {
            return HttpStatusCode.valueOf(400);
        }
        return HttpStatusCode.valueOf(500);
    }

    private ErrorRestOut createErrorRestOut(Exception exception) {
        return ErrorRestOut.builder()
                .timestamp(ZonedDateTime.now().toString())
                .message(exception.getMessage())

                .build();
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCourseNotFoundException(CourseNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
