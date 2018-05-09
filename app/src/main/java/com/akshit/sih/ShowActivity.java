package com.akshit.sih;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {
    ListView mVaccinesList ;
    ArrayList<String>  list ;
    ArrayAdapter<String> adapter;
    ArrayList<String> years;
    Spinner yearSpinner;
    ArrayAdapter<String> yearAdapter;
    Button btnYear;
    String err = "No vaccination in this year";
    String uid;
    String h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        btnYear=findViewById(R.id.btnYear);
        mVaccinesList= (ListView) findViewById(R.id.mVaccinesList);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        list = new ArrayList<>();
        //Info = new String();
        yearSpinner = findViewById(R.id.yearSpinner);
        years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years);
        for (int i = thisYear; i >= 2017; i--)
        {
            years.add(String.valueOf(i));
        }

        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        h=getIntent().getExtras().getString("value");
        adapter = new ArrayAdapter<String>(this,R.layout.show_user_info, R.id.userInfo,list);
        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.clear();
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

                final String selectedYear = yearSpinner.getSelectedItem().toString();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("owners").child(uid).child("Vaccination").child(h).child(selectedYear);
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String,String> map=(Map<String,String>)dataSnapshot.getValue();
                        //int u = map.size();
                        String a[] = new String[6];
                        a[0]="Hemorrhagic Septicemia";
                        a[1]="Black Quarter (BQ)";
                        a[2]="Anthrax";
                        a[3]="Brucella";
                        a[4]="Theileriosis";
                        a[5]="Rabies";

                        for(int i =0;i<6;i++)
                        {
                            if (map.containsKey(a[i]))
                            {
                                list.add(a[i]+ "\n" + map.get(a[i]));
                            }


              /*  for(DataSnapshot ds :DataSnapshot.getChildren())
                {
                    Info = ds.getValue(String.class);
                    list.add(+"   "+Info)
                }*/


                        }
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if (list.size()==0)
                        {

                            Toast.makeText(ShowActivity.this, "No data for year "+selectedYear, Toast.LENGTH_LONG).show();
                        }
                        mVaccinesList.setAdapter(adapter);





                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
