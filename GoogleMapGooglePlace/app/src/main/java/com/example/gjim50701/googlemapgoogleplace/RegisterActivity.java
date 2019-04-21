package com.example.gjim50701.googlemapgoogleplace;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {


    private EditText UserRegName,UserRegAccount,UserRegPassword;
    private Button UserRegister;

    private String User_Name,User_Account,User_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UserRegName = (EditText) findViewById(R.id.user_reg_name);
        UserRegAccount = (EditText) findViewById(R.id.user_reg_account);
        UserRegPassword = (EditText) findViewById(R.id.user_reg_password);
        UserRegister = (Button) findViewById(R.id.register);

        UserRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                User_Name = UserRegName.getText().toString();
                User_Account = UserRegAccount.getText().toString();
                User_Password = UserRegPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(RegisterActivity.this, "註冊成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("註冊失敗")
                                        .setNegativeButton("重新註冊", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(User_Name, User_Account,User_Password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
