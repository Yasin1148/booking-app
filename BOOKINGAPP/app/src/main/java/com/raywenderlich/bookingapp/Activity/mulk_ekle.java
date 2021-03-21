package com.raywenderlich.bookingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.raywenderlich.bookingapp.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.raywenderlich.bookingapp.Activity.MainActivity.MyPREFERENCES;

public class mulk_ekle extends AppCompatActivity {

    private  static final int PICK_IMAGE=100;
    Uri imageUri;
    Veritabani db;
    EditText edittext_mbaslik , edittext_acikadres , editText_ucret;
    CheckBox ucrettipigunluk,ucrettipiaylik;
    Button btn_kayit_tamamla;
    ImageView imageView_mulkresmi;
    String Mulk_basligi , Acik_adres , Ucret;
    String secilenil,secilenilce,secilenmulturu,secilenisitmaturu,secilenodasayisi,secilenkatbilgisi, secilenucrettipi;
    SharedPreferences sharedpreferences;
    Spinner spinner_mtur,spinner_isitma,spinner_oda_sayisi,spinner_kat_bilgisi;
    //Spinner içerisine koyacağımız verileri tanımlıyoruz.
    private String[] iller={"MUĞLA","ANKARA","ADANA"};
    private String[] ilceler0={"FETHİYE","BODRUM","MARMARİS"};
    private String[] ilceler1={"ALTINDAĞ","ÇANKAYA","KEÇİÖREN"};
    private String[] ilceler2={"SEYHAN","CEYHAN","POZANTI"};

    //Spinner'ları ve Adapter'lerini tanımlıyoruz.
    private Spinner spinnerIller;
    private Spinner spinnerIlceler;
    private ArrayAdapter<String> dataAdapterForIller;
    private ArrayAdapter<String> dataAdapterForIlceler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulk_ekle);

        db= new Veritabani(getApplicationContext());

        ucrettipiaylik = findViewById(R.id.checkBox_aylik);
        ucrettipigunluk = findViewById(R.id.checkBox_gunluk);

        edittext_mbaslik = findViewById(R.id.edittext_mbaslik);
        edittext_acikadres = findViewById(R.id.edittext_acikadres);
        editText_ucret = findViewById(R.id.editText_ucret);
        btn_kayit_tamamla = findViewById(R.id.btn_kayit_tamamla);
        spinner_mtur = findViewById(R.id.spinner_mtur);
        spinner_isitma = findViewById(R.id.spinner_isitma);
        spinner_oda_sayisi = findViewById(R.id.spinner_oda_sayisi);
        spinner_kat_bilgisi = findViewById(R.id.spinner_kat_bilgisi);
        imageView_mulkresmi = findViewById(R.id.imageView_mulkresmi);
        spinnerIller = findViewById(R.id.spinner_il);
        spinnerIlceler = findViewById(R.id.spinner_ilce);
        dataAdapterForIller = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, iller);
        dataAdapterForIlceler = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,ilceler0);
        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForIller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        spinnerIller.setAdapter(dataAdapterForIller);
        spinnerIlceler.setAdapter(dataAdapterForIlceler);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String isid=sharedpreferences.getString("id","");
        String isad=sharedpreferences.getString("adsoyad","");
        final int isidint=Integer.parseInt(isid);

        btn_kayit_tamamla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView tempimg= imageView_mulkresmi;
                byte[] bytes = imageViewToByte(imageView_mulkresmi);
                Mulk_basligi = edittext_mbaslik.getText().toString();
                Ucret = editText_ucret.getText().toString();
                Acik_adres = edittext_acikadres.getText().toString();
                secilenil=spinnerIller.getSelectedItem().toString();
                secilenilce=spinnerIlceler.getSelectedItem().toString();
                secilenmulturu=spinner_mtur.getSelectedItem().toString();
                secilenisitmaturu=spinner_isitma.getSelectedItem().toString();
                secilenodasayisi=spinner_oda_sayisi.getSelectedItem().toString();
                secilenkatbilgisi=spinner_kat_bilgisi.getSelectedItem().toString();
                if (ucrettipiaylik.isChecked()){
                    secilenucrettipi="Aylık";
                }
                else if (ucrettipigunluk.isChecked()){
                    secilenucrettipi="Günlük";
                }
                Mulks mulk=new Mulks(isidint,secilenmulturu,Mulk_basligi,secilenil,secilenilce,Acik_adres,bytes,secilenisitmaturu,secilenodasayisi,secilenkatbilgisi,Ucret,secilenucrettipi);
                db.MulkEkle(mulk);

                Intent intent = new Intent(mulk_ekle.this, Profilim.class);
                startActivity(intent);
            }
        });

        spinner_mtur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerIller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Hangi il seçilmişse onun ilçeleri adapter'e ekleniyor.
                if(parent.getSelectedItem().toString().equals(iller[0]))
                    dataAdapterForIlceler = new ArrayAdapter<String>(mulk_ekle.this, android.R.layout.simple_spinner_item,ilceler0);
                else if(parent.getSelectedItem().toString().equals(iller[1]))
                    dataAdapterForIlceler = new ArrayAdapter<String>(mulk_ekle.this, android.R.layout.simple_spinner_item,ilceler1);
                else if(parent.getSelectedItem().toString().equals(iller[2]))
                    dataAdapterForIlceler = new ArrayAdapter<String>(mulk_ekle.this, android.R.layout.simple_spinner_item,ilceler2);

                dataAdapterForIlceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerIlceler.setAdapter(dataAdapterForIlceler);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerIlceler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imageView_mulkresmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        spinner_isitma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_oda_sayisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_kat_bilgisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private byte [] imageViewToByte(ImageView image){
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return  byteArray;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            imageUri=data.getData();
            imageView_mulkresmi.setImageURI(imageUri);
        }
    }
}