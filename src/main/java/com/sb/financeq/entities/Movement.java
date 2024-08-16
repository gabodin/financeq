package com.sb.financeq.entities;

import com.sb.financeq.entities.enums.Category;
import com.sb.financeq.entities.enums.Status;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "movement")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movementId;

    private String title;

    private BigDecimal amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate movementDate;

    private ZonedDateTime createdAt;

    private String description;

    private Integer category;

    private Integer status;

    @Column(name = "essential")
    private boolean isEssential;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User userId;

    public Movement() {}

    public Movement(Integer movementId, String title, BigDecimal amount, LocalDate movementDate, ZonedDateTime createdAt, String description, Integer category, Integer status, boolean isEssential, User userId) {
        this.movementId = movementId;
        this.title = title;
        this.amount = amount;
        this.movementDate = movementDate;
        this.createdAt = createdAt;
        this.description = description;
        this.category = category;
        this.status = status;
        this.isEssential = isEssential;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDate movementDate) {
        this.movementDate = movementDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getMovementId() {
        return movementId;
    }

    public void setMovementId(Integer movementId) {
        this.movementId = movementId;
    }

    public boolean getIsEssential() {
        return isEssential;
    }

    public void setIsEssential(boolean essential) {
        this.isEssential = essential;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        if(this.category == null) return null;
        else return Category.valueOf(this.category);
    }

    public void setCategory(Category category) {
        if(category != null) {
            this.category = category.getCode();
        }
    }

    public Status getStatus() {
        if(this.status == null) return null;
        else return Status.valueOf(this.status);
    }

    public void setStatus(Status status) {
        if(status != null) {
            this.status = status.getCode();
        }
    }

    @Override
    public String toString() {
        return "Movement{" +
                "movementId=" + movementId +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", movementDate=" + movementDate +
                ", createdAt=" + createdAt +
                ", description='" + description + '\'' +
                ", category=" + getCategory() +
                ", status=" + this.getStatus() +
                ", isEssential=" + isEssential +
                ", userId=" + userId +
                '}';
    }
}
