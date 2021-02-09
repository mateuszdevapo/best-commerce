package pl.devapo.azer.bestcommerce.signup.validation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.devapo.azer.bestcommerce.signup.exception.UserAlreadyExistException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorResponse handleBindException(BindException ex) {
        List<ErrorResponse.ErrorDetails> errorDetails = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errorDetails.add(
                ErrorResponse.ErrorDetails.builder()
                        .fieldName(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build()
        ));

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.toString());
        response.setMessage("Validation failed for given data");
        response.setErrors(errorDetails);
        return response;
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorResponse handleUserAlreadyExistException(UserAlreadyExistException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.CONFLICT.toString());
        response.setMessage("Validation failed for given data");
        response.setErrors(List.of(
                new ErrorResponse.ErrorDetails("email", "User with given email already exist in database")));
        return response;
    }
}
