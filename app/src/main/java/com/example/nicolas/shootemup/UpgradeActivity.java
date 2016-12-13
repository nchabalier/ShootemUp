package com.example.nicolas.shootemup;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nicolas on 04/11/2016.
 */

public class UpgradeActivity extends Activity{

    private ImageButton buttonReturn;
    private Button buttonBuyCoins;
    private ImageButton buttonPlay;
    private ListView listView;
    private TextView textBestScore;
    private TextView textTotalCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_upgrade);

        Intent i = getIntent();
        final ScoreBoard myScore = (ScoreBoard) i.getParcelableExtra("my_score");

        textBestScore = (TextView) findViewById(R.id.bestScoreText);
        textBestScore.setText(String.valueOf(myScore.getScore()));
        textTotalCoins = (TextView) findViewById(R.id.totalCoinsText);
        textTotalCoins.setText(String.valueOf(myScore.getCoin()));


        buttonReturn = (ImageButton) findViewById(R.id.buttonReturn);
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

        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gameActivityIntent = new Intent(UpgradeActivity.this, GameActivity.class);
                gameActivityIntent.putExtra("my_score", myScore);
                startActivity(gameActivityIntent);
            }
        });

        listView = (ListView) findViewById(R.id.upgradeListView);
        UpgradeAdapter adapter = new UpgradeAdapter(this, generateData());
        listView.setAdapter(adapter);
        /*listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    private ArrayList<UpgradeItem> generateData(){
        ArrayList<UpgradeItem> items = new ArrayList<UpgradeItem>();
        items.add(new UpgradeItem(0, "Increase ship speed","1000 coins"));
        items.add(new UpgradeItem(1, "Increase shoot speed","2000 coins"));
        items.add(new UpgradeItem(2, "Double shoots","3000 coins"));
        items.add(new UpgradeItem(3, "Missile","4000 coins"));

        return items;
    }
}
