package com.example.filippocasa.myapplicationactionbar.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.filippocasa.myapplicationactionbar.App;
import com.example.filippocasa.myapplicationactionbar.R;
import com.example.filippocasa.myapplicationactionbar.models.ProductModelDb;
import com.example.filippocasa.myapplicationactionbar.models.UserModel;


public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_MODEL_ID_EXTRA = "com.example.filippocasa.myapplicationactionbar.extra.PRODUCT_MODEL_ID_EXTRA";
    public static final String USER_LOG_EXTRA = "com.example.filippocasa.myapplicationactionbar.extra.USER_LOG_EXTRA";
    public static final String USER_DATA_EXTRA = "com.example.filippocasa.myapplicationactionbar.extra.USER_DATA_EXTRA";


    private ProductModelDb productModelDb;
    private UserModel userData;

    private TextView name;
    private TextView price;
    private ImageView imageView;
    private TextView descrizione;
    private TextView unità;
    private NumberPicker qnt;
    private TextView totPrice;
    private long idProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        idProduct = getIntent().getLongExtra(PRODUCT_MODEL_ID_EXTRA, 0);
        if (idProduct==0){
            finish();
        }
        boolean isLogged = getIntent().getBooleanExtra(USER_LOG_EXTRA, false);
        if (isLogged){
            userData = getIntent().getParcelableExtra(USER_DATA_EXTRA);
        }else{
            Button addToChar = (Button) findViewById(R.id.button);
            addToChar.setVisibility(View.INVISIBLE);
        }
        qnt = (NumberPicker)findViewById(R.id.numberPicker_qnt);
        name = (TextView)findViewById(R.id.product_name_detail);
        price = (TextView)findViewById(R.id.product_price_detail);
        imageView = (ImageView)findViewById(R.id.imageView_detail);
        descrizione = (TextView)findViewById(R.id.product_description_detail);
        unità = (TextView)findViewById(R.id.text_unità);
        totPrice = (TextView)findViewById(R.id.text_tot_euro);
    }

    @Override
    protected void onStart() {
        super.onStart();
        productModelDb = App.getDatabaseProduct().getProductById(idProduct);
        forCategoryAdjustPriceAndMore();
        name.setText(productModelDb.getmName());
        imageView.setImageBitmap(BitmapFactory.decodeResource(App.getmContext().getResources(), productModelDb.getmImageId()));
        descrizione.setText(productModelDb.getmDescrizione());
        totPrice.setText(this.getResources().getString(R.string.tot_price));
        qnt.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                double prezzoUn = productModelDb.getmPrice();
                int quantità = picker.getValue();
                double prezzotot = prezzoUn*quantità;
                totPrice.setText(String.valueOf(prezzotot));
            }
        });

    }
    private void forCategoryAdjustPriceAndMore(){ //todo Implementare il metodo per aggiustare unità di misura in vase al prodotto
        String Um;
        switch (productModelDb.getmCategory()){
            case "Tartufo Puro":
                Um = this.getResources().getString(R.string.unità_price_qnt_tartufo);
                price.setText(productModelDb.getmPrice() + Um);
                unità.setText(this.getResources().getString(R.string.unità_tartufo));
                qnt.setMaxValue(250);
                qnt.setMinValue(20);
                qnt.setValue(50);
                qnt.setWrapSelectorWheel(true);
                break;
            case "Condimento":
                Um = this.getResources().getString(R.string.unità_price_qnt_pezzo);
                price.setText(productModelDb.getmPrice() + Um);
                unità.setText(this.getResources().getString(R.string.unità_pezzo));
                qnt.setMaxValue(25);
                qnt.setMinValue(1);
                qnt.setValue(1);
                qnt.setWrapSelectorWheel(false);
                break;
            default:
                Um = "ND";
                price.setText(productModelDb.getmPrice() + Um);
                unità.setText(this.getResources().getString(R.string.unità_pezzo));
        }
    }
    public void addToCharrello(View v){
        ChoiseProductActivity.carrello.addProductsToDBCharr(productModelDb,userData,qnt.getValue());
        Log.d("Prodotto aggiunto","addToCharrello execute");
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
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
