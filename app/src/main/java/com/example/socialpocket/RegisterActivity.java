package com.example.socialpocket;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.socialpocket.MyApplication.IP;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) - 40) {
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) - 40) {
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password.setTypeface(Typeface.DEFAULT);
                        return true;
                    }
                }
                return false;
            }
        });

        final EditText email = findViewById(R.id.email);
        final EditText retyped = findViewById(R.id.retype_password);
        retyped.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (retyped.getRight() - retyped.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) - 40) {
                        retyped.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (retyped.getRight() - retyped.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) - 40) {
                        retyped.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        retyped.setTypeface(Typeface.DEFAULT);
                        return true;
                    }
                }
                return false;
            }
        });

        Button register = findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = username.getText().toString();
                String p = password.getText().toString();
                String e = email.getText().toString();
                String r = retyped.getText().toString();

                if(!u.isEmpty() && !p.isEmpty() && !e.isEmpty() && !r.isEmpty()) {
                    if(e == r) {
                        try {
                            final String url = "https://" + IP + "/api/Account/Register";

                            OkHttpClient client = new OkHttpClient();
                            RequestBody body = new FormBody.Builder()
                                    .add("Username", u)
                                    .add("Password", p)
                                    .add("Email", e)
                                    .build();

                            Request request = new Request.Builder()
                                    .url(url)
                                    .post(body)
                                    .header("content-type", "application/json")
                                    .build();

                            Response response = client.newCall(request).execute();
                            String res = response.body().string();
                            String test = response.toString();
                            Toast.makeText(v.getContext(), res, Toast.LENGTH_LONG).show();
                            Toast.makeText(v.getContext(), test, Toast.LENGTH_LONG).show();

                            if (res == "Successful") {
                                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(v.getContext(), "Email or username alredy exist!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(v.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(v.getContext(), "Password and retyped password dont match", Toast.LENGTH_LONG).show();
                    }
                } else {
                    username.setText("");
                    password.setText("");
                    retyped.setText("");
                    email.setText("");
                    Toast.makeText(v.getContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
