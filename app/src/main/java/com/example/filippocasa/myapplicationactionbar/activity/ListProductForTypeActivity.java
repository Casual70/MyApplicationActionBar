package com.example.filippocasa.myapplicationactionbar.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.filippocasa.myapplicationactionbar.App;
import com.example.filippocasa.myapplicationactionbar.R;
import com.example.filippocasa.myapplicationactionbar.adapter.MCursorAdapterListOffert;
import com.example.filippocasa.myapplicationactionbar.databaseProduct.MYDatabaseProduct;
import com.example.filippocasa.myapplicationactionbar.models.ProductModelDb;
import com.example.filippocasa.myapplicationactionbar.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ListProductForTypeActivity extends Activity {

    public static final String LIST_PRODUCT_ACTION = "com.example.filippocasa.myapplicationactionbar.action.LIST_PRODUCT_ACTION";
    public static final String CHOISE_EXTRA = "com.example.filippocasa.myapplicationactionbar.extra.CHOISE_EXTRA";
    public static final String USER_DATA_EXTRA = "com.example.filippocasa.myapplicationactionbar.extra.USER_DATA_EXTRA";


    private MYDatabaseProduct db;
    private int choiseId;
    private ListView listView;
    private UserModel userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_for_type);
        choiseId = getIntent().getIntExtra(CHOISE_EXTRA,-1);
        userData = getIntent().getParcelableExtra(USER_DATA_EXTRA);
        listView = (ListView)findViewById(R.id.list);
        db = App.getDatabaseProduct();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (choiseId == -1){
            finish();
        }
        switch (choiseId){
            case ChoiseProductActivity.CONDIMENTI_ID:
                Cursor cursor = db.getProductForCategoryCursor("Condimento");
                MCursorAdapterListOffert cursorAdapter = new MCursorAdapterListOffert(this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                listView.setAdapter(cursorAdapter);
                break;
            case ChoiseProductActivity.RICETTE_ID:
                //todo Implementare un activity riservata per le ricette
                break;
            case ChoiseProductActivity.SALSE_ID:
                cursor = db.getProductForCategoryCursor("Altro");
                cursorAdapter = new MCursorAdapterListOffert(this, cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                listView.setAdapter(cursorAdapter);
                break;
            case ChoiseProductActivity.TARTUFO_ID:
                cursor = db.getProductForCategoryCursor("Tartufo Puro");
                cursorAdapter = new MCursorAdapterListOffert(this, cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                listView.setAdapter(cursorAdapter);
                break;

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor productCursor = (Cursor)parent.getItemAtPosition(position);
                final long idProduct = productCursor.getLong(productCursor.getColumnIndex(MYDatabaseProduct.KEY_ID));
                final Intent detailProductIntent = new Intent(getApplicationContext(),ProductDetailActivity.class);
                detailProductIntent.putExtra(ProductDetailActivity.PRODUCT_MODEL_ID_EXTRA,idProduct);
                detailProductIntent.putExtra(ProductDetailActivity.USER_LOG_EXTRA,true);
                detailProductIntent.putExtra(ProductDetailActivity.USER_DATA_EXTRA,userData);
                startActivity(detailProductIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_product_for_type, menu);
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
