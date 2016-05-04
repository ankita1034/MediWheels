package com.ogre.slide.models;

/**
 * Created by Hungry Ogre on 4/21/2016.
 */
public class Medicine {

    int medid;
    String name;
    int power;
    int cost;

    public Medicine()
    {
    }
    public Medicine(String name,int power,int cost){
        this.name=name;
        this.power=power;
        this.cost=cost;
    }

    public int getMedid()
    {
        return this.medid;
    }

    public void setMedid(int medid)
    {
        this.medid=medid;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public int getPower()
    {
        return this.power;
    }

    public void setPower(int power)
    {
        this.power=power;
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
