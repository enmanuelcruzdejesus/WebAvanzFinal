package com.example.microserviciousuario.entity;

public class UserDto {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDto(User user, String role) {
        this.user = user;
        this.role = role;
    }

    User user;
    String role;
    
    public UserDto()
    {}    
}
