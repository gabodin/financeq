package com.sb.financeq.services;

import com.sb.financeq.entities.Movement;
import com.sb.financeq.entities.MovementDTO;
import com.sb.financeq.entities.User;
import com.sb.financeq.entities.enums.Category;
import com.sb.financeq.entities.enums.Status;
import com.sb.financeq.entities.exceptions.MovementNotFoundException;
import com.sb.financeq.repositories.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovementService {
    private final MovementRepository movementRepository;
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
                        (float) result[2],
                        Category.valueOf((int) result[3]),
                        Status.valueOf((int) result[4]),
                        (String) result[5]))
                .collect(Collectors.toList());
    }

    public Page<Movement> listMovements(User userId, Pageable pageable, String sortBy, String order) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return movementRepository.findByUserId(userId, page);
    }
}
