package com.sb.financeq.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty
    private String password;

    private String firstName;
    private String lastName;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private ZonedDateTime registrationDate;

    private boolean isActive;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public User() {}

    public User(Integer userId, String email, String password, String firstName, String lastName, ZonedDateTime registrationDate, boolean isActive, List<Role> roles) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
        this.roles = roles;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if(this.roles == null) {
            this.roles = new ArrayList<Role>();
        }

        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registrationDate=" + registrationDate +
                ", isActive=" + isActive +
                ", roles=" + roles +
                '}';
    }
}
