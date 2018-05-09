package com.akshit.sih;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GetOwnerDetails1 extends AppCompatActivity {

    EditText name;

    EditText phone;
    EditText radii;
    EditText address;
    Button submit;
    double longitude;
    double latitude;

    String p = "35,75";
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getownerdetails);





        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        radii = (EditText) findViewById(R.id.radii);
        submit = (Button) findViewById(R.id.submit);
//
//        Geocoder geocoder;
//        String bestProvider;
//        List<Address> user = null;
//        double lat;
//        double lng;
//
//        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//       double lng1=location.getLongitude();
//        double lat1=location.getLatitude();
//
//
//
//
////        if (location == null){
////            Toast.makeText(this,"Location Not found",Toast.LENGTH_LONG).show();
////        }else{
////            geocoder = new Geocoder(this);
////            try {
////                user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
////                lat=(double)user.get(0).getLatitude();
////                lng=(double)user.get(0).getLongitude();
////                System.out.println(" DDD lat: " +lat+",  longitude: "+lng);
////
////            }catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
//
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();




        myRef = database.getReference("owners");




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.child(uid).child("Details").child("Name").setValue(name.getText().toString());
                myRef.child(uid).child("Details").child("phone").setValue(phone.getText().toString());
                myRef.child(uid).child("Details").child("address").setValue(address.getText().toString());
                myRef.child(uid).child("Details").child("Radii").setValue(radii.getText().toString());
                myRef.child(uid).child("Details").child("location").setValue("35,77");
                myRef.child(uid).child("Mithuns").child("status").setValue("0,0");
               Toast.makeText(GetOwnerDetails1.this,"Done on Firebase",Toast.LENGTH_LONG).show();
               startActivity(new Intent(GetOwnerDetails1.this,Owner_MapsActivity.class));


            }
        });


    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1){
//            Bundle extras = data.getExtras();
//            longitude = extras.getDouble("Longitude");
//            latitude = extras.getDouble("Latitude");
//            Toast.makeText(GetOwnerDetails1.this,longitude+","+latitude,Toast.LENGTH_LONG).show();
//        }
//    }


}
