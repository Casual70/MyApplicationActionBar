package com.example.filippocasa.myapplicationactionbar.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.filippocasa.myapplicationactionbar.models.UserModel;



public class AccessActivity extends AppCompatActivity {

    private static final int LOGIN_REQUEST_ID = 1;
    private static final int REGISTRATION_REQUEST_ID = 2;

    private ListView offertList;


    private MCursorAdapterListOffert cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        offertList = (ListView)findViewById(R.id.listView);
        App.getIstanceApp();
        MYDatabaseProduct db = App.getDatabaseProduct();
        Cursor cursor = db.getAllProductCursor();
        cursorAdapter = new MCursorAdapterListOffert(this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        offertList.setAdapter(cursorAdapter);
        offertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor productModelDbCursor= (Cursor)parent.getItemAtPosition(position);
                final long idProduct = productModelDbCursor.getLong(productModelDbCursor.getColumnIndexOrThrow(MYDatabaseProduct.KEY_ID));
                final Intent detailproductIntent = new Intent(getApplicationContext(),ProductDetailActivity.class);
                detailproductIntent.putExtra(ProductDetailActivity.PRODUCT_MODEL_ID_EXTRA,idProduct);
                startActivity(detailproductIntent);
            }

        });
    }

    public void doLogin(View view){
        Intent loginIntent = new Intent(AccessLogActivity.ACCESS_LOG_ACTION);
        startActivityForResult(loginIntent,LOGIN_REQUEST_ID);
    }
    public void doRegistration(View view){
        Intent registrationIntent = new Intent(RegistrationActivity.REGISTARTION_ACTION);
        startActivityForResult(registrationIntent,REGISTRATION_REQUEST_ID);
    }
    public void doAnonimus(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REGISTRATION_REQUEST_ID){
            switch (resultCode){
                case RESULT_OK:
                    final UserModel userModel = data.getParcelableExtra(RegistrationActivity.USER_DATA_EXTRA);
                    final Intent choiseIntent = new Intent(ChoiseProductActivity.CHOISE_ACTION);
                    choiseIntent.putExtra(ChoiseProductActivity.USER_DATA_EXTRA,userModel);
                    startActivity(choiseIntent);
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }
        if (requestCode == LOGIN_REQUEST_ID){
            switch (resultCode){
                case RESULT_OK:
                    final UserModel userModel = data.getParcelableExtra(AccessLogActivity.USER_DATA_EXTRA);
                    final Intent choiseIntent = new Intent(ChoiseProductActivity.CHOISE_ACTION);
                    choiseIntent.putExtra(ChoiseProductActivity.USER_DATA_EXTRA,userModel);
                    startActivity(choiseIntent);
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
