package com.example.filippocasa.myapplicationactionbar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.filippocasa.myapplicationactionbar.R;
import com.example.filippocasa.myapplicationactionbar.databaseProduct.dbLocalForSend.MYDatabaseCarrello;
import com.example.filippocasa.myapplicationactionbar.models.UserModel;


public class ChoiseProductActivity extends AppCompatActivity {

    public static final String USER_DATA_EXTRA = "com.example.filippocasa.myapplicationactionbar.extra.USER_DATA_EXTRA";
    public static final String CHOISE_ACTION = "com.example.filippocasa.myapplicationactionbar.action.CHOISE_ACTION";
    public static final String USER_DATA_KEY = "com.example.filippocasa.myapplicationactionbar.key.USER_DATA_KEY";


    public static final int CONDIMENTI_ID = 1;
    public static final int RICETTE_ID = 2;
    public static final int SALSE_ID = 3;
    public static final int TARTUFO_ID = 4;

    private UserModel userModel;
    private ImageView condimentiIma,ricetteIma,salseIma,tartufoIma;
    public static MYDatabaseCarrello carrello;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_product);
        Log.d("On Cretae", "On create metodo");
        userModel = getIntent().getParcelableExtra(USER_DATA_EXTRA);
        carrello = new MYDatabaseCarrello(this);
        carrello.clearAllDbCharr();
        this.setTitle("Ciao " + userModel.getmUsername());
        condimentiIma = (ImageView)findViewById(R.id.image_condimenti);
        ricetteIma = (ImageView)findViewById(R.id.image_ricette);
        salseIma = (ImageView)findViewById(R.id.image_salse);
        tartufoIma = (ImageView)findViewById(R.id.image_tartufo);
    }

    @Override
    protected void onStart() {
        super.onStart();

        condimentiIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listProdForCondimentiIntent = new Intent(ListProductForTypeActivity.LIST_PRODUCT_ACTION);
                listProdForCondimentiIntent.putExtra(ListProductForTypeActivity.CHOISE_EXTRA,CONDIMENTI_ID);
                listProdForCondimentiIntent.putExtra(ListProductForTypeActivity.USER_DATA_EXTRA,userModel);
                startActivity(listProdForCondimentiIntent);
            }
        });
        ricetteIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listRicetteIntent = new Intent(ListProductForTypeActivity.LIST_PRODUCT_ACTION);//todo verificare se può andare o è meglio un'altra activity
                listRicetteIntent.putExtra(ListProductForTypeActivity.CHOISE_EXTRA,RICETTE_ID);
                listRicetteIntent.putExtra(ListProductForTypeActivity.USER_DATA_EXTRA,userModel);
                startActivity(listRicetteIntent);
            }
        });
        salseIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listProdForSalseIntent = new Intent(ListProductForTypeActivity.LIST_PRODUCT_ACTION);
                listProdForSalseIntent.putExtra(ListProductForTypeActivity.CHOISE_EXTRA,SALSE_ID);
                listProdForSalseIntent.putExtra(ListProductForTypeActivity.USER_DATA_EXTRA,userModel);
                startActivity(listProdForSalseIntent);
            }
        });
        tartufoIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listProdTartufoIntent = new Intent(ListProductForTypeActivity.LIST_PRODUCT_ACTION);
                listProdTartufoIntent.putExtra(ListProductForTypeActivity.CHOISE_EXTRA,TARTUFO_ID);
                listProdTartufoIntent.putExtra(ListProductForTypeActivity.USER_DATA_EXTRA,userModel);
                startActivity(listProdTartufoIntent);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(USER_DATA_KEY,userModel);
        //da qui
        Log.d("Salvo","Salvato istance state");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userModel = savedInstanceState.getParcelable(USER_DATA_KEY);
        Log.d("On resome","on Resume Saved Istance");

    }

    public void goToCharr(View v){
        Intent charrIntent = new Intent(CarrelloActivity.CARRELLO_ACTION);
        charrIntent.putExtra(CarrelloActivity.USER_DATA_EXTRA,userModel);
        startActivity(charrIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choise_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
