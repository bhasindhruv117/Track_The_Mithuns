package com.akshit.sih;

import android.*;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Owner_MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    String p="35,77";
    String m="35,77";
    private String uid;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationManager locationManager;
    double lat,lng;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    NavigationView navigationView;

    Map<String,String> map=new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__maps);


        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        mToogle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        navigationView=(NavigationView)findViewById(R.id.naav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id) {
                    case R.id.nav_account:
                        startActivity(new Intent(Owner_MapsActivity.this,UserInfo.class));

                        break;
                    case R.id.nav_maps:
                        startActivity(new Intent(Owner_MapsActivity.this,UserInfo.class));
                        break;
                    case R.id.nav_logout:

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Owner_MapsActivity.this,MainActivity.class));



                        break;


                }
                return false;
            }
        });
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();




        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);





//        myRef = database.getReference("owners").child(uid).child("Mithuns");


        myRef = database.getReference("owners").child(uid).child("Mithuns");
        //myRef.getParent().child("Details").child("location").setValue(p);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


               // m=dataSnapshot.child("Details").child("loaction").getValue().toString();

                //if(dataSnapshot.child("Details").child("loaction").getValue().toString().equals("0,0"))
                   //myRef.getParent().child("Details").child("location").setValue(p);





                mGoogleMap.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {






                    if(ds.getValue().toString().equals("0,0"))
                    {
                       // Toast.makeText(Owner_MapsActivity.this,"NO MITHUNS",Toast.LENGTH_LONG).show();

                    }
                    else if(!ds.getKey().equals("status")) {

                        String h = ds.getValue().toString();
                        String o = ds.getKey();
                        String separated[] = h.split(",");
                        String lat1 = separated[0].trim();
                        String lng1 = separated[1].trim();
                        double lat = Double.parseDouble(lat1);
                        double lng = Double.parseDouble(lng1);
//                        if(!m.containsKey(o))
//                            m.put(o,0);
                    MarkerOptions options1 = new MarkerOptions().title(o).position(new LatLng(lat, lng)).snippet("I am here");
                   mGoogleMap.addMarker(options1);

                        //notification
//

//                     notify.cancel(NOTIFICATION_ID);
                        String separated1[] = p.split(",");
                        String lat11 = separated1[0].trim();
                        String lng11 = separated1[1].trim();
                        double lat111 = Double.parseDouble(lat11);
                        double lng111 = Double.parseDouble(lng11);

                        //        String separated1[] = p.split(",");
//        String lat11 = separated1[0].trim();
//        String lng11 = separated1[1].trim();
//        double lat111 = Double.parseDouble(lat11);
//        double lng111 = Double.parseDouble(lng11);
//
// LatLng dangerous_area=new LatLng(lat111,lng111);
//        mGoogleMap.addCircle(new CircleOptions()
//                .center(dangerous_area)
//                .radius(5000)
//                .strokeColor(Color.BLACK)
//                .fillColor(0x220000FF)
//                .strokeWidth(5.0f)
////
//        );





                        float[] a = new float[2];
                        Location.distanceBetween(lat, lng, lat111, lng111, a);


                        if (a[0] > 5000) {
                            if(!map.containsKey(ds.getKey().toString()))
                            {
                                map.put(ds.getKey().toString(),"in");
                                sendNotification(ds.getKey().toString(), "In");
                            }
                            else{
                                if(map.get(ds.getKey().toString()).equals("out"))
                                {
                                    sendNotification(ds.getKey().toString(), "In");
                                }
                            }



                            Log.e("loc", "ouside");
                        }
                        else {
                            if(!map.containsKey(ds.getKey().toString()))
                            {
                                map.put(ds.getKey().toString(),"out");
                                sendNotification(ds.getKey().toString(), "out");
                            }
                            else
                                {
                                if(map.get(ds.getKey().toString()).equals("in"))
                                {
                                    sendNotification(ds.getKey().toString(), "out");
                                }
                            }
                            Log.e("loc", "inside");

                        }


                        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                /////f(arg0.getTitle()!="Current Position"){
                                    Intent i = new Intent(Owner_MapsActivity.this, CattleInfo.class);
                                    i.putExtra("value",marker.getTitle().toString());

                                    startActivity(i);

                            }
                        });

//                            else{
//                                Toast.makeText(Owner_MapsActivity.this,"It's Current Location",Toast.LENGTH_LONG).show();
//                                return false;
//                            }
//                                // Toast.makeText(Admin_MapsActivity.this, "marker is clicked", Toast.LENGTH_SHORT).show();// display toast



                }
                }
            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private void sendNotification(String a,String b)
    {
        Notification.Builder builder=new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Notification")
                .setContentText(a+" is "+b+" dangerous area");
        NotificationManager manager=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent=new Intent(this,Owner_MapsActivity1.class);
        PendingIntent contentIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(contentIntent);
        Notification notification=builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |=Notification.DEFAULT_SOUND;
        manager.notify(new Random().nextInt(),notification);

    }


    private void showGPSDisabledAlertToUser()
    {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);

                            mapFrag.getMapAsync(Owner_MapsActivity.this);
                        }
                    });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();


    }

    @Override
    public void onPause()
    {
        super.onPause();


        if (mGoogleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
        else
        {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
//        LatLng dangerous_area=new LatLng(lat,lng);
//        mGoogleMap.addCircle(new CircleOptions()
//                .center(dangerous_area)
//                .radius(5000)
//                .strokeColor(Color.BLACK)
//                .fillColor(0x220000FF)
//                .strokeWidth(5.0f)
//
//        );

    }

    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        double lat=(double)location.getLatitude();
        double lng=(double)location.getLongitude();
        // String lat1=(String)lat;
        p=lat+","+lng;
        //database.getReference("owners").child(uid).child("Details").child("location").setValue(p);

        Toast.makeText(this,lat+" "+lng,Toast.LENGTH_SHORT).show();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");

        if(mCurrLocationMarker!=null)
        {
            mCurrLocationMarker.remove();
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
//
////For circle  ...................................
        String separated1[] = m.split(",");
        String lat11 = separated1[0].trim();
        String lng11 = separated1[1].trim();
        double lat111 = Double.parseDouble(lat11);
        double lng111 = Double.parseDouble(lng11);

        LatLng dangerous_area=new LatLng(lat111,lng111);
        mGoogleMap.addCircle(new CircleOptions()
                .center(dangerous_area)
                .radius(5000)
                .strokeColor(Color.BLACK)
                .fillColor(0x220000FF)
                .strokeWidth(5.0f)

        );



    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else
            {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else
        {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                showGPSDisabledAlertToUser();
            }

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {

                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                        {
                            showGPSDisabledAlertToUser();
                        }

                        if (mGoogleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToogle.onOptionsItemSelected(item))
        { return true;}

        return super.onOptionsItemSelected(item);
    }
}