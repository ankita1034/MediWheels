package com.ogre.slide.models;

/**
 * Created by Hungry Ogre on 4/21/2016.
 */
public class Doctor {

    int doctorid;
    String name;
    String specializtion;

    public Doctor()
    {
    }
    public Doctor(String name,String specializtion){
        this.name=name;
        this.specializtion=specializtion;
    }

    public int getDoctorid()
    {
        return this.doctorid;
    }

    public void setDoctorid(int doctorid)
    {
        this.doctorid=doctorid;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public String getSpecializtion()
    {
        return this.specializtion;
    }

    public void setSpecializtion(String specializtion)
    {
        this.specializtion=specializtion;
    }
}
