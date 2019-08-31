package com.controller;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

public class TestForSale {
    @NotNull
    private int productId;

    @NotNull
    private int userId;

    @Future//只能是将来的日期
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull
    @DecimalMin(value = "0.1")
    private double price;

    @Min(1)
    @Max(100)
    @NotNull
    private int quantity;

    @NotNull
    @DecimalMax("500000.0")
    @DecimalMin("1.0")
    private double amount;

    @Pattern(regexp = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@"
            + "([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$", message = "邮件不符合模式")
    private String email;

    @Size(min = 0, max = 256)
    private String note;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
