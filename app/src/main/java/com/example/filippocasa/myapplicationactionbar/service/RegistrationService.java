package com.example.filippocasa.myapplicationactionbar.service;

import com.example.filippocasa.myapplicationactionbar.models.UserModel;

public class RegistrationService {

    private static RegistrationService reg;

    public synchronized static RegistrationService get(){
        reg = new RegistrationService();
        return reg;
    }
    public UserModel register (final String Username,
                               final String Password,
                               final String Email){
        return UserModel.create(Username,Password).withEmail(Email);
    }
}
