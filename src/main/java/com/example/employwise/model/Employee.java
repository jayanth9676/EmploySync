package com.example.employwise.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

@Data
@Document(collection = "employees")
public class Employee {
    @Id
    private UUID id;

    @NotBlank(message = "Employee name is required")
    private String employeeName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private UUID reportsTo;

    @NotBlank(message = "Profile image URL is required")
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
    private String profileImage;
}