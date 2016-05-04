package com.ogre.slide.models;

/**
 * Created by Hungry Ogre on 4/21/2016.
 */
public class User {

    int userid;
    String name;
    String email;
    String password;
    String phone;

    // Empty constructor
    public User(){

    }
    // constructor
    public User(String name,String email,String phone, String password){
        this.name = name;
        this.email=email;
        this.password=password;
        this.phone = phone;
    }

    public User(int userid,String name,String email,String phone, String password){
        this.userid=userid;
        this.name = name;
        this.email=email;
        this.password=password;
        this.phone = phone;
    }

    // constructor
    public User(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public int getUserid()
    {
        return this.userid;
    }

    public void setUserid(int userid)
    {
        this.userid=userid;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email=email;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password=password;
    }

    public String getPhone()
    {
        return this.phone;
    }

    public void setPhone(String phone)
    {
        this.phone=phone;
    }

}
