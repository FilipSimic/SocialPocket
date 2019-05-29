package com.example.socialpocket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (MyApplication) getApplication();
        loadRecyclerView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MyEvent event) {
        Toast.makeText(this, event.getPost().toString(), Toast.LENGTH_SHORT).show();
    };


    private void loadRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.rvMain);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, app.getPosts());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void readFromFile(View v)
    {
        TextView temp = (TextView)findViewById(R.id.textView);
        temp.setText("");
        temp.setText(app.readFromFile("postInfo.txt"));

        Intent i = new Intent(this, MyService.class);
        stopService(i);

        Intent intent = new Intent(this, MyBootReceiver.class);
        intent.setAction("android.intent.action.ACTION_BOOT_COMPLETED");
        sendBroadcast(intent);
    }

    public void writeToFile(View v)
    {
        EventBus.getDefault().post(new MyEvent(new Post(app.getIdApp(), "MyEvent", "Filip", "Primer")));
        app.saveToFile("postInfo.txt", app.toJson());

        Intent i = new Intent(this, MyService.class);
        i.putExtra("serviceIntent", "Primer storitve");

        startService(i);
    }
}
