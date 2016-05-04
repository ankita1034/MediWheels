package com.ogre.slide.models;

/**
 * Created by Hungry Ogre on 4/21/2016.
 */
public class Cart {

    int cartid;
    String medname;
    String productname;
    int quantity;
    int cost;

    public Cart(){}

    public Cart(String medname,String productname,int quantity,int cost){
        this.medname=medname;
        this.productname=productname;
        this.quantity=quantity;
        this.cost=cost;
    }

    public Cart(String medname,int quantity,int cost){
        this.medname=medname;
        this.quantity=quantity;
        this.cost=cost;
    }

    public void setCartid(int cartid)
    {
        this.cartid=cartid;
    }

    public int getCartid()
    {
        return this.cartid;
    }


    public void setMedname(String  medname)
    {
        this.medname=medname;
    }

    public String getMedname()
    {
        return this.medname;
    }

    public void setProductname(String productname)
    {
        this.productname=productname;
    }

    public String getProductname()
    {
        return this.productname;
    }
    public void setQuantity(int quantity)
    {
        this.quantity=quantity;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public void setCost(int cost)
    {
        this.cost=cost;
    }

    public int getCost()
    {
        return this.cost;
    }
}
