package com.akshit.sih;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class UserInfo extends AppCompatActivity {
    FirebaseUser user;
    TextView textView,id;
    Button button;
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
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
                    case R.id.marker:
                        startActivity(new Intent(UserInfo.this,Owner_MapsActivity1.class));
                        finish();
                        break;
                    case R.id.vaccination:
                       // startActivity(new Intent(UserInfo.this,UserInfo.class));
                        break;


                }
                return false;
            }
        });
        user= FirebaseAuth.getInstance().getCurrentUser();
        textView=findViewById(R.id.username);
        String usernamee=user.getUid();
//        id=findViewById(R.id.id);
//        id.setText(usernamee);
//        button=findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserInfo.this,MainActivity.class));
                finish();
            }
        });
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(mToogle.onOptionsItemSelected(item))
//        { return true;}
//
//        return super.onOptionsItemSelected(item);
//    }
}
