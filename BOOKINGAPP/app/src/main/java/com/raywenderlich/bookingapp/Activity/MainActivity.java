package com.raywenderlich.bookingapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.raywenderlich.bookingapp.R;

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    Button kayitol , giris;
    EditText editTextemail , editTextsifre;
    String Email, Sifre , hata_mesaji ;
    Boolean hata = false;
    SharedPreferences sharedpreferences;
    private Veritabani dBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT > 20)
            window.setStatusBarColor(getResources().getColor(R.color.color_white));

        Veritabani veritabani = new Veritabani(MainActivity.this);

        dBHelper = new Veritabani(MainActivity.this);

        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        kayitol = findViewById(R.id.kayitol);
        giris   = findViewById(R.id.giris);
        editTextsifre = findViewById(R.id.editTextsifre);
        editTextemail = findViewById(R.id.editTextemail);

        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , Kaydol.class);
                startActivity(intent);

            }
        });

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email = editTextemail.getText().toString();
                Sifre = editTextsifre.getText().toString();

                if (Email.matches(""))
                {
                    hata_mesaji += "Email Alanını Lütfen Düzgün Doldurunuz";
                    hata = true;
                }

                int sifre_karakter = Sifre.length();
                if (sifre_karakter < 6)
                {
                    hata_mesaji += "Şifre 6 Karakterden Az Olamaz";
                    hata = true;
                }

                if (hata)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Hata");
                    alertDialog.setMessage(hata_mesaji);
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(RESULT_OK, "Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editTextsifre.setText("");
                            hata_mesaji = " ";
                            hata = false;
                        }
                    });
                    alertDialog.show();
                }
                else
                {
                    Veritabani db = new Veritabani(getApplicationContext());
                    Users isuser=db.LoginControl(Email,Sifre);
                    if (isuser.id>0){//giriş başarılı
                       SharedPreferences.Editor editor = sharedpreferences.edit();
                       String userid=String.valueOf(isuser.id) ;
                        editor.putString("id",userid);
                        editor.putString("adsoyad", isuser.ad);
                        editor.commit();

                        Intent iintent = new Intent(MainActivity.this , Giris.class);
                        startActivity(iintent);
                    }
                    else{

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Hata");
                    alertDialog.setMessage(hata_mesaji);
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(RESULT_OK, "Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editTextsifre.setText("");
                            hata_mesaji = "Giriş başarısız. Email veya şifre hatalı ";
                            hata = false;
                        }
                    });
                    alertDialog.show();
                    }
                }
            }
        });
    }
}