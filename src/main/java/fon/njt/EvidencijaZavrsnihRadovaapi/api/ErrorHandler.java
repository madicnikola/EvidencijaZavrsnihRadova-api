package fon.njt.EvidencijaZavrsnihRadovaapi.api;

import fon.njt.EvidencijaZavrsnihRadovaapi.dto.MessageDto;
import fon.njt.EvidencijaZavrsnihRadovaapi.exceptions.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Value("${api_doc_url}")
    private String details;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApplicationError> handleNotFoundException(NotFoundException exception, WebRequest webRequest) {
        ApplicationError error = new ApplicationError();
        error.setCode(101);
        error.setMessage(exception.getMessage());
        error.setDetails(details);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ApplicationError> handleNotFoundException(UserAlreadyExists exception, WebRequest webRequest) {
        ApplicationError error = new ApplicationError();
        error.setCode(409);
        error.setMessage(exception.getMessage());
        error.setDetails(details);

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({HttpClientErrorException.Unauthorized.class, InvalidHeaders.class})
    public ResponseEntity<ApplicationError> handleUnauthorizedException(HttpClientErrorException.Unauthorized exception, WebRequest webRequest) {
        ApplicationError error = new ApplicationError();
        error.setCode(210);
        error.setMessage(exception.getMessage());
        error.setDetails(details);

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApplicationError> handleAccessDenied(AccessDeniedException exception, WebRequest webRequest) {
        ApplicationError error = new ApplicationError();
        error.setCode(403);
        error.setMessage(exception.getMessage());
        error.setDetails(details);

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotPresentException.class)
    public ResponseEntity<ApplicationError> handleNotPresentException(NotPresentException exception, WebRequest webRequest) {
        ApplicationError error = new ApplicationError();
        error.setCode(101);
        error.setMessage(exception.getMessage());
        error.setDetails(details);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ApplicationError> handleNotificationException(NotPresentException exception, WebRequest webRequest) {
        ApplicationError error = new ApplicationError();
        error.setCode(208);
        error.setMessage(exception.getMessage());
        error.setDetails(details);
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(error);
    }

    @ExceptionHandler(BadRequestBodyException.class)
    public ResponseEntity<ApplicationError> handleBadRequestBodyException(BadRequestBodyException exception, WebRequest webRequest) {
        ApplicationError error = new ApplicationError();
        error.setCode(400);
        error.setMessage(exception.getMessage());
        error.setDetails(details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<MessageDto> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageDto("File too large!"));
    }
}
