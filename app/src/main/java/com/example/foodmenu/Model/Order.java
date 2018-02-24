package com.example.foodmenu.Model;

/**
 * Created by engmnmubarak on 2/24/2018.
 */

public class Order {

    private String ProdectId;
    private String ProdectName;
    private String Quantity;
    private String Price;
    private String Discount;

    public Order() {
    }

    public Order(String prodectId, String prodectName, String quantity, String price, String discount) {
        ProdectId = prodectId;
        ProdectName = prodectName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public String getProdectId() {
        return ProdectId;
    }

    public void setProdectId(String prodectId) {
        ProdectId = prodectId;
    }

    public String getProdectName() {
        return ProdectName;
    }

    public void setProdectName(String prodectName) {
        ProdectName = prodectName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
