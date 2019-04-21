package com.example.gjim50701.googlemapgoogleplace;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUT = 9001;

    private EditText UserAccount, UserPassword;
    private Button Login, Register;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private String User_Name,User_Account,User_Image,User_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserAccount = (EditText) findViewById(R.id.user_Account);
        UserPassword = (EditText) findViewById(R.id.user_Password);
        Login = (Button) findViewById(R.id.login);
        Register = (Button) findViewById(R.id.register);

        sp = getSharedPreferences("info", MODE_PRIVATE);
        String numberStr1 = sp.getString("useraccount","");
        String passwordStr2 = sp.getString("userpassword","");
        UserAccount.setText(numberStr1);
        UserPassword.setText(passwordStr2);

        if(isServicesOK()){
            init();
        }
    }

    private void init(){

        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String user_account = UserAccount.getText().toString();
                final String user_password = UserPassword.getText().toString();
                String numberStr = UserAccount.getText().toString().trim();
                String passwordStr = UserPassword.getText().toString().trim();

                if (numberStr.isEmpty() || passwordStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "帳密不能為空值", Toast.LENGTH_SHORT).show();
                    return ;
                }else{
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("useraccount", numberStr);
                    editor.putString("userpassword", passwordStr);

                    editor.apply();}

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                Toast.makeText(MainActivity.this, "歡迎登入", Toast.LENGTH_SHORT).show();

                                User_Name = jsonResponse.getString("name");
                                User_Account = jsonResponse.getString("username");
                                User_Password = jsonResponse.getString("password");
                                User_Image = jsonResponse.getString("userimage");
                                
                                AppController gv = (AppController) getApplicationContext();
                                gv.setUserName(User_Name);
                                gv.setUserId(User_Account);
                                gv.setUserPass(User_Password);
                                gv.setUserImage(User_Image);

                                Intent intent = new Intent(MainActivity.this, HomePage.class);
                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("登入失敗")
                                        .setNegativeButton("重登", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(user_account, user_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG,"isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG,"isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUT);
            dialog.show();
        }else{
            Toast.makeText(this, "You can`t make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
