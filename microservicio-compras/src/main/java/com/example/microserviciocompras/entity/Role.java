package com.example.microserviciocompras.entity;

public class Role {
    private Long id;
    private String name;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Role(){}
    public Role(String name){
        this.name= name;
    }


}
