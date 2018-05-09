package com.akshit.sih;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class Phone1 extends AppCompatActivity {
    private final int RC_SIGN_IN = 123 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseAuth auth = FirebaseAuth.getInstance();


            if (auth.getCurrentUser()==null)
            {
                startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder().setAvailableProviders(Arrays
                                .asList(new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build())).build(), RC_SIGN_IN);


            }
            else
                {
                startActivity(new Intent(this, Owner_MapsActivity.class)
                        .putExtra("phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()));
                finish();

            }
        }


        protected void onActivityResult(int requestCode ,int resultCode , Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RC_SIGN_IN)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK)
            {
                if (!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty())
                {
                    startActivity(new Intent(this,Phone1.class).putExtra("phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()));
                    finish();
                    return;
                }
                else
                {
                    if (response==null)
                    { Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    return;}

                    if (response.getErrorCode()== ErrorCodes.NO_NETWORK)
                    {
                        Toast.makeText(this, "No Internet ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (response.getErrorCode()== ErrorCodes.UNKNOWN_ERROR)
                    {
                        Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Toast.makeText(this, "Unknown Sign In Error!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
