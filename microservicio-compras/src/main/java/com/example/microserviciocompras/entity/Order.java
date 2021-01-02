package com.example.microserviciocompras.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="order")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String customer;
    private Date order_date;
    private String employee_asig;
    private String status;
    private Double total;

    public Order(){}

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getEmployee_asig() {
        return employee_asig;
    }

    public void setEmployee_asig(String employee_asig) {
        this.employee_asig = employee_asig;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

 
    
    
    
}
