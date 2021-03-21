package com.raywenderlich.bookingapp.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class Veritabani extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "veritabani";
    private static final int DATABASE_VERSION = 8;
    private static final String KAYITLAR_TABLE = "kayitlar";
    private static final String MULKLER_TABLE = "mulkler";

    public static final String ROW_ID = "id";
    public static final String ROW_AD = "ad";
    public static final String ROW_EMAIL = "email";
    public static final String ROW_SIFRE = "sifre";
    public static final String ROW_CEPTEL = "ceptel";

    public static final String ROW_MULKID = "mulkid";
    public static final String ROW_MULKSAHIBIID = "mulksahibiid";
    public static final String ROW_MTUR = "mtur";
    public static final String ROW_MBASLIK = "baslik";
    public static final String ROW_IL = "il";
    public static final String ROW_ILCE = "ilce";
    public static final String ROW_ACIKADRES = "acikadres";
    public static final String ROW_IMAGE = "image";
    public static final String ROW_ISITMA = "isitma";
    public static final String ROW_ODASAYISI = "odasayisi";
    public static final String ROW_KATBILGISI = "katbilgisi";
    public static final String ROW_UCRET = "ucret";
    public static final String ROW_UCRETTIPI = "ucrettipi";

    public static final String SQL_TABLE_USERS = "CREATE TABLE " + KAYITLAR_TABLE + "("
            +ROW_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            +ROW_AD+" TEXT NOT NULL, "
            +ROW_EMAIL+" TEXT NOT NULL, "
            +ROW_SIFRE+" TEXT NOT NULL, "
            +ROW_CEPTEL+" TEXT NOT NULL)";

    public static final String SQL_TABLE_MULKLER= "CREATE TABLE " + MULKLER_TABLE + "("
            +ROW_MULKID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            +ROW_MTUR+" TEXT NOT NULL, "
            +ROW_MBASLIK+" TEXT NOT NULL, "
            +ROW_IL+" TEXT NOT NULL, "
            +ROW_ILCE+" TEXT NOT NULL, "
            +ROW_ACIKADRES+" TEXT NOT NULL, "
            +ROW_IMAGE+" BLOB NOT NULL, "
            +ROW_ISITMA+" TEXT NOT NULL, "
            +ROW_ODASAYISI+" TEXT NOT NULL, "
            +ROW_KATBILGISI+" TEXT NOT NULL, "
            +ROW_UCRET+" TEXT NOT NULL, "
            +ROW_UCRETTIPI+" TEXT NOT NULL, "
            +ROW_MULKSAHIBIID+" INTEGER NOT NULL)";
    public Veritabani(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) //sadece ilk veri tabanı oluşturulduğunda çalışır
    {
        db.execSQL(SQL_TABLE_USERS);
        db.execSQL(SQL_TABLE_MULKLER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int iL) //veritabanında güncelleme yapıldığında bu metot içerisinde tablolar yeniden oluşturulur //veri tabanı versiyonuu güncellendiğinde çalışır.
    {
        Log.w(Veritabani.class.getName(),
                "Upgrading database from version " + i + " to "
                        + iL + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + KAYITLAR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MULKLER_TABLE);
        onCreate(db);

    }

    public void UserEkle(Users users)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ROW_AD , users.ad);
        values.put(ROW_EMAIL , users.email);
        values.put(ROW_SIFRE , users.sifre);
        values.put(ROW_CEPTEL , users.ceptel);
        long newRowId = db.insert(KAYITLAR_TABLE, null, values);
    }
    public boolean MulkDelete(int mulkid){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(MULKLER_TABLE,  "mulkid = ?", new String[] {String.valueOf(mulkid)}) > 0;
    }
    public void MulkEkle(Mulks mulk)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ROW_MTUR , mulk.mtur);
        values.put(ROW_MBASLIK , mulk.baslik);
        values.put(ROW_IL , mulk.il);
        values.put(ROW_ILCE , mulk.ilce);

        values.put(ROW_ACIKADRES , mulk.acikadres);
        values.put(ROW_IMAGE , mulk.image);
        values.put(ROW_ISITMA , mulk.isitma);
        values.put(ROW_ODASAYISI , mulk.odasayisi);
        values.put(ROW_KATBILGISI , mulk.katbilgisi);
        values.put(ROW_UCRET , mulk.ucret);
        values.put(ROW_UCRETTIPI , mulk.ucrettipi);
        values.put(ROW_MULKSAHIBIID , mulk.mulksahibiid);
        long newRowId = db.insert(MULKLER_TABLE, null, values);
    }
    public List<Mulks> GetMulkList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mulkler", null);
        int rowCount = cursor.getCount();
        List<Mulks> mulkList = new ArrayList<Mulks>();
        for (int i = 0; i < rowCount; i++) {
            while (cursor.moveToNext()){

                int mulkidis = cursor.getInt(cursor.getColumnIndex(""+ROW_MULKID));
                int mulksahibiidis = cursor.getInt(cursor.getColumnIndex(""+ROW_MULKSAHIBIID));
                String mturis = cursor.getString(cursor.getColumnIndex(""+ROW_MTUR));
                String baslikis = cursor.getString(cursor.getColumnIndex(""+ROW_MBASLIK));
                String ilis = cursor.getString(cursor.getColumnIndex(""+ROW_IL));
                String ilceis = cursor.getString(cursor.getColumnIndex(""+ROW_ILCE));
                String acikadresis = cursor.getString(cursor.getColumnIndex(""+ROW_ACIKADRES));
                byte[] imageis = cursor.getBlob(cursor.getColumnIndex(""+ROW_IMAGE));
                String isitmais = cursor.getString(cursor.getColumnIndex(""+ROW_ISITMA));
                String odasayisiis = cursor.getString(cursor.getColumnIndex(""+ROW_ODASAYISI));
                String katbilgisiis = cursor.getString(cursor.getColumnIndex(""+ROW_KATBILGISI));
                String ucretis = cursor.getString(cursor.getColumnIndex(""+ROW_UCRET));
                String ucrettipiis = cursor.getString(cursor.getColumnIndex(""+ROW_UCRETTIPI));
                Mulks mulk = new Mulks(mulkidis,mulksahibiidis,mturis,baslikis,ilis,ilceis,acikadresis,imageis,isitmais,odasayisiis,katbilgisiis,ucretis,ucrettipiis);
                mulkList.add(mulk);
            }
        }
        db.close();
        cursor.close();
        return mulkList;
    }
    public List<Mulks> GetMulkList(int isUserid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mulkler WHERE mulksahibiid= '"+isUserid+"'", null);

        int rowCount = 0;
        if (cursor !=null)
            rowCount = cursor.getCount();

        List<Mulks> mulkList = new ArrayList<Mulks>();
        for (int i = 0; i < rowCount; i++) {
            while (cursor.moveToNext()){

                int mulkidis = cursor.getInt(cursor.getColumnIndex(""+ROW_MULKID));
                int mulksahibiidis = cursor.getInt(cursor.getColumnIndex(""+ROW_MULKSAHIBIID));
                String mturis = cursor.getString(cursor.getColumnIndex(""+ROW_MTUR));
                String baslikis = cursor.getString(cursor.getColumnIndex(""+ROW_MBASLIK));
                String ilis = cursor.getString(cursor.getColumnIndex(""+ROW_IL));
                String ilceis = cursor.getString(cursor.getColumnIndex(""+ROW_ILCE));
                String acikadresis = cursor.getString(cursor.getColumnIndex(""+ROW_ACIKADRES));
                byte[] imageis = cursor.getBlob(cursor.getColumnIndex(""+ROW_IMAGE));
                String isitmais = cursor.getString(cursor.getColumnIndex(""+ROW_ISITMA));
                String odasayisiis = cursor.getString(cursor.getColumnIndex(""+ROW_ODASAYISI));
                String katbilgisiis = cursor.getString(cursor.getColumnIndex(""+ROW_KATBILGISI));
                String ucretis = cursor.getString(cursor.getColumnIndex(""+ROW_UCRET));
                String ucrettipiis = cursor.getString(cursor.getColumnIndex(""+ROW_UCRETTIPI));
                Mulks mulk = new Mulks(mulkidis,mulksahibiidis,mturis,baslikis,ilis,ilceis,acikadresis,imageis,isitmais,odasayisiis,katbilgisiis,ucretis,ucrettipiis);
                mulkList.add(mulk);
            }
        }
        db.close();
        cursor.close();
        return mulkList;
    }

    public List<Users> GetUserList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM kayitlar", null);
        int rowCount = cursor.getCount();
        List<Users> userList = new ArrayList<Users>();
        for (int i = 0; i < rowCount; i++) {
            while (cursor.moveToNext()){
                int idis = cursor.getInt(cursor.getColumnIndex(""+ROW_ID));
                String adis = cursor.getString(cursor.getColumnIndex(""+ROW_AD));
                String emailis = cursor.getString(cursor.getColumnIndex(""+ROW_EMAIL));
                String sifreis = cursor.getString(cursor.getColumnIndex(""+ROW_SIFRE));
                String ceptelis = cursor.getString(cursor.getColumnIndex(""+ROW_CEPTEL));
                Users user = new Users(idis,adis,emailis,sifreis,ceptelis);
                userList.add(user);
            }
        }
        db.close();
        cursor.close();

        return userList;
    }

    public boolean EmailControl(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor2 = db.rawQuery("SELECT * FROM kayitlar WHERE email= '"+email+"'", null);
        int rowCount = cursor2.getCount();
        db.close();
        cursor2.close();
        if (rowCount>0)
            return true;
        return false;
    }
    public Users LoginControl(String email,String sifre)
    {
        Users user = new Users(0,"0","0","","");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor2 = db.rawQuery("SELECT * FROM kayitlar WHERE email= '"+email+"' and sifre='"+sifre+"'", null);
        int rowCount = cursor2.getCount();
        cursor2.moveToFirst();
        if (rowCount>0) {
            int idis = cursor2.getInt(cursor2.getColumnIndex("" + ROW_ID));
            String adis = cursor2.getString(cursor2.getColumnIndex("" + ROW_AD));
            String emailis = cursor2.getString(cursor2.getColumnIndex("" + ROW_EMAIL));
            String sifreis = cursor2.getString(cursor2.getColumnIndex("" + ROW_SIFRE));
            String ceptelis = cursor2.getString(cursor2.getColumnIndex("" + ROW_CEPTEL));
            user.id = idis;
            user.ad = adis;
            user.sifre = sifreis;
            user.email = emailis;
            user.ceptel = ceptelis;
        }
        db.close();
        cursor2.close();
        return user;
    }
}