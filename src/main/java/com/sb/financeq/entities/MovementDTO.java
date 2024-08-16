package com.sb.financeq.entities;

import com.sb.financeq.entities.enums.Category;
import com.sb.financeq.entities.enums.Status;

import java.math.BigDecimal;

public record MovementDTO(
        Integer movementId, String title, BigDecimal amount, Category category, Status status, String movementDate
) {
}
