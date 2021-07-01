package com.example.remainder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class DateTime extends AppCompatActivity {

    String name;
    String remainder;
    String datum;
    String vreme ;
    TextView textName ;
    TextView enterDate, enterTime ;
    EditText takeRemainder ;
    RelativeLayout relativeDate, relativeTime, remindButton ;
    DatePickerDialog.OnDateSetListener listener ;
    Database database ;
    int hour, min ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        getName();
        takeRemainder = findViewById(R.id.remainder) ;
        remainder = takeRemainder.getText().toString() ;

        relativeDate = findViewById(R.id.enterDate) ;
        enterDate = findViewById(R.id.date) ;
        relativeTime = findViewById(R.id.enterTime) ;
        enterTime = findViewById(R.id.time) ;

        remindButton = findViewById(R.id.setRemainder) ;
        database = new Database(DateTime.this) ;

        Calendar calendar = Calendar.getInstance() ;
        final int year = calendar.get(Calendar.YEAR) ;
        final int month = calendar.get(Calendar.MONTH) ;
        final int day = calendar.get(Calendar.DAY_OF_MONTH) ;

        relativeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(DateTime.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1 ;
                        datum = year+""+month+""+day ;
                        enterDate.setText(datum);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        relativeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(DateTime.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay ;
                        min = minute ;
                        vreme = hour+":"+min ;
                        SimpleDateFormat twentyFour = new SimpleDateFormat("HH:mm") ;
                        try {
                            Date date = twentyFour.parse(vreme) ;
                            //SimpleDateFormat twelve = new SimpleDateFormat("HH:mm") ;
                            enterTime.setText(twentyFour.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, true) ;
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });
        textName = findViewById(R.id.categoryName) ;
        textName.setText(name);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("id", "name", NotificationManager.IMPORTANCE_DEFAULT) ;
            NotificationManager manager = getSystemService(NotificationManager.class) ;
            manager.createNotificationChannel(channel);
        }

        remindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSet = database.addOne(enterDate.getText().toString(), enterTime.getText().toString(), takeRemainder.getText().toString(), textName.getText().toString());
                if (isSet == true) {
                    Toast.makeText(DateTime.this, "Reminder Set", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DateTime.this, "Try Again", Toast.LENGTH_SHORT).show();
                }

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent notificationIntent = new Intent(DateTime.this, NotificationClass.class);
                PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar cal = Calendar.getInstance();

                cal.set(Calendar.HOUR_OF_DAY, 8);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 15);

                if(database.checkIfTodaysDateExists()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
                    }
                }

                //Toast.makeText(DateTime.this, "Hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DateTime.this, MainActivity.class) ;
                startActivity(intent);
            }
        });
    }

    public void getName(){
        if(getIntent().hasExtra("category")){
            name = getIntent().getStringExtra("category") ;
        }
    }
}