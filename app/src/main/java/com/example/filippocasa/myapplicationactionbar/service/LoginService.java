package com.example.filippocasa.myapplicationactionbar.service;

import com.example.filippocasa.myapplicationactionbar.models.UserModel;

public class LoginService {

    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";

    private static LoginService loginService;

    public UserModel login(final String username,final String password){
        UserModel userModel = null;
        if(USERNAME.equalsIgnoreCase(username)&&PASSWORD.equalsIgnoreCase(password)){
          userModel = UserModel.create(username, password);
        }
        return userModel;
    }
    public synchronized static LoginService get(){
        if (loginService == null){
            loginService = new LoginService();
        }
        return loginService;
    }
}
