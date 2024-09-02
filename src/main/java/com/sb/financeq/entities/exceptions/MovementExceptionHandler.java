package com.sb.financeq.entities.exceptions;

import com.sb.financeq.entities.User;
import com.sb.financeq.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MovementExceptionHandler {

    private final UserService userService;

    public MovementExceptionHandler(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler
    public String handleException(UserNotAuthorizedException exception, Model model) {
        User user = userService.getCurrentUser();
        MovementErrorResponse movementErrorResponse = new MovementErrorResponse();

        movementErrorResponse.setMessage(exception.getMessage());
        movementErrorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        movementErrorResponse.setTimestamp(System.currentTimeMillis());

        model.addAttribute("errorResponse", movementErrorResponse);
        model.addAttribute("errorType", "Access Denied");
        model.addAttribute("user", user);

        return "errors/errors-page";
    }

    @ExceptionHandler
    public String handleException(MovementNotFoundException exception, Model model) {
        User user = userService.getCurrentUser();
        MovementErrorResponse movementErrorResponse = new MovementErrorResponse();

        movementErrorResponse.setMessage(exception.getMessage());
        movementErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        movementErrorResponse.setTimestamp(System.currentTimeMillis());

        model.addAttribute("errorResponse", movementErrorResponse);
        model.addAttribute("errorType", "Movement not found");
        model.addAttribute("user", user);

        return "errors/errors-page";
    }
}
