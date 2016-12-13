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

public class StoreActivity extends Activity{

    private ImageButton buttonReturn;
    private ListView listView;
    private TextView textTotalCoins;
    private  ScoreBoard myScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_store);


    }

    private ArrayList<UpgradeItem> generateData(){
        ArrayList<UpgradeItem> items = new ArrayList<UpgradeItem>();
        items.add(new UpgradeItem(0, "100 coin","0.99$"));
        items.add(new UpgradeItem(1, "500 coin","3.99$"));
        items.add(new UpgradeItem(2, "2000 coin","9.99$"));
        items.add(new UpgradeItem(3, "5000 coin","19.99$"));
        items.add(new UpgradeItem(4, "20000 coin","49.99$"));
        items.add(new UpgradeItem(5, "50000 coin","99.99$"));

        return items;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        myScore = (ScoreBoard) i.getParcelableExtra("my_score");

        ScoreBoardDAO scoreBoardDAO = new ScoreBoardDAO(getBaseContext());
        scoreBoardDAO.open();
        final ScoreBoard myScore2 = scoreBoardDAO.getScoreboard(myScore.getName());

        scoreBoardDAO.close();

        textTotalCoins = (TextView) findViewById(R.id.totalCoinsText);
        textTotalCoins.setText(String.valueOf(myScore2.getCoin()));

        buttonReturn = (ImageButton) findViewById(R.id.buttonReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.coinListView);
        UpgradeAdapter adapter = new UpgradeAdapter(this, generateData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                Log.d("Position :", Integer.toString(i));
                                                switch (i) {

                                                    case 0:
                                                        myScore2.setCoin(myScore2.getCoin() + 100);
                                                        break;
                                                    case 1:
                                                        myScore2.setCoin(myScore2.getCoin() + 500);
                                                        break;
                                                    case 2:
                                                        myScore2.setCoin(myScore2.getCoin() + 2000);
                                                        break;
                                                    case 3:
                                                        myScore2.setCoin(myScore2.getCoin() + 5000);
                                                        break;
                                                    case 4:
                                                        myScore2.setCoin(myScore2.getCoin() + 20000);
                                                        break;
                                                    case 5:
                                                        myScore2.setCoin(myScore2.getCoin() + 50000);
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




