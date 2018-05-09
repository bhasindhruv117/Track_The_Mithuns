package com.akshit.sih;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User extends AppCompatActivity {

    EditText name,phone,address;
    String uid;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= databaseReference.child("owners").child(uid).child("details");
        databaseReference.child("name").setValue(name);
        databaseReference.child("phone").setValue(phone);
        databaseReference.child("address").setValue(address);
    }
}
