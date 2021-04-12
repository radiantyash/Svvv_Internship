package com.abc.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView verifyMsg;
    Button resendCode;
    FirebaseAuth fAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();

        resendCode = findViewById(R.id.resendCode);
        verifyMsg = findViewById(R.id.verifymsg);

        userId = fAuth.getCurrentUser().getUid();
        FirebaseUser user = fAuth.getCurrentUser();

        if(!user.isEmailVerified()){
           verifyMsg.setVisibility(View.VISIBLE);
           resendCode.setVisibility(View.VISIBLE);

           resendCode.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           Toast.makeText(view.getContext() , "Verification Mail Has Been Sent", Toast.LENGTH_SHORT).show();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.d("tag", "onFailure: Email not sent"+e.getMessage());
                       }
                   });
               }
           });
        }
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();//logout user
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}