package com.codecool.shop.config;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class RegistrationForm {
    @Size(min = 5, max = 30, message = "Username should be between 5 and 30 characters long.")
    private String username;

    @NotBlank(message = "Enter a valid e-mail address.")
    @Email(message = "Enter a valid e-mail address.")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    private String password2;

    @AssertTrue(message="Passwords must match.")
    private boolean isValid() {
        return password.equals(password2);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
