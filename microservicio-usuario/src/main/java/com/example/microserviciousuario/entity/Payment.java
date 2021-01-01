package com.example.microserviciousuario.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class Payment {

    private int id;
    private String transaction_id;
    private String invoice_id;
    private String customer_id;
    private String payer_email;
    private String business;
    private Date order_date;
    private String address_city;
    private String address_zip;
    private String address_state;
    private String address_name;
    private String item_name;
    private BigDecimal payment_gross;
    private BigDecimal handling_amount;
    private BigDecimal payment_fee;
    private BigDecimal shipping;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPayer_email() {
        return payer_email;
    }

    public void setPayer_email(String payer_email) {
        this.payer_email = payer_email;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_zip() {
        return address_zip;
    }

    public void setAddress_zip(String address_zip) {
        this.address_zip = address_zip;
    }

    public String getAddress_state() {
        return address_state;
    }

    public void setAddress_state(String address_state) {
        this.address_state = address_state;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public BigDecimal getPayment_gross() {
        return payment_gross;
    }

    public void setPayment_gross(BigDecimal payment_gross) {
        this.payment_gross = payment_gross;
    }

    public BigDecimal getHandling_amount() {
        return handling_amount;
    }

    public void setHandling_amount(BigDecimal handling_amount) {
        this.handling_amount = handling_amount;
    }

    public BigDecimal getPayment_fee() {
        return payment_fee;
    }

    public void setPayment_fee(BigDecimal payment_fee) {
        this.payment_fee = payment_fee;
    }

    public BigDecimal getShipping() {
        return shipping;
    }

    public void setShipping(BigDecimal shipping) {
        this.shipping = shipping;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment() {
    }

}
