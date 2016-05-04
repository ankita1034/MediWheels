package com.ogre.slide.models;

/**
 * Created by Hungry Ogre on 4/21/2016.
 */
public class HealthProduct {

    int healthid;
    String name;
    int cost;

    public HealthProduct()
    {
    }
    public HealthProduct(String name,int cost){
        this.name=name;
        this.cost=cost;
    }

    public int getHealthid()
    {
        return this.healthid;
    }

    public void setHealthid(int healthid)
    {
        this.healthid=healthid;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public int getCost()
    {
        return this.cost;
    }

    public void setCost(int cost)
    {
        this.cost=cost;
    }
}
