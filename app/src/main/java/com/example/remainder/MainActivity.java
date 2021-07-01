package com.example.remainder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    RelativeLayout openRemainder, checkRemainders ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openRemainder = findViewById(R.id.goFurther);
        checkRemainders = findViewById(R.id.checkRemainders) ;
        openRemainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Options.class) ;
                startActivity(intent);
            }
        });
        checkRemainders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowReminders.class) ;
                startActivity(intent);
            }
        });

    }

}