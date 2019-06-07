package com.example.socialpocket;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyApplication extends Application
{
    private static final String TAG = "MyApplication";
    public static final String CHANNEL_ID = "MyChannel";

    private String idApp;
    private ArrayList<Post> posts;
    private ArrayList<User> users;

    public void onCreate()
    {
        super.onCreate();

        EventBus.getDefault().register(this);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        idApp = pref.getString("idApp", "");
        posts = new ArrayList<>();
        users = new ArrayList<>();

        if (idApp.equals("")) {
            idApp = UUID.randomUUID().toString().replace("-", "");
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("idApp", idApp);
            editor.apply();
        }

        posts.add(new Post(idApp, "test", "asd", "dd"));
        posts.add(new Post(idApp, "test2", "asd", "dd"));
        posts.add(new Post(idApp, "test3", "asd", "dd"));

        users.add(new User("Filip", "admin", "filip@gmail.com"));
        users.add(new User("Niko","lilass", "niko@gmail.com"));
        users.add(new User("Test", "asd","dd@aa"));

        createChannel();
    }

    @TargetApi(23)
    private void createChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Primer Storitve",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MyEvent event) {
        Toast.makeText(this, event.getPost().toString(), Toast.LENGTH_SHORT).show();
    };

    public void fromJson(String jsonString)
    {
        Type listType = new TypeToken<ArrayList<Post>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<Post> temp = gson.fromJson(jsonString, listType);
        this.setPosts(temp);
    }

    public String toJson()
    {
        Type listType = new TypeToken<ArrayList<Post>>() {}.getType();
        Gson gson = new Gson();
        return gson.toJson(posts, listType);
    }

    private File getFile(String fileName) {
        File filesDir = getFilesDir();
        return new File(filesDir, fileName);
    }

    public void saveToFile(String fileName, String jsonString) {
        try
        {
            FileUtils.writeStringToFile(getFile(fileName), jsonString);
        } catch (IOException e)
        {
            Log.d(TAG, "Error while saving file: " + e.getMessage());
        }
    }

    public String readFromFile(String fileName)
    {
        if (getFile(fileName).exists())
        {
            try
            {
                return FileUtils.readFileToString(getFile(fileName));
            }
            catch (IOException e) {
                Log.d(TAG, "Error while reading file: " + e.getMessage());
                return "";
            }
        }
        return "";
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public String getIdApp() { return idApp; }

    public ArrayList<User> getUsers() { return users; }

    public void changePassword(int index, String newPass) {
        users.get(index).setPassword(newPass);
    }

    public void register(String user, String pass, String email) {
        users.add(new User(user, pass, email));
    }

    public void saveFace(String base64) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("loginFace", base64);
        editor.apply();
    }

    public String getFace() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        return pref.getString("loginFace","");
    }
}
