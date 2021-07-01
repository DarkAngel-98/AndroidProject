package com.example.remainder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ShowReminders extends AppCompatActivity {

    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_remainders);
        listView = findViewById(R.id.listView) ;
        Database database = new Database(ShowReminders.this) ;
        database.checkIfTodaysDateExists();
        List<String> rowList = database.returnData() ;

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(ShowReminders.this, android.R.layout.simple_list_item_1, rowList) ;
        listView.setAdapter(arrayAdapter);
        //Toast.makeText(this, rowList.toString(), Toast.LENGTH_SHORT).show();
    }
}