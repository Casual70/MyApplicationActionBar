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
import com.example.filippocasa.myapplicationactionbar.databaseProduct.MYDatabaseProduct;

public class MCursorAdapterListOffert extends CursorAdapter {

    public MCursorAdapterListOffert(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(App.getmContext()).inflate(R.layout.list_offert_lay1,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView productName = (TextView)view.findViewById(R.id.textView_product);
        final TextView price = (TextView)view.findViewById(R.id.textView_price);
        final ImageView image = (ImageView)view.findViewById(R.id.imageView_list);
        productName.setText(cursor.getString(cursor.getColumnIndex(MYDatabaseProduct.KEY_NAME)));
        double priceprov = cursor.getDouble(cursor.getColumnIndex(MYDatabaseProduct.KEY_PRICE));
        price.setText(String.valueOf(priceprov));
        image.setImageBitmap(BitmapFactory.decodeResource(App.getmContext().getResources(),cursor.getInt(cursor.getColumnIndex(MYDatabaseProduct.KEY_IMAGE))));
        //disponibilità todo
    }
}
