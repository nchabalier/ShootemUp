package com.example.nicolas.shootemup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Nicolas on 04/11/2016.
 */

public class UpgradeActivity extends Activity{

    private Button buttonReturn;
    private Button buttonBuyCoins;
    private Button buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        buttonReturn = (Button) findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonBuyCoins = (Button) findViewById(R.id.buttonBuyCoins);
        buttonBuyCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent storeActivityIntent = new Intent(UpgradeActivity.this, StoreActivity.class);
                startActivity(storeActivityIntent);
            }
        });

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivityIntent = new Intent(UpgradeActivity.this, GameActivity.class);
                startActivity(gameActivityIntent);
            }
        });

    }
}
