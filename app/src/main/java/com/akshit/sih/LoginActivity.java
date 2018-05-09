package com.akshit.sih;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
   // private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    String h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        h=getIntent().getExtras().getString("Value");
        auth = FirebaseAuth.getInstance();

        inputEmail =  findViewById(R.id.email);
        inputPassword =  findViewById(R.id.password);
        //progressBar = findViewById(R.id.progressBar);
        btnSignup =  findViewById(R.id.btn_signup);
        btnLogin =  findViewById(R.id.btn_login);
        btnReset =  findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEmail.setText("");
                inputPassword.setText("");
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEmail.setText("");
                inputPassword.setText("");
                startActivity(new Intent(LoginActivity.this, ResetPassword.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNet();

                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    inputEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    inputPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    inputPassword.requestFocus();
                    return;
                }
               // progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {


                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                              //  progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this," Authentication failed, check your email and password or sign up", Toast.LENGTH_LONG).show();
                                    }
                                 else {
                                    if(checkIfEmailVerified()) {
                                        if(h.equals("owner"))
                                        {
                                            Intent intent = new Intent(LoginActivity.this,Owner_MapsActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else if(h.equals("admin"))
                                        {
                                           Intent intent = new Intent(LoginActivity.this, Admin_MapsActivity1.class);
                                            startActivity(intent);
                                            finish();

                                        }

                                    }
                                    else
                                    {
                                        inputPassword.setText("");
                                    }
                                }
                            }
                        });
            }
        });
    }

    public void checkNet()
    {
        if(!isNetworkAvailable())
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Internet Connection Required ").setCancelable(true).setPositiveButton("Retry",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,
                                    int id) {

                    checkNet();;
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    private boolean checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {

            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            return true;

        }
        else
        {

            Toast.makeText(LoginActivity.this, "Please verify your email...", Toast.LENGTH_SHORT).show();
            return false;


        }
    }

    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}

