package com.akshit.sih;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Phone2 extends AppCompatActivity {
    TextView txtPhone ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.action_sign_out)
            signOut();
        return true;
    }

    private void signOut() {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(Phone2.this,Phone1.class));
                        finish();
                    }

                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone2);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Firebase Auth");
        setSupportActionBar(toolbar);
        txtPhone= findViewById(R.id.txtPhone);
        if (getIntent()!=null)
              txtPhone.setText(getIntent().getStringExtra("phone"));


    }


}
