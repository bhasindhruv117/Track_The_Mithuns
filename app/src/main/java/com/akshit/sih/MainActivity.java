package com.akshit.sih;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView= findViewById(R.id.textView);
        textView.setTypeface(null, Typeface.BOLD);


        Button admin;
        FirebaseUser auth=FirebaseAuth.getInstance().getCurrentUser();
        if(auth!=null)
            startActivity(new Intent(MainActivity.this,Owner_MapsActivity.class));

        admin= findViewById(R.id.admin);
        Button owner= findViewById(R.id.owner);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(MainActivity.this,LoginActivity.class);
                String h="admin";
                j.putExtra("Value",h);
                startActivity(j);
            }
        });
        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(MainActivity.this,Phone1.class);
              //  String h="owner";
                //j.putExtra("Value",h);
                startActivity(j);
            }
        });

    }
}
