package com.example.microserviciousuario.entity;

public class Product {
   

    private int pid;

   

    private String productName;
    private String descrip;
    private Double price;
    private String img;

    public Product(){}

    public Product(int pid,String productName, String descrip, Double price, String img) {
        this.pid = pid;
        this.productName = productName;
        this.descrip = descrip;
        this.price = price;
        this.img = img;
    }
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
  

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
