package com.example.Success18.Authentication.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CrossOrigin

public class SignUp {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email(message = "please provide a valid email address")
    private String email;
    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40,message = "should be 6 characters and above")
    private String password;

    @NotBlank@Size(min = 6, max = 40,message = "should be 6 characters and above")
    private String confirmPassword;

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @NotBlank(message = "should be 10 and above")
    @Size(min = 10, max = 12)
    private String phoneNo;

}
