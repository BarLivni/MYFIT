package com.example.myfit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInPage extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1001;//code for google sign in
    SignInButton signin;
    EditText name,phone;
    String nameStr,phoneStr;
    GoogleSignInClient mGoogleSignInClient;
    private  FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        //GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        // Check if user is signed in (non-null) and move other activity.
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(getApplicationContext(), MainLandingPage.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        mAuth = FirebaseAuth.getInstance();
        signin=findViewById(R.id.sign_in_button);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);


        createRequest();

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneStr;
                signIn();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyFit", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                phoneStr=phone.getText().toString();
                editor.putString("userPhone",phoneStr);
                editor.commit();

            }
        });
    }

    private void createRequest() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {

        if (name.length() <1){
            name.setError("נדרש למלא שם");
            name.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            phone.setError("הכנס מספר נייד");
            phone.requestFocus();
            return;
        }

        //intent to sign in with google
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully
            firebaseAuthWithGoogle(account);
            //final String uuid = UUID.randomUUID().toString();
            nameStr=name.getText().toString();
            phoneStr=phone.getText().toString();
            User addUser=new User(nameStr,phoneStr);
            // write to database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users").child(phoneStr);
            myRef.child("Users").child(phoneStr);
            myRef.setValue(addUser);

            Intent intent = new Intent(SignInPage.this,MainLandingPage.class);
            startActivity(intent);
        } catch (ApiException e) {
            // Google Sign In failed
            Toast.makeText(SignInPage.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignInPage.this,MainLandingPage.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInPage.this,"sorry auth failed",Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }

}

/*public verification (){

    Query checkUser=FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone").equalTo(phoneStr);
    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                phone.setError(null);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(SignInPage.this,error.getMessage(),Toast.LENGTH_SHORT).show();

        }
    });
}
*/