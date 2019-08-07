package com.example.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "users")
public class Base {

    @Id
    private String id;
    @NotEmpty(message = "Username should not be empty")
    @JsonProperty("username")
    @Indexed(unique = true)
    private String username;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8)
    @JsonIgnore
    private String password;
    @JsonProperty("roles")
    private List<String> roles = null;

    public Base() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setRole(String role) {
        if (this.roles != null) {
            if (role.equals("ADMIN") || role.equals("USER") || role.equals("PROBE"))
                this.roles.add(role);
        } else {
            this.roles = new ArrayList<String>();
            if (role.equals("ADMIN") || role.equals("USER") || role.equals("PROBE"))
                this.roles.add(role);
        }
    }
}
