package com.example.microserviciousuario.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "appsetting")
public class AppSetting {

    @Id
    private int id;
    private String descrip;
    private String value;

    public AppSetting(){}
    public AppSetting(int id, String descrip, String value) {
        this.id = id;
        this.descrip = descrip;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}