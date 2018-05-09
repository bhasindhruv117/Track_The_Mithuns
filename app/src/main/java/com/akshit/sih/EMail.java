package com.akshit.sih;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EMail extends AppCompatActivity {
    EditText mailTo, mailSubject, mailMessage;
    Button sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        mailTo = (EditText) findViewById(R.id.email);
        mailSubject = (EditText) findViewById(R.id.subject);
        mailMessage = (EditText) findViewById(R.id.content);

        sendEmail = (Button) findViewById(R.id.button);
        sendEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //Get and Set editText value in String.
                String to = mailTo.getText().toString();
                String subject = mailSubject.getText().toString();
                String message = mailMessage.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //This will show prompts of email intent
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email sender :"));
            }

        });
    }
}

