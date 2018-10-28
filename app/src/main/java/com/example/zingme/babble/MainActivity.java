package com.example.zingme.babble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zingme.babble.adapters.FeaturesAdapter;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    FeaturesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.feature_listView);
        adapter=new FeaturesAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
