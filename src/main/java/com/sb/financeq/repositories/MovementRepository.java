package com.sb.financeq.repositories;

import com.sb.financeq.entities.Movement;
import com.sb.financeq.entities.MovementDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer> {

    @Query(value = " SELECT m.movement_id, m.title, m.amount, m.category, m.status, m.movement_date"
            + " FROM movement m"
            + " where m.user_id = :user", nativeQuery = true)
    List<Object[]> getUserMovementDto(@Param("user") Integer user);

    
}
