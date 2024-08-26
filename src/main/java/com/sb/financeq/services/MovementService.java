package com.sb.financeq.services;

import com.sb.financeq.entities.Movement;
import com.sb.financeq.entities.MovementDTO;
import com.sb.financeq.entities.enums.Category;
import com.sb.financeq.entities.enums.Status;
import com.sb.financeq.entities.exceptions.MovementNotFoundException;
import com.sb.financeq.repositories.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovementService {
    private final MovementRepository movementRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    public MovementService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public List<Movement> getAll() {
        return movementRepository.findAll();
    }

    public Movement findById(Integer id) {

        return movementRepository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException("Movement not found with id: " + id));
    }

    public Movement save(Movement movement) {
        return movementRepository.save(movement);
    }

    public void deleteById(Integer id) {
        movementRepository.deleteById(id);
    }

    public List<MovementDTO> getMovementsDtoByUser(Integer userId) {
        List<Object[]> results = movementRepository.getUserMovementDto(userId);
        return results.stream()
                .map(result -> new MovementDTO(
                        (Integer) result[0],
                        (String) result[1],
                        (BigDecimal) result[2],
                        Category.valueOf((int) result[3]),
                        Status.valueOf((int) result[4]),
                        ((Timestamp) result[5]).toLocalDateTime().toLocalDate().format(formatter)))
                .collect(Collectors.toList());
    }
}
