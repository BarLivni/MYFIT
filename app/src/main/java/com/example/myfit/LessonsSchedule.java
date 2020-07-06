package com.example.myfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LessonsSchedule extends AppCompatActivity {

    DatabaseReference mRef;
    Button btnShowLesson;
    TextView textViewShcedule;
    final ArrayList<Lesson> array=new ArrayList<>();
    String mdate;
    Lesson lessonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_schedule);


        textViewShcedule=findViewById(R.id.textViewShcedule);

        final CalendarView simpleCalendarView = (CalendarView) findViewById(R.id.calendarView); // get the reference of CalendarView
        simpleCalendarView.setOnDateChangeListener
                (new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, final int year, final int month, final int dayOfMonth) {
                        mdate=year+"/"+month+"/"+dayOfMonth;
                        // after click on btn, it show the lessons of that day
                        btnShowLesson = findViewById(R.id.btnViewLesson);
                        btnShowLesson.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mRef = FirebaseDatabase.getInstance().getReference().child("Lessons");
                                Query query=mRef.orderByChild("date").equalTo(mdate);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        array.clear();
                                        if(snapshot.exists())
                                            showData(snapshot);
                                        else
                                            Toast.makeText(LessonsSchedule.this, "אין שיעורים זמינים באותו היום", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(LessonsSchedule.this, "נכשל בקריאה מהבסיס נתונים", Toast.LENGTH_LONG).show();

                                    }
                                });

                            }
                        });
                    }
                });
    }


    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
            lessonInfo=snapshot.getValue(Lesson.class);
            array.add(lessonInfo);
            //lessonInfo.type=ds.child(lessonID).getValue("type");
            // array.add(lessonInfo.type);
        }
    }


}
