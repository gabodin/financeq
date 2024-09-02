package com.sb.financeq.controllers;

import com.sb.financeq.entities.Movement;
import com.sb.financeq.entities.MovementDTO;
import com.sb.financeq.entities.User;
import com.sb.financeq.entities.enums.Category;
import com.sb.financeq.entities.enums.Status;
import com.sb.financeq.entities.exceptions.UserNotAuthorizedException;
import com.sb.financeq.services.MovementService;
import com.sb.financeq.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/movements")
@Controller
public class MovementController {

    private final MovementService movementService;
    private final UserService userService;

    @Autowired
    public MovementController(MovementService movementService, UserService userService) {
        this.movementService = movementService;
        this.userService = userService;
    }

    @GetMapping("")
    public String listMovementsDto(Model model) {
        User user = userService.getCurrentUser();

        List<MovementDTO> movementsDto = movementService.getMovementsDtoByUser(user.getUserId());

        model.addAttribute("movements", movementsDto);
        model.addAttribute("user", user);

        return "movements/movements-list-dto";
    }

    @GetMapping("/new")
    public String newMovement(Model model) {
        User user = userService.getCurrentUser();
        Movement movement = new Movement();
        Category[] categories = Category.values();
        Status[] statuses = Status.values();

        model.addAttribute("movement", movement);
        model.addAttribute("categories", categories);
        model.addAttribute("statuses", statuses);
        model.addAttribute("user", user);


        return "movements/new-movement";
    }

    @PostMapping("/add")
    public String addMovement(@ModelAttribute Movement movement, Model model) {
        System.out.println(movement);
        User user = userService.getCurrentUser();

        movement.setUserId(user);
        movement.setCreatedAt(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));

        movementService.save(movement);

        return "redirect:/movements";
    }

    @GetMapping("/delete/{movementId}")
    public String deleteMovement(@PathVariable Integer movementId, Model model) {
        Movement movement = movementService.findById(movementId);

        User user = userService.getCurrentUser();

        if (!movement.getUserId().getUserId().equals(user.getUserId())) {
            throw new UserNotAuthorizedException("You are not allowed to delete this movement");
        }

        movementService.deleteById(movementId);

        return "redirect:/movements";
    }

    @GetMapping("/update/{id}")
    public String updateMovement(@PathVariable Integer id, Model model) {
        Movement movement = movementService.findById(id);
        Category[] categories = Category.values();
        Status[] statuses = Status.values();

        User user = userService.getCurrentUser();

        if (!movement.getUserId().getUserId().equals(user.getUserId())) {
            throw new UserNotAuthorizedException("You are not allowed to update this movement");
        }

        model.addAttribute("movement", movement);

        model.addAttribute("categories", categories);
        model.addAttribute("statuses", statuses);
        model.addAttribute("user", user);

        return "movements/update-movement";
    }

    @PostMapping("/update")
    public String updateMovement(@ModelAttribute Movement movement, Model model) {
        movement.setCreatedAt(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        User user = userService.getCurrentUser();

        movement.setUserId(user);
        movementService.save(movement);

        return "redirect:/movements/list";
    }

    @GetMapping("/detail/{id}")
    public String viewMovement(@PathVariable Integer id, Model model) {
        Movement movement = movementService.findById(id);
        User user = userService.getCurrentUser();

        if (!movement.getUserId().getUserId().equals(user.getUserId())) {
            throw new UserNotAuthorizedException("You are not allowed to access this movement");
        }

        model.addAttribute("movement", movement);
        model.addAttribute("user", user);


        return "movements/movement-detail";

    }
}
