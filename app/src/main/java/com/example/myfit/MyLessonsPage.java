package com.example.myfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyLessonsPage extends AppCompatActivity {

    TextView myLessons;
    Lesson lesson;
    //final ArrayList<Lesson> lessonsAr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lessons_page);

        myLessons = (TextView) findViewById(R.id.myLessonsTextView);

        SharedPreferences sp = getSharedPreferences("MyFit", 0);
        String restorePhone = sp.getString("userPhone", null);


        // Read from the database -> final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Lesson");
        //Query checkUserLesson = FirebaseDatabase.getInstance().getReference("Lessons").orderByChild("phone").equalTo(restorePhone);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                  lesson=singleSnapshot.getValue(Lesson.class);
                  myLessons.setText(lesson.type+lesson.hour+ lesson.date);
            }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyLessonsPage.this, "נכשל בקריאה מהבסיס נתונים", Toast.LENGTH_LONG).show();

            }
        });
    }
}

        /*

    private void showData(DataSnapshot snapshot) {
        for(DataSnapshot sp : snapshot.getChildren()){
            lesson=snapshot.getValue(Lesson.class);
            lessonsAr.add(lesson);
            //lessonInfo.type=ds.child(lessonID).getValue("type");
            // array.add(lessonInfo.type);
    }
}


        Query checkUserLesson=FirebaseDatabase.getInstance().getReference("UsersInLesson").orderByChild("phone").equalTo(phoneStr);
        checkUserLesson.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phone.setError(null);
                }else
                    Toast.makeText(getApplicationContext(), "אין שיעורים זמינים למשתמש", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(MyLessonsPage.this, "פעולת קריאת נתונים נכשלה:(" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
  }
*/

/* mDatabase.child("users").child(userId).child("username").setValue(name);

                final String user_mail = mAuth.getCurrentUser().getEmail();
                String value = dataSnapshot.getValue(String.class);
                if (value.equals(user_mail)) {
                    textMyLessons.setText("***בתהליך בניה***");
                } else


 */