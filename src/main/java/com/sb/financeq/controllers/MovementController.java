package com.sb.financeq.controllers;

import com.sb.financeq.entities.Movement;
import com.sb.financeq.entities.MovementDTO;
import com.sb.financeq.entities.User;
import com.sb.financeq.entities.enums.Category;
import com.sb.financeq.entities.enums.Status;
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
        List<MovementDTO> movementsDto = movementService.listDto(1);

        model.addAttribute("movements", movementsDto);

        return "movements/movements-list-dto";
    }

    @GetMapping("/new")
    public String newMovement(Model model) {
        Movement movement = new Movement();
        Category[] categories = Category.values();
        Status[] statuses = Status.values();

        model.addAttribute("movement", movement);
        model.addAttribute("categories", categories);
        model.addAttribute("statuses", statuses);

        return "movements/new-movement";
    }

    @PostMapping("/add")
    public String addMovement(@ModelAttribute Movement movement, Model model) {
        System.out.println(movement);

        Optional<User> user = userService.findById(1);

        user.ifPresent(movement::setUserId);
        movement.setCreatedAt(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));

        movementService.save(movement);

        return "redirect:/movements/list";
    }

    @GetMapping("/delete/{movementId}")
    public String deleteMovement(@PathVariable Integer movementId, Model model) {

        movementService.deleteById(movementId);

        return "redirect:/movements/list";
    }

    @GetMapping("/update/{id}")
    public String updateMovement(@PathVariable Integer id, Model model) {
        Optional<Movement> movement = movementService.findById(id);
        Category[] categories = Category.values();
        Status[] statuses = Status.values();

        movement.ifPresentOrElse(movement1 -> model.addAttribute("movement", movement1), () -> {
            throw new RuntimeException("Movement not found");
        });

        model.addAttribute("categories", categories);
        model.addAttribute("statuses", statuses);

        return "movements/update-movement";
    }

    @PostMapping("/update")
    public String updateMovement(@ModelAttribute Movement movement, Model model) {
        movement.setCreatedAt(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        Optional<User> user = userService.findById(1);

        user.ifPresent(movement::setUserId);
        movementService.save(movement);

        return "redirect:/movements/list";
    }
}
