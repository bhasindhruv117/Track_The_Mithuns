package com.akshit.sih;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Marker_MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private String uid;
    private String h;

    FirebaseDatabase database;
    DatabaseReference myRef;

    double lat,lng;
    String u="35,75";
    //  String p;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationManager locationManager;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    NavigationView navigationView;
    //final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker__maps);
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
                        startActivity(new Intent(Marker_MapsActivity.this,UserInfo.class));

                        break;
                    case R.id.nav_maps:
                        startActivity(new Intent(Marker_MapsActivity.this,UserInfo.class));
                        break;
                    case R.id.nav_logout:

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Marker_MapsActivity.this,MainActivity.class));
                        finish();

                        break;

                }
                return false;
            }
        });
        uid=getIntent().getExtras().getString("value");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        else
        {
            Toast.makeText(this,"In else",Toast.LENGTH_LONG).show();
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                showGPSDisabledAlertToUser();
            }
        }
        database= FirebaseDatabase.getInstance();
        myRef = database.getReference("owners").child(uid).child("Mithuns");

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);


        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                        String h=ds.getValue().toString();
                        String o=ds.getKey();
                        String separated[]=h.split(",");
                        String lat1=separated[0].trim();
                        String lng1=separated[1].trim();
                        double lat=Double.parseDouble(lat1);
                        double lng=Double.parseDouble(lng1);
                        if(mCurrLocationMarker!=null)
                            mCurrLocationMarker.remove();
                        MarkerOptions options1=new MarkerOptions().title(o).position(new LatLng(lat,lng)).snippet("I am here");
                        mGoogleMap.addMarker(options1);
                        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                        {

                            @Override
                            public boolean onMarkerClick(Marker arg0) {
                                // if marker  source is clicked

                                    Intent i = new Intent(Marker_MapsActivity.this, ShowActivity.class);
                                    i.putExtra("value", arg0.getTitle().toString());
                                    startActivity(i);
                                    Toast.makeText(Marker_MapsActivity.this, "Marker is clicked", Toast.LENGTH_SHORT).show();

                                return true;
                            }

                        });

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

  private void showGPSDisabledAlertToUser()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);

                        mapFrag.getMapAsync(Marker_MapsActivity.this);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
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

        //stop location updates when Activity is no longer active
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

        LatLng dangerous_area=new LatLng(lat,lng);
        mGoogleMap.addCircle(new CircleOptions()
                .center(dangerous_area)
                .radius(5000)
                .strokeColor(Color.BLUE)
                .fillColor(0x220000FF)
                .strokeWidth(5.0f)

        );
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
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
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
        double lat=location.getLatitude();
        double lng=location.getLongitude();
        u=lat+","+lng;
        Toast.makeText(this,lat+" "+lng,Toast.LENGTH_SHORT).show();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else
            {
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
                    Toast.makeText(Marker_MapsActivity.this, "Permission Denied",Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }
}