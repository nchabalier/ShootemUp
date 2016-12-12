package com.example.nicolas.shootemup;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

public class MainActivity extends Activity {

    private ImageButton buttonOptions;
    private ImageButton buttonUpdgrade;
    private Button buttonScore;
    private EditText editPseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPseudo = (EditText) findViewById(R.id.editPseudo);

        buttonOptions = (ImageButton) findViewById(R.id.buttonOptions);
        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent optionsActivityIntent = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(optionsActivityIntent);
            }
        });

        buttonUpdgrade = (ImageButton) findViewById(R.id.buttonUpgrade);
        buttonUpdgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editPseudo.getText().toString();

                //If the current name is not in the data base we add it with a score equal to 0
                ScoreBoardDAO scoreBoardDAO = new ScoreBoardDAO(getBaseContext());
                scoreBoardDAO.open();
                scoreBoardDAO.add(new ScoreBoard(1, name, 0,0,15,10,TypeWeapon.BASE));
                scoreBoardDAO.close();

                //TODO: passer le "name" en extra dans l'intent
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
