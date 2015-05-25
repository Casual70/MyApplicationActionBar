package com.example.filippocasa.myapplicationactionbar.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class UserModel implements Parcelable {

    private static final byte PRESENT =1;
    private static final byte NOT_PRESENT =0;

    private String mUsername;
    private String mPassword;
    private String mEmail;

    public static final Creator<UserModel>CREATOR = new Creator<UserModel>(){

        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
    public UserModel (Parcel source){
        this.mUsername =source.readString();
        this.mPassword =source.readString();
        if (source.readByte() == PRESENT){
            this.mEmail = source.readString();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUsername);
        dest.writeString(mPassword);
        if (!TextUtils.isEmpty(mEmail)){
            dest.writeByte(PRESENT);
            dest.writeString(mEmail);
        }else{dest.writeByte(NOT_PRESENT);}
    }
    public static UserModel create(final String username,final String password){
        return new UserModel(username,password);
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    private UserModel (final String mUsername, final String mPassword){
        this.mUsername = mUsername;
        this.mPassword = mPassword;
    }
    public UserModel withEmail(final String mEmail){
        this.mEmail = mEmail;
        return this;
    }
}
