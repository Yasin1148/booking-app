package com.raywenderlich.bookingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.raywenderlich.bookingapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.raywenderlich.bookingapp.Activity.MainActivity.MyPREFERENCES;

public class Giris extends AppCompatActivity {

    ImageView profilim;
    ListView liste_mulkum;
    List<Mulks> myMulkList;
    SharedPreferences sharedpreferences;
    ArrayList<Mulk_islem> listem;
    Veritabani db;
    Button btn_cikis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        profilim = findViewById(R.id.profilim);

        Toolbar toolbar = findViewById(R.id.booking_giris);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        liste_mulkum = findViewById(R.id.liste_mulkum);
        db= new Veritabani(getApplicationContext());

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String isid=sharedpreferences.getString("id","");
        String isad=sharedpreferences.getString("adsoyad","");

        listem = new ArrayList<>();
        final List<Mulks>myMulkList= db.GetMulkList(); //giriş yapan kullanıcının mülk listesi
        for (int i=0;i<myMulkList.size();i++){
            Mulks mulk = myMulkList.get(i);
            Mulk_islem kiralik = new Mulk_islem(mulk.baslik,mulk.il , mulk.ilce,mulk.image,mulk.mulkid);
            listem.add(kiralik);
        }
        Mulk_Islem_Adapter adapter = new Mulk_Islem_Adapter(this, R.layout.adapter_view_layout,listem);
        liste_mulkum.setAdapter(adapter);


        btn_cikis = findViewById(R.id.button_cikis);
        btn_cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //log out shared preferences da ki kullanıcı silinir ve anasayfaya gidilir.
                SharedPreferences preferences =getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(Giris.this , MainActivity.class);
                startActivity(intent);
            }
        });


        profilim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Giris.this , Profilim.class);
                startActivity(intent );
            }
        });
    }
}
