package com.raywenderlich.bookingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.raywenderlich.bookingapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.raywenderlich.bookingapp.Activity.MainActivity.MyPREFERENCES;

public class Profilim extends AppCompatActivity {
    int mulkid;
    private Veritabani dBHelper;
    Veritabani db;
    Button button_mulk;
    TextView textView_adsayod ;
    SharedPreferences sharedpreferences;
    ListView list_mulk;
    List<Mulks> myMulkList;
    ArrayList<Mulk_islem> listem;
    private static final String TAG = "Profilim";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilim);

        Toolbar toolbar = findViewById(R.id.profilimmm);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        db= new Veritabani(getApplicationContext());

        Log.d(TAG, "onCreate: Started");

        textView_adsayod = findViewById(R.id.textView_adsayad);
        dBHelper = new Veritabani(Profilim.this);

        list_mulk = findViewById(R.id.list_mulk);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String isid=sharedpreferences.getString("id","");
        String isad=sharedpreferences.getString("adsoyad","");
        final int isidint=Integer.parseInt(isid);

        textView_adsayod.setText(isad);

        listem = new ArrayList<>();
        final List<Mulks>myMulkList= db.GetMulkList(isidint); //giriş yapan kullanıcının mülk listesi
        for (int i=0;i<myMulkList.size();i++){
            Mulks mulk = myMulkList.get(i);
            Mulk_islem kiralik = new Mulk_islem(mulk.baslik,mulk.il , mulk.ilce,mulk.image,mulk.mulkid);
            listem.add(kiralik);
        }
        Mulk_Islem_Adapter adapter = new Mulk_Islem_Adapter(this, R.layout.adapter_view_layout,listem);
        list_mulk.setAdapter(adapter);

        button_mulk = findViewById(R.id.button_mulk);
        button_mulk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profilim.this , mulk_ekle.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerForContextMenu(list_mulk);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int menuItemIndex = item.getItemId();
        String [] menuItems = getResources().getStringArray(R.array.context_menu);
        String menuItemName = menuItems[menuItemIndex];

        switch (menuItemName) {

            case "Edit":

                break;

            case "Delete":
                if (dBHelper.MulkDelete(mulkid)){
                    Toast.makeText(getApplicationContext(),"MÜLK SİLME İŞLEM BAŞARILI",Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                    break;
                }
        }
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        switch (v.getId()){

            case R.id.list_mulk:

                AdapterView.AdapterContextMenuInfo info =
                        (AdapterView.AdapterContextMenuInfo) menuInfo;
                menu.setHeaderTitle("işlem");
                int index = info.position;
                mulkid=listem.get(index).getmulkid();
                String [] actions = getResources().getStringArray(R.array.context_menu);
                for (int i=0;i<actions.length;i++){
                    menu.add(Menu.NONE, i, i, actions[i]);
                }
                break;
        }
    }
}