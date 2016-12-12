package com.example.nicolas.shootemup;


import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nicolas on 12/11/2016.
 */

public class ScoreBoardActivity extends Activity {

    ListView listView;
    ScoreBoardDAO scoreBoardDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        listView = (ListView) findViewById(R.id.scoreListView);
        //listView = getListView();
        //registerForContextMenu(listView);

        scoreBoardDAO = new ScoreBoardDAO(getBaseContext());
        scoreBoardDAO.open();
        //scoreBoardDAO.dropTable();
        //scoreBoardDAO.createTableIfNotExist();
        //scoreBoardDAO.deleteAllScores();
        scoreBoardDAO.createDefaultScoreBoard();

        List<ScoreBoard> scores = scoreBoardDAO.getAllScores();
        ScoreBoardAdapter adapter = new ScoreBoardAdapter(getBaseContext(), android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(adapter);

        scoreBoardDAO.close();

    }
}
