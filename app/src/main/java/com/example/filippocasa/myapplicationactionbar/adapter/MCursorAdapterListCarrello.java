package com.example.filippocasa.myapplicationactionbar.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filippocasa.myapplicationactionbar.App;
import com.example.filippocasa.myapplicationactionbar.R;
import com.example.filippocasa.myapplicationactionbar.databaseProduct.dbLocalForSend.MYDatabaseCarrello;
import com.example.filippocasa.myapplicationactionbar.models.ProductModelDb;

public class MCursorAdapterListCarrello extends CursorAdapter {


    public MCursorAdapterListCarrello(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(App.getmContext()).inflate(R.layout.list_offert_lay1,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ProductModelDb productModelDb = App.getDatabaseProduct().getProductById(cursor.getLong(cursor.getColumnIndex(MYDatabaseCarrello.KEY_PRODUCTDB)));
        final TextView productName = (TextView)view.findViewById(R.id.textView_product);
        final TextView price = (TextView)view.findViewById(R.id.textView_price);
        final ImageView image = (ImageView)view.findViewById(R.id.imageView_list);
        productName.setText(productModelDb.getmName());
        price.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(MYDatabaseCarrello.KEY_PRICE_TOT))));
        image.setImageBitmap(BitmapFactory.decodeResource(App.getmContext().getResources(),productModelDb.getmImageId()));
    }
}
