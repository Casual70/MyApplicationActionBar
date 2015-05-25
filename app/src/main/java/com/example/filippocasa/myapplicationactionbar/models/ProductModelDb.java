package com.example.filippocasa.myapplicationactionbar.models;

import android.os.Parcel;
import android.os.Parcelable;


public class ProductModelDb implements Parcelable{

    private long id;
    private String mName;
    private double mPrice;
    private int mImageId;
    private String mCategory;
    private int mDisponibile;
    private String mDescrizione;

    public ProductModelDb (String mName,double mPrice,String mCategory,int mImageId,int mDisponibile){
        super();
        this.mName = mName;
        this.mPrice = mPrice;
        this.mCategory = mCategory;
        this.mImageId = mImageId;
        this.mDisponibile = mDisponibile;
    }
    public ProductModelDb(Parcel source){
        id = source.readLong();
        mName = source.readString();
        mPrice = source.readDouble();
        mImageId = source.readInt();
        mCategory = source.readString();
        mDisponibile = source.readInt();
        mDescrizione = source.readString();
    }


    public static final Creator<ProductModelDb>CREATOR = new Creator<ProductModelDb>(){

        @Override
        public ProductModelDb createFromParcel(Parcel source) {
            return new ProductModelDb(source);
        }

        @Override
        public ProductModelDb[] newArray(int size) {
            return new ProductModelDb[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getmName() {
        return mName;
    }

    public double getmPrice() {
        return mPrice;
    }

    public int getmImageId() {
        return mImageId;
    }

    public int getmDisponibile() {
        return mDisponibile;
    }

    public String getmDescrizione() {
        return mDescrizione;
    }

    public String getmCategory() {
        return mCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(mName);
        dest.writeDouble(mPrice);
        dest.writeInt(mImageId);
        dest.writeString(mCategory);
        dest.writeInt(mDisponibile);
        dest.writeString(mDescrizione);
    }
}
