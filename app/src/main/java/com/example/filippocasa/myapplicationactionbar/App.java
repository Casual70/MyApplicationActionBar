package com.example.filippocasa.myapplicationactionbar;

import android.app.Application;
import android.content.Context;

import com.example.filippocasa.myapplicationactionbar.databaseProduct.MYDatabaseProduct;
import com.example.filippocasa.myapplicationactionbar.models.ProductModelDb;

import java.util.Random;

public class App extends Application {

    private static Context mContext;
    private static MYDatabaseProduct databaseProduct;

    static App istanceApp;

    public static App getIstanceApp(){
        if (istanceApp == null){
            istanceApp = new App();
        }
        return istanceApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        getIstanceApp();
        databaseProduct = new MYDatabaseProduct(this);
        databaseProduct.clearAllDbProd();
        initDefaultdb(databaseProduct);

    }
    public static Context getmContext(){
        return mContext;
    }

    public static MYDatabaseProduct getDatabaseProduct() {
        return databaseProduct;
    }

    private void initDefaultdb(MYDatabaseProduct db){ //metodo provvisorio per inizializzare un db a caso

        //descrizioni prodotto
        String descOlioAroma = "";
        String descTarBianco = "";
        String descTarNero = "";


        Random rnd = new Random();
        int lenght = 15;
        double price[] = {1.00,2.00,5.00,10.00,15.00,30.00};
        String categoria[] = {"Condimento","Tartufo Puro","Altro"};//todo ampliare con altre categorie
        String product[] = {"Olio aromatizzato","Tartufo fresco Bianco","Tartufo freasco Nero"};
        int image[] =  {R.drawable.condimento,
                        R.drawable.tar_bianco,
                        R.drawable.tar_nero};
        String descrizioni[] = {};


        for (int i = 0; i<= lenght;i++){
            int indexRandom = rnd.nextInt(product.length);
            String nameProduct = product[indexRandom];
            double priceProduct = price[rnd.nextInt(price.length)];
            String categoryProduct;
            if (indexRandom==1||indexRandom==2){
                categoryProduct = categoria[1];
            }else{
                categoryProduct = categoria[0];
            }
            int imageProduct = image[indexRandom];
            boolean dispProduct = rnd.nextBoolean();

            ProductModelDb productModelDb = new ProductModelDb(nameProduct,priceProduct,categoryProduct,imageProduct,1);
            db.addProductToDb(productModelDb);
        }
    }

}
