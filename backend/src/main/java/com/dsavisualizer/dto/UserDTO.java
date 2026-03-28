package com.dsavisualizer.dto;

import com.dsavisualizer.model.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String bio;
    private String profilePicture;
    private Set<Role> roles;
    private LocalDateTime createdAt;
    private Integer problemsSolved;
    private Double averageAccuracy;
}
