package com.example.logan.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CreatePosition extends AppCompatActivity {
    Button btnCreatePositionAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_position);
        btnCreatePositionAdd = (Button) findViewById(R.id.CreatePositionAddButton);
        btnCreatePositionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreatePosition.this,AddPosition.class));
            }
        });
    }
}
