package com.example.logan.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPosition extends AppCompatActivity {

    Button btnSubmitAddPosition;
    EditText etaddPositionName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_position);
        btnSubmitAddPosition = (Button) findViewById(R.id.submitAddPositionButton);
        btnSubmitAddPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etaddPositionName = (EditText) findViewById(R.id.addPositionNameEditText);
                if(TextUtils.isEmpty(etaddPositionName.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Must enter position name",Toast.LENGTH_SHORT).show();
                }
                else
                    startActivity(new Intent(AddPosition.this,PositionStats.class));
            }
        });
    }
}
