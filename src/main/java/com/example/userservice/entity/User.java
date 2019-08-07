package com.example.userservice.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class User extends Base{

    @NotEmpty(message = "Email should not be empty")
    @Email
    @JsonProperty("mail")
    private String mail;

    public User(){
        super();
    }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

}
