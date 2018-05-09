package com.akshit.sih;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class between extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("owners");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean a=false;
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if(!ds.getKey().toString().equals(uid))
                    {
                        startActivity(new Intent(getApplicationContext(),GetOwnerDetails1.class));
                        a=true;


                    }
                }
                if(a==false)
                {
                    startActivity(new Intent(getApplicationContext(),Owner_MapsActivity.class));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
