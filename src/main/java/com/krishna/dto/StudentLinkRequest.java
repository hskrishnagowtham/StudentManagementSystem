
package com.krishna.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class StudentLinkRequest {

    @NotNull(message = "Roll number is required")
    @Min(value = 1, message = "Roll number must be positive")
    private Integer rollNumber;

    @NotBlank(message = "Username is required")
    private String username;

    public StudentLinkRequest() {
    }

    public Integer getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(Integer rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
