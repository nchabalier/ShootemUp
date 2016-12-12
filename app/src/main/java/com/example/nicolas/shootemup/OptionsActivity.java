package com.example.nicolas.shootemup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * Created by Nicolas on 04/11/2016.
 */

public class OptionsActivity extends Activity {

    private ImageButton returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // Intent which launched the options activity
        Intent i = getIntent();

        returnButton = (ImageButton) findViewById(R.id.buttonReturn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
