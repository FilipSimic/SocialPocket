package com.example.socialpocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SyncActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

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
                Toast.makeText(v.getContext(), "TODO: Facebook sync", Toast.LENGTH_LONG).show();
            }
        });
    }
}
