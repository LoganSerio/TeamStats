package com.example.logan.test2.com.android.teamstats.Activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logan.test2.com.android.teamstats.R;

/**
 * A class that deals with the users ability to send feedback,
 */
public class SendFeedbackActivity extends AppCompatActivity {

    Button btnFeedbackSubmit;
    EditText etxtFeedbackSubject;
    EditText etxtFeedbackResponse;
    @Override
    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        etxtFeedbackResponse = (EditText) findViewById(R.id.feedbackResponse);
        etxtFeedbackSubject = (EditText) findViewById(R.id.feedbackSubject);
        btnFeedbackSubmit = (Button) findViewById(R.id.feedbackSubmitButton);
        btnFeedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + "TeamStatsFeedback@gmail.com"));
                String subject = etxtFeedbackSubject.getText().toString();
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                String response = etxtFeedbackResponse.getText().toString();
                emailIntent.putExtra(Intent.EXTRA_TEXT,  response);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendFeedbackActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}