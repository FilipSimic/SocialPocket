package com.example.socialpocket;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.socialpocket.MyApplication.IP;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = findViewById(R.id.username);
                EditText pass = findViewById(R.id.password);

                String username = user.getText().toString();
                String password = pass.getText().toString();

                if(!username.isEmpty() && !password.isEmpty()) {
                    try {
                        final String url = "https://"+ IP + "/api/Account/Login";

                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("Username", username)
                                .add("Password", password)
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

                        if(res == "Successful") {
                            startActivity(new Intent(getBaseContext(), MainActivity.class));
                        } else {
                            Toast.makeText(v.getContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                            user.setText("");
                            pass.setText("");
                        }
                    } catch(Exception ex) {
                        Toast.makeText(v.getContext(), ex.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(v.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    user.setText("");
                    pass.setText("");
                }
            }
        });

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

        TextView forgotPass = findViewById(R.id.forgot_password);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ForgotPasswordActivity.class));
            }
        });

        TextView registration = findViewById(R.id.register);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });

        TextView faceLogin = findViewById(R.id.opencv_login);
        faceLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), FaceLoginActivity.class));
            }
        });
    }
}
