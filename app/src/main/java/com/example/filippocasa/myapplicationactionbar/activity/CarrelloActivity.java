package com.example.filippocasa.myapplicationactionbar.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.filippocasa.myapplicationactionbar.R;
import com.example.filippocasa.myapplicationactionbar.adapter.MCursorAdapterListCarrello;
import com.example.filippocasa.myapplicationactionbar.databaseProduct.dbLocalForSend.MYDatabaseCarrello;
import com.example.filippocasa.myapplicationactionbar.models.UserModel;

public class CarrelloActivity extends AppCompatActivity {

    public static final String CARRELLO_ACTION = "com.example.filippocasa.myapplicationactionbar.action.CARRELLO_ACTION";
    public static final String USER_DATA_EXTRA = "com.example.filippocasa.myapplicationactionbar.extra.USER_DATA_EXTRA";

    private UserModel userModel;
    private MCursorAdapterListCarrello cursorAdapter;
    private MYDatabaseCarrello dbCarrello;

    private ListView carrelloList;
    private TextView mOrdineLabel,mOrdineData;
    private TextView mPriceLabel,mPriceData;
    private TextView mNoteLabel,mNoteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);
        userModel = getIntent().getParcelableExtra(USER_DATA_EXTRA);
        carrelloList = (ListView)findViewById(R.id.listView2);
        mOrdineData = (TextView)findViewById(R.id.textView11);
        mPriceData = (TextView)findViewById(R.id.textView12);
        mNoteData = (TextView)findViewById(R.id.textView13);
        dbCarrello = ChoiseProductActivity.carrello;
        Cursor cursor = dbCarrello.getAllProduct();
        cursorAdapter = new MCursorAdapterListCarrello(this,cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ARRIVOA","edfased");
        carrelloList.setAdapter(cursorAdapter);
        mOrdineData.setText(userModel.getmUsername());
        mPriceData.setText(String.valueOf(dbCarrello.getTotPrice()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carrello, menu);
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
