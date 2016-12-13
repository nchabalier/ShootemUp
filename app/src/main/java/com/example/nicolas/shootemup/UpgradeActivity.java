package com.example.nicolas.shootemup;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private  ScoreBoard myScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_upgrade);


    }

    private ArrayList<UpgradeItem> generateData(){
        ArrayList<UpgradeItem> items = new ArrayList<UpgradeItem>();
        items.add(new UpgradeItem(0, "Increase ship speed","1000 coins"));
        items.add(new UpgradeItem(1, "Increase Firerate","2000 coins"));
        items.add(new UpgradeItem(2, "Double shoots","3000 coins"));
        items.add(new UpgradeItem(3, "Missile","4000 coins"));

        return items;
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent i = getIntent();
        myScore = (ScoreBoard) i.getParcelableExtra("my_score");

        ScoreBoardDAO scoreBoardDAO = new ScoreBoardDAO(getBaseContext());
        scoreBoardDAO.open();
        final ScoreBoard myScore2 = scoreBoardDAO.getScoreboard(myScore.getName());

        scoreBoardDAO.close();


        textBestScore = (TextView) findViewById(R.id.bestScoreText);
        textBestScore.setText(String.valueOf(myScore2.getScore()));
        textTotalCoins = (TextView) findViewById(R.id.totalCoinsText);
        textTotalCoins.setText(String.valueOf(myScore2.getCoin()));


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
                storeActivityIntent.putExtra("my_score", myScore2);
                startActivity(storeActivityIntent);
            }
        });

        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gameActivityIntent = new Intent(UpgradeActivity.this, GameActivity.class);
                gameActivityIntent.putExtra("my_score", myScore2);
                startActivity(gameActivityIntent);
            }
        });

        listView = (ListView) findViewById(R.id.upgradeListView);
        UpgradeAdapter adapter = new UpgradeAdapter(this, generateData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Position :",Integer.toString(i));
                switch(i){
                    case 0:
                        if(myScore2.getCoin()>=1000) {
                            myScore2.setShipSpeed(myScore2.getShipSpeed() + 5);
                            myScore2.setCoin(myScore2.getCoin()-1000);
                        }
                        break;
                    case 1:
                        if(myScore2.getCoin()>=2000) {
                            myScore2.setShootSpeed(myScore2.getShootSpeed()+5);
                            myScore2.setCoin(myScore2.getCoin()-2000);
                        }
                        break;
                    case 2:
                        if(myScore2.getCoin()>=3000) {
                            myScore2.setWeaponType(TypeWeapon.GATTELING);
                            myScore2.setCoin(myScore2.getCoin()-3000);
                        }
                        break;
                    case 3:
                        if(myScore2.getCoin()>=4000) {
                            myScore2.setWeaponType(TypeWeapon.BAZOOKA);
                            myScore2.setCoin(myScore2.getCoin()-4000);
                        }
                        break;

                }
                ScoreBoardDAO scoreBoardDAO = new ScoreBoardDAO(getBaseContext());
                scoreBoardDAO.open();
                scoreBoardDAO.update(myScore2);

                scoreBoardDAO.close();

                textTotalCoins.setText(String.valueOf(myScore2.getCoin()));
            }
        });

    }
}
