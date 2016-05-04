package com.ogre.slide.models;

/**
 * Created by Hungry Ogre on 4/11/2016.
 */
public class ListModel {

    private String medicineName="";
    private int price;
    int quantity;

    public String getmedicineName()
    {
        return this.medicineName;
    }
    public void setmedicineName(String medicineName)
    {
        this.medicineName=medicineName;
    }

    public int getPrice()
    {
        return this.price;
    }
    public void setPrice(int price)
    {
        this.price=price;
    }

    public int getQuantity()
    {
        return this.quantity;
    }
    public void setQuantity(int quantity)
    {
        this.quantity=quantity;
    }

}
