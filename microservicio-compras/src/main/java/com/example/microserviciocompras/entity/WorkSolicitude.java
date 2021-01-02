package com.example.microserviciocompras.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "worksolicitude")
public class WorkSolicitude {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String customer_email;
    private Date order_date;

    private String employee_asig;
    private String status;
    private BigDecimal total;
    
    public WorkSolicitude(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


}
