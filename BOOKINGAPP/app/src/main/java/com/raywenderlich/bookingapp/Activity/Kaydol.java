package com.raywenderlich.bookingapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.raywenderlich.bookingapp.R;

public class Kaydol extends AppCompatActivity {

    Button kayit_tamamlandi;
    EditText editkayitad , editkayitemail , editkayitsifre , editkayitsiftek , editkayitcep ;
    boolean hata = false;
    String hata_mesajı;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);

        Toolbar toolbar = findViewById(R.id.profilimmm);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        kayit_tamamlandi = findViewById(R.id.kayit_tamamlandı);
        editkayitad      = findViewById(R.id.editkayitad);
        editkayitemail   = findViewById(R.id.editkayitemail);
        editkayitsifre   = findViewById(R.id.editkayitsifre);
        editkayitsiftek  = findViewById(R.id.editkayitsiftek);
        editkayitcep     = findViewById(R.id.editkayitcep);

        kayit_tamamlandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Veritabani veritabani = new Veritabani(Kaydol.this);

                String Ad       = editkayitad.getText().toString();
                String Email    = editkayitemail.getText().toString();
                String Sifre    = editkayitsifre.getText().toString();
                String Sifretek = editkayitsiftek.getText().toString();
                String Ceptel   = editkayitcep.getText().toString();
                int sifre_karakter = Sifre.length();


                if(Ad.matches("") || Email.matches("") || Sifre.matches("") || Sifretek.matches("") || Ceptel.matches(""))
                {// Kayıt esnasında girilecek değerlerin boş bırakılmaması gerektiği için kontrol işlemi yapılmaktadır.

                    hata = true;
                    hata_mesajı = "Lütfen Gerekli Alanları Doldurunuz!!!";
                }
                else if (!Sifre.matches(Sifretek))
                {
                    hata=true;
                    hata_mesajı = "Lütfen Aynı Şifreleri Giriniz!!!";
                }
                else if (sifre_karakter < 6)
                {
                    hata_mesajı += "Şifre 7 karakterden az olamaz!!!";
                    hata = true;
                }

                if (hata)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(Kaydol.this).create();
                    alertDialog.setTitle("Hata");
                    alertDialog.setMessage(hata_mesajı);
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(RESULT_OK, "Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            editkayitsifre.setText("");
                            editkayitsiftek.setText("");
                            hata_mesajı = "";
                            hata = false;
                        }
                    });
                    alertDialog.show();
                }

                else //hata yoksa
                {
                    Veritabani db = new Veritabani(getApplicationContext());

                    if (db.EmailControl(Email))
                    {
                        hata_mesajı = "Bu Email'e ait kayit zaten mevcut !!";
                        AlertDialog alertDialog = new AlertDialog.Builder(Kaydol.this).create();
                        alertDialog.setTitle("Hata");
                        alertDialog.setMessage(hata_mesajı);
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(RESULT_OK, "Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editkayitemail.setText("");
                                editkayitsifre.setText("");
                                hata_mesajı = " ";
                                hata = false;
                            }
                        });
                        alertDialog.show();
                    }
                    else
                    {
                        Users user= new Users( Ad, Email, Sifre,Ceptel);
                        db.UserEkle(user);

                        Intent intent = new Intent(Kaydol.this , MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}