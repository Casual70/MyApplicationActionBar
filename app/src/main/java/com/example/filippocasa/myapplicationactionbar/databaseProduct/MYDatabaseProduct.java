package com.example.filippocasa.myapplicationactionbar.databaseProduct;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.filippocasa.myapplicationactionbar.models.ProductModelDb;

import java.util.ArrayList;
import java.util.List;

public class MYDatabaseProduct extends SQLiteOpenHelper{

    //database version
    private static final int DATABASE_VERSION = 2;
    //database name
    private static final String DATABASE_NAME = "productListDatabase";
    // table name
    public static final String TABLE = "table_list";
    // table name colonne
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME ="name";
    public static final String KEY_PRICE ="price";
    public static final String KEY_CATEGORY ="categoria";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DISPONIBILITA ="disponibilità";

    public MYDatabaseProduct(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+ TABLE + "("+KEY_ID + " INTEGER PRIMARY KEY,"+
                                                           KEY_NAME+" TEXT,"+
                                                           KEY_PRICE+" INTEGER,"+
                                                           KEY_CATEGORY+" TEXT,"+
                                                           KEY_IMAGE+" INTEGER,"+
                                                           KEY_DISPONIBILITA+" INTEGER"+")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion ==1){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }
    }

    public void addProductToDb(ProductModelDb productModelDb){
        // aprire connessione con db
        SQLiteDatabase db = this.getWritableDatabase();
        // definire valori nella tabella
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,productModelDb.getmName());
        values.put(KEY_PRICE,productModelDb.getmPrice());
        values.put(KEY_CATEGORY,productModelDb.getmCategory());
        values.put(KEY_IMAGE,productModelDb.getmImageId());
        values.put(KEY_DISPONIBILITA,productModelDb.getmDisponibile());
        // inserte riga per id
        long rowId = db.insertOrThrow(TABLE,null,values);
        //chiudere connessione
        db.close();
    }
    public void clearAllDbProd(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE,null,null);
    }
    //query
    // quesry lista da categoria
    public List<ProductModelDb>getProductForCategory(String category){
        List<ProductModelDb>listProduct = new ArrayList<ProductModelDb>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE,new String[]{KEY_ID,KEY_NAME,KEY_PRICE,KEY_CATEGORY,KEY_IMAGE,KEY_DISPONIBILITA},
                                 KEY_CATEGORY+" = ?",new String[]{String.valueOf(category)},null,null,"_id ASC");
        if (cursor.moveToFirst()){
            do{
                ProductModelDb productModelDb = new ProductModelDb(cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                                                    cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)),
                                                    cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)),
                                                    cursor.getInt(cursor.getColumnIndex(KEY_IMAGE)),
                                                    cursor.getInt(cursor.getColumnIndex(KEY_DISPONIBILITA)));
                listProduct.add(productModelDb);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listProduct;
    }
    public Cursor getProductForCategoryCursor(String category){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE,new String[]{KEY_ID,KEY_NAME,KEY_PRICE,KEY_CATEGORY,KEY_IMAGE,KEY_DISPONIBILITA},
                                 KEY_CATEGORY+" = ?",new String[]{String.valueOf(category)},null,null,"_id ASC");
        if (cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    //query per tutti gli oggetti
    public List<ProductModelDb>getAllProduct(){
        List<ProductModelDb>listProduct = new ArrayList<ProductModelDb>();

        String selectQuesry = "SELECT  * FROM "+TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuesry,null);
        // ciclare nel cursore e prendere le righe
        if (cursor.moveToFirst()){
            do{
                ProductModelDb productModelDb = new ProductModelDb(cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                                                                   cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)),
                                                                   cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)),
                                                                   cursor.getInt(cursor.getColumnIndex(KEY_IMAGE)),
                                                                   cursor.getInt(cursor.getColumnIndex(KEY_DISPONIBILITA)));
                listProduct.add(productModelDb);
            }while(cursor.moveToNext());
        }
        // close cursor
        cursor.close();
        return listProduct;
    }
    public Cursor getAllProductCursor(){
        String selectQuesry = "SELECT  * FROM "+TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuesry,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    // quesry numero oggeti
    public int getProductCount(){
        String selectQuesry = "SELECT  * FROM "+TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuesry,null);
        cursor.close();
        return cursor.getCount();
    }
    //query oggetto da id
    public ProductModelDb getProductById(final long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE,new String[]{KEY_ID,KEY_NAME,KEY_PRICE,KEY_CATEGORY,KEY_IMAGE,KEY_DISPONIBILITA},
                                 KEY_ID+" = ?",new String[]{String.valueOf(id)},null,null,"_id ASC");
        if (cursor!=null){
            cursor.moveToNext();
        }
        ProductModelDb productModelDb = new ProductModelDb(cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                                                           cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)),
                                                           cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)),
                                                           cursor.getInt(cursor.getColumnIndex(KEY_IMAGE)),
                                                           cursor.getInt(cursor.getColumnIndex(KEY_DISPONIBILITA)));
        productModelDb.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
        cursor.close();
        return productModelDb;
    }

    // update productModel
    public int updateProductModel(ProductModelDb productModelDb){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,productModelDb.getmName());
        values.put(KEY_PRICE,productModelDb.getmPrice());
        values.put(KEY_CATEGORY,productModelDb.getmCategory());
        values.put(KEY_IMAGE,productModelDb.getmImageId());
        values.put(KEY_DISPONIBILITA,productModelDb.getmDisponibile());
        int result = db.update(TABLE,values,KEY_ID+" = ?",new String[]{String.valueOf(productModelDb.getId())});
        db.close();
        return result;
    }
    // delete ProductModel
    public void deleteProductModel(ProductModelDb productModelDb){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE,KEY_ID+" = ?",new String[]{String.valueOf(productModelDb.getId())});
        db.close();
    }

}
