package com.ogre.slide.models;

/**
 * Created by Hungry Ogre on 4/21/2016.
 */
public class Appointment {

    int appid;
    int doctorid;
    String time;
    String date;

    public Appointment(){}

    public Appointment(int doctorid,String date,String time){
        this.doctorid=doctorid;
        this.date=date;
        this.time=time;
    }

    public void setAppid(int appid)
    {
        this.appid=appid;
    }
    public int getAppid()
    {
        return this.appid;
    }

    public void setDoctorid(int doctorid)
    {
        this.doctorid=doctorid;
    }
    public int getDoctorid()
    {
        return this.doctorid;
    }

    public void setTime(String time)
    {
        this.time=time;
    }
    public String getTime()
    {
        return this.time;
    }

    public void setDate(String date)
    {
        this.date=date;
    }
    public String getDate()
    {
        return this.date;
    }
}
