package com.raywenderlich.bookingapp.Activity;

public class Mulks {

    public int mulkid;
    public int mulksahibiid;
    public String mtur;
    public String baslik;
    public String il;
    public String ilce;
    public String acikadres;
    public byte[] image;
    public String isitma;
    public String odasayisi;
    public String katbilgisi;
    public String ucret;
    public String ucrettipi;

    public Mulks(int mulksahibiid, String mtur, String baslik, String il, String ilce , String acikadres , byte[]  image, String isitma , String odasayisi , String katbilgisi , String ucret , String ucrettipi)
    {
        this.mtur=mtur;
        this.baslik=baslik;
        this.il=il;
        this.ilce=ilce;
        this.acikadres=acikadres;
        this.image=image;
        this.isitma=isitma;
        this.odasayisi=odasayisi;
        this.katbilgisi=katbilgisi;
        this.ucret=ucret;
        this.ucrettipi=ucrettipi;
        this.mulksahibiid=mulksahibiid;
    }

    public Mulks(int mulkid, int mulksahibiid, String mtur, String baslik, String il, String ilce , String acikadres , byte[]  image , String isitma , String odasayisi , String katbilgisi , String ucret , String ucrettipi)
    {
        this.mulkid=mulkid;
        this.mulksahibiid=mulksahibiid;
        this.mtur=mtur;
        this.baslik=baslik;
        this.il=il;
        this.ilce=ilce;
        this.acikadres=acikadres;
        this.image=image;
        this.isitma=isitma;
        this.odasayisi=odasayisi;
        this.katbilgisi=katbilgisi;
        this.ucret=ucret;
        this.ucrettipi=ucrettipi;
    }
}
