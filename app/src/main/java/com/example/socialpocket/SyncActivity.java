package com.example.socialpocket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class SyncActivity extends AppCompatActivity {


    CallbackManager callbackManager;
    MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        FacebookSdk.fullyInitialize();

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Button facebook = findViewById(R.id.btn_facebook);
                        facebook.setBackground(getDrawable(R.drawable.button_success));
                        facebook.setTextColor(getColor(R.color.btn_success));
                        facebook.setText(getString(R.string.facebookSuccess));
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SyncActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(SyncActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        setContentView(R.layout.activity_sync);

        app = (MyApplication)getApplication();

        Button instagram = findViewById(R.id.btn_instagram);
        instagram.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

        Button face = findViewById(R.id.btn_addFace);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SyncActivity.this, FaceRegisterActivity.class));
            }
        });

        if(app.getFace() != "") {
            face.setBackground(getDrawable(R.drawable.button_success));
            face.setTextColor(getColor(R.color.btn_success));
            face.setText(getString(R.string.faceAdded));
        }

        Button cont = findViewById(R.id.btn_continue);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SyncActivity.this, MainActivity.class));
            }
        });

        Button twitter = findViewById(R.id.btn_twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                WebDriver driver = new ChromeDriver(options);
                driver.get("http://www.google.com");
                WebElement element = driver.findElement(By.name("q"));
                element.sendKeys("Cheese!");
                element.submit();
                System.out.println("Page title is: " + driver.getTitle());

                (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return d.getTitle().toLowerCase().startsWith("cheese!");
                    }
                });

                System.out.println("Page title is: " + driver.getTitle());
                driver.quit();
            }
        });

        Button facebook = findViewById(R.id.btn_facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SyncActivity.this, Arrays.asList("public_profile", "user_friends"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
