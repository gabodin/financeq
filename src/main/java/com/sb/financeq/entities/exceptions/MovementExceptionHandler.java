package com.sb.financeq.entities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MovementExceptionHandler {

    @ExceptionHandler
    public String handleException(UserNotAuthorizedException exception, Model model) {
        MovementErrorResponse movementErrorResponse = new MovementErrorResponse();

        movementErrorResponse.setMessage(exception.getMessage());
        movementErrorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        movementErrorResponse.setTimestamp(System.currentTimeMillis());

        model.addAttribute("errorResponse", movementErrorResponse);
        model.addAttribute("errorType", "Access Denied");

        return "errors/errors-page";
    }

    @ExceptionHandler
    public String handleException(MovementNotFoundException exception, Model model) {
        MovementErrorResponse movementErrorResponse = new MovementErrorResponse();

        movementErrorResponse.setMessage(exception.getMessage());
        movementErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        movementErrorResponse.setTimestamp(System.currentTimeMillis());

        model.addAttribute("errorResponse", movementErrorResponse);
        model.addAttribute("errorType", "Movement not found");

        return "errors/errors-page";
    }
}
