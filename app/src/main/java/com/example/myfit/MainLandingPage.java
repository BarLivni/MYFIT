package com.example.myfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainLandingPage extends AppCompatActivity{

    Button btnLogOut;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing_page);

        /*
        //shared preferences
        SharedPreferences sp = getSharedPreferences("MyFit ", 0 );
        final SharedPreferences.Editor sedt = sp.edit ();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        final String nameStr = signInAccount.getDisplayName();

*/
        mAuth = FirebaseAuth.getInstance();
        btnLogOut = findViewById(R.id.btnLogOut);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        ListView list = (ListView) findViewById(R.id.listView);
        adapter.add("השיעורים שלי");
        adapter.add("לוח חוגים");
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                   // sedt.putString ("User_Name", nameStr);
                 //   sedt.commit();
                    Intent intent = new Intent(MainLandingPage.this, MyLessonsPage.class);
                    startActivity(intent);
                }
                if (position == 1) {
                    Intent intent = new Intent(MainLandingPage.this, LessonsSchedule.class);
                    startActivity(intent);
                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                signOut();
            }
        });

        ImageView aniView = (ImageView) findViewById(R.id.imageRunnerAnimate);
        ObjectAnimator mover = ObjectAnimator.ofFloat(aniView, "X", 700f);
        mover.setDuration(4500);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(aniView, "alpha", 1f, 0f);
        fadeOut.setDuration(4500);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mover, fadeOut);
        animatorSet.start();
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainLandingPage.this, "Signout Successfuly", Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                        Intent intent = new Intent(MainLandingPage.this, SignInPage.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}