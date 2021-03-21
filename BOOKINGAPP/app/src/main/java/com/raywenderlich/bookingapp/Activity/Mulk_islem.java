package com.raywenderlich.bookingapp.Activity;

public class Mulk_islem {
    private String baslik;
    private String Il;
    private String Ilce;
    private byte[] imgURL;
    private int mulkid;
    public Mulk_islem(String baslik, String Il, String Ilce, byte[]  imgURL,int mulkid)
    {
        this.baslik=baslik;
        this.Il=Il;
        this.Ilce=Ilce;
        this.imgURL=imgURL;
        this.mulkid=mulkid;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getIl() {
        return Il;
    }

    public void setIl(String il) {
        Il = il;
    }

    public String getIlce() {
        return Ilce;
    }

    public void setIlce(String ilce) {
        Ilce = ilce;
    }

    public byte[]  getImgURL() {
        return imgURL;
    }

    public void setImgURL(byte[]  imgURL) {
        this.imgURL = imgURL;
    }
    public int getmulkid() {
        return mulkid;
    }

    public void setMulkid(int mulkid) {
        this.mulkid = mulkid;
    }
}
