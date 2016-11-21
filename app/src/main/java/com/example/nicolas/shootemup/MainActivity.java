package com.example.nicolas.shootemup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonOptions;
    private Button buttonUpdgrade;
    private Button buttonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOptions = (Button) findViewById(R.id.buttonOptions);
        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent optionsActivityIntent = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(optionsActivityIntent);
            }
        });

        buttonUpdgrade = (Button) findViewById(R.id.buttonUpgrade);
        buttonUpdgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upgradeActivityIntent = new Intent(MainActivity.this, UpgradeActivity.class);
                startActivity(upgradeActivityIntent);
            }
        });

        buttonScore = (Button) findViewById(R.id.buttonScore);
        buttonScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreboardActivityIntent = new Intent(MainActivity.this, ScoreBoardActivity.class);
                startActivity(scoreboardActivityIntent);
            }
        });

    }
}
