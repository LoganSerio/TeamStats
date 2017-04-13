package com.example.logan.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AddPosition extends AppCompatActivity {

    Button btnSubmitAddPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_position);
        btnSubmitAddPosition = (Button) findViewById(R.id.submitAddPositionButton);
        btnSubmitAddPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPosition.this,PositionStats.class));
            }
        });
    }
}
