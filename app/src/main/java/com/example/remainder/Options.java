package com.example.remainder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Options extends AppCompatActivity {
    String describe[], categories[] ;
    int images[] = {R.drawable.buy_logo, R.drawable.friends_logo,R.drawable.music1,R.drawable.nature, R.drawable.other_logo, R.drawable.relax, R.drawable.run, R.drawable.school_logo, R.drawable.work_logo};
    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        describe = getResources().getStringArray(R.array.describe) ;
        categories = getResources().getStringArray(R.array.categories) ;
        recyclerView = findViewById(R.id.recyclerView) ;

        MyAdapter adapter = new MyAdapter(this, describe, categories, images) ;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}