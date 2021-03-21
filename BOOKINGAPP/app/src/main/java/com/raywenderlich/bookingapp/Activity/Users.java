package com.raywenderlich.bookingapp.Activity;

public class Users {

    public int id;
    public String ad;
    public String email;
    public String sifre;
    public String ceptel;

    public Users( String ad, String email, String sifre,String ceptel)
    {
        this.ad=ad;
        this.email=email;
        this.sifre=sifre;
        this.ceptel=ceptel;
    }

    public Users(int id,String ad, String email, String sifre, String ceptel)
    {
        this.id=id;
        this.ad=ad;
        this.email=email;
        this.sifre=sifre;
        this.ceptel=ceptel;
    }
}
