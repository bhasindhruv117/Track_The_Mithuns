package com.akshit.sih;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CattleInfo extends AppCompatActivity  implements View.OnClickListener{

    EditText t1;
    String type;
    Button btnSave;
    Button btnShow;
    String vaccDate;
    Spinner mySpinner;
    String[] mon = new String[]{
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "June",
            "July",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
    };
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DocumentReference myDb = FirebaseFirestore.getInstance().document("Vaccination/Users");
    DatabaseReference myRef ;
    String uid;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_info);
        a=getIntent().getExtras().getString("value");

        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        t1 = findViewById(R.id.getTime);
       // findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        btnSave = findViewById(R.id.btnSavee);
        btnSave.setOnClickListener(this);
        btnShow = findViewById(R.id.btnShoww);





        mySpinner = findViewById(R.id.mySpinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CattleInfo.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.vaccinationType));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        btnShow.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View view) {
                if(!isConnected(CattleInfo.this)) buildDialog(CattleInfo.this).show();
                else {
                    Intent i;
                    i = new Intent(getApplicationContext(), ShowActivity.class);
                    i.putExtra("value",a);
                    startActivity(i);
                }
            }


        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_ID);
            }
        });

    }

    public boolean isConnected(Context context)
    {
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo =cm.getActiveNetworkInfo();
        if (netinfo!= null && netinfo.isConnectedOrConnecting())
        {
            android.net.NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile =cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return mobile != null && mobile.isConnectedOrConnecting() || wifi != null && wifi.isConnectedOrConnecting();
        }
        else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("NO Internet Connection");
        builder.setMessage("Please check your internet connection!!!!");
        return builder;
    }





    private void colocar_fecha() {
        t1.setText(mDayIni +"-" +(mon[mMonthIni]) +   "-" + mYearIni);
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();

                }

            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);


        }


        return null;
    }

    private void SetCattleInfo() {
        type = mySpinner.getSelectedItem().toString();
        myRef=database.getReference().child("owners").child(uid).child("Vaccination").child(a).child(String.valueOf(mYearIni)).child(type);
        myRef.addValueEventListener(new ValueEventListener() {
            int flag=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists())
                {  // findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    myRef.setValue(vaccDate);
                    Toast.makeText(CattleInfo.this, "Saved", Toast.LENGTH_SHORT).show();
                    //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    flag=1;
                    return;

                }
                else
                {    if (flag == 0 )
                {     // findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    Toast.makeText(CattleInfo.this, "Data already present for "+ type+ " for year "+ String.valueOf(mYearIni), Toast.LENGTH_LONG).show();
                  //  findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                }
                }
                return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



       /* Map<String,Object> user = new HashMap<>();
        user.put("date",vaccDate);
        //myDb.collection(String.valueOf(mYearIni)).document(type).set(user);
*/


    }



    @Override
    public void onClick(View view) {
        vaccDate = t1.getText().toString();
        if (view == btnShow)
            finish();

        if (view == btnSave) {
            if(vaccDate.equals(""))

                Toast.makeText(this, "Please select appropriate date", Toast.LENGTH_SHORT).show();

            else {
                if(!isConnected(CattleInfo.this)) buildDialog(CattleInfo.this).show();
                else {
                    SetCattleInfo();

                    t1.getText().clear();
                }
            }



        }
    }
}
