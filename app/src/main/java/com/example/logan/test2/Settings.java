package com.example.logan.test2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The Settings class that contains the option to open the user manual and the send feedback page.
 */
public class Settings extends AppCompatActivity {
    Button btnSendFeedback;
    Button btnUserManual;
    Button btnTempTeamPage;
    @Override
    /**
     * Initializes the activity and displays it on the device's screen
     * @param savedInstanceState saves the state of the app incase the app needs to be re-initialized
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnSendFeedback = (Button) findViewById(R.id.sendFeedbackButton);
        btnUserManual = (Button) findViewById(R.id.reorderTeamsButton);
        btnTempTeamPage = (Button) findViewById(R.id.btnTempTeamPage);

        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,SendFeedback.class));
            }
        });

        btnUserManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/LoganSerio/TeamStats/blob/master/README.md";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        /*
        THIS IS A TEMPORARY ACCESS TO THE TEAM PAGE, PLEASE DELETE WHEN TEAM PAGE IS LINKED
         */
        btnTempTeamPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,TeamPage.class));
            }
        });
    }
}
