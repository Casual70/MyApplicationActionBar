package com.example.filippocasa.myapplicationactionbar.databaseProduct.dbLocalForSend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.filippocasa.myapplicationactionbar.models.ProductModelDb;
import com.example.filippocasa.myapplicationactionbar.models.UserModel;

public class MYDatabaseCarrello extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "productListCarrello";

    public static final String TABLE = "carrello_list";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME_USER ="name_user";
    public static final String KEY_PRODUCTDB = "productdb";
    public static final String KEY_QNT ="quantità";
    public static final String KEY_PRICE_TOT ="price_tot";

    public MYDatabaseCarrello (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE+"("+KEY_ID+" INTEGER PRIMARY KEY,"+
                                                        KEY_NAME_USER+" TEXT,"+
                                                        KEY_PRODUCTDB+" INTEGER,"+
                                                        KEY_QNT+" INTEGER,"+
                                                        KEY_PRICE_TOT+" INTEGER"+")";

        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion ==1){
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE);
            onCreate(db);
        }
    }
    public void addProductsToDBCharr (ProductModelDb productModelDb, UserModel userModel,int quantità){
        // apro connessione
        SQLiteDatabase db = this.getWritableDatabase();
        //definsco i valori
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_USER,userModel.getmUsername());
        values.put(KEY_PRODUCTDB,productModelDb.getId());
        values.put(KEY_QNT,quantità);
        values.put(KEY_PRICE_TOT,productModelDb.getmPrice()*quantità);
        // riga per id
        long rowId = db.insertOrThrow(TABLE,null,values);
        db.close();
    }
    public void clearAllDbCharr(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE,null,null);
    }
    //query
    // get Tutti i prodotti
    public Cursor getAllProduct(){
        String selectQuery = "SELECT * FROM "+TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    //get Somma Totale Price Table
    public double getTotPrice(){ //todo da sistemare android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 1
        double totPrice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE,new String[]{KEY_PRICE_TOT},null,null,null,null,"_id ASC");
        while (cursor.moveToNext()){
            double oldp = totPrice;
            totPrice = oldp + cursor.getDouble(cursor.getColumnIndex(KEY_PRICE_TOT));
        }
        return totPrice;
    }
}
