package com.sb.financeq.entities;

import com.sb.financeq.entities.enums.Category;
import com.sb.financeq.entities.enums.Status;

public record MovementDTO(
        Integer movementId, String title, float amount, Category category, Status status, String movementDate
) {
}
