package com.example.filippocasa.myapplicationactionbar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.filippocasa.myapplicationactionbar.R;
import com.example.filippocasa.myapplicationactionbar.models.UserModel;
import com.example.filippocasa.myapplicationactionbar.service.RegistrationService;


public class RegistrationActivity extends AppCompatActivity {

    public static final String REGISTARTION_ACTION = "com.example.filippocasa.myapplicationactionbar.action.REGISTARTION_ACTION";
    public static final String USER_DATA_EXTRA = "com.example.filippocasa.myapplicationactionbar.extra.USER_DATA_EXTRA";

    private EditText etUsername,etPassword,etEmail;
    private TextView errorCred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etUsername = (EditText)findViewById(R.id.Username_text);
        etPassword = (EditText)findViewById(R.id.Password_text);
        etEmail = (EditText)findViewById(R.id.Email_text);
        errorCred = (TextView)findViewById(R.id.error_label);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
    public void doSendReg(View v){
        errorCred.setVisibility(View.INVISIBLE);
        Editable user = etUsername.getText();
        Editable pass = etPassword.getText();
        Editable emailEdit = etEmail.getText();
        if (TextUtils.isEmpty(user)||TextUtils.isEmpty(pass)){
            errorCred.setVisibility(View.VISIBLE);
            return;
        }
        final String username = user.toString();
        final String password = pass.toString();
        final String email = emailEdit.toString();
        final UserModel userModel = RegistrationService.get().register(username,password,email);
        if (userModel != null) {
            popUpConfirm(userModel);
        } else {
            errorCred.setVisibility(View.VISIBLE);
        }


    }
    private void popUpConfirm(final UserModel um){
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View confirmPopup = layoutInflater.inflate(R.layout.popup_confirm_reg,null);
        final PopupWindow popupWindow = new PopupWindow(confirmPopup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final Button confirmBut = (Button)confirmPopup.findViewById(R.id.button_confirm);
        final Button modifyBut = (Button)confirmPopup.findViewById(R.id.button_modify);
        final TextView username = (TextView)confirmPopup.findViewById(R.id.textView_username);
        final TextView password = (TextView)confirmPopup.findViewById(R.id.textView_password);
        final TextView email = (TextView)confirmPopup.findViewById(R.id.textView_email);
        username.setText(um.getmUsername());
        password.setText(um.getmPassword());
        email.setText(um.getmEmail());
        confirmBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent();
                regIntent.putExtra(USER_DATA_EXTRA, um);
                setResult(RESULT_OK, regIntent);
                finish();
                popupWindow.dismiss(); //todo Usare start on activity result anche nel carrello per passare i dati dello stesso
            }
        });
        modifyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(etPassword,50,30,BIND_IMPORTANT);
    }
}
