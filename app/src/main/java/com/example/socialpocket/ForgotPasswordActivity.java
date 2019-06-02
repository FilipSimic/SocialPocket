package com.example.socialpocket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final EditText email = findViewById(R.id.email);
        Button submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = email.getText().toString();
                if(!textEmail.isEmpty()) {
                    try {
                        final String url = "https://"+ IP + "/api/Account/Forgot";

                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("Email", textEmail)
                                .build();

                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .header("content-type", "application/json")
                                .build();

                        Response response = client.newCall(request).execute();
                        String res = response.body().string();

                        if(res == "Failed") {
                            Toast.makeText(v.getContext(), "Email doesn't exist", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(v.getContext(), "Password successfuly changed. New password is: " + res, Toast.LENGTH_LONG).show();
                        }
                        email.setText("");
                    } catch(Exception ex) {
                        Toast.makeText(v.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
