package com.example.filippocasa.myapplicationactionbar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.filippocasa.myapplicationactionbar.R;
import com.example.filippocasa.myapplicationactionbar.models.UserModel;
import com.example.filippocasa.myapplicationactionbar.service.LoginService;

public class AccessLogActivity extends AppCompatActivity {

    public static final String ACCESS_LOG_ACTION = "com.example.filippocasa.myapplicationactionbar.action.ACCESS_LOG_ACTION";
    public static final String USER_DATA_EXTRA = "com.example.filippocasa.myapplicationactionbar.extra.USER_DATA_EXTRA";

    private EditText usernameEdit;
    private EditText passwordRdit;
    private TextView error;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_log);
        usernameEdit = (EditText)findViewById(R.id.login_username);
        passwordRdit = (EditText)findViewById(R.id.login_password);
        error = (TextView)findViewById(R.id.error_label_login);
    }
    public void doLogin(View view){
        error.setVisibility(View.INVISIBLE);
        final Editable userEdit = usernameEdit.getText();
        final Editable passEdit = passwordRdit.getText();
        if (TextUtils.isEmpty(userEdit)||TextUtils.isEmpty(passEdit)){
            error.setVisibility(View.VISIBLE);
            return;
        }
        final String username = userEdit.toString();
        final String password = passEdit.toString();
        final UserModel userModel = LoginService.get().login(username,password);
        if (userModel != null){
            Intent resultIntent = new Intent();
            resultIntent.putExtra(USER_DATA_EXTRA,userModel);
            setResult(RESULT_OK,resultIntent);
            finish();
        }else{
            error.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_access_log, menu);
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
}
