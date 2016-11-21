package com.example.nicolas.shootemup;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Nicolas on 12/11/2016.
 */


public class ScoreBoardAdapter extends ArrayAdapter<ScoreBoard> {

    private final Context context;
    private final List<ScoreBoard> scores;

    public ScoreBoardAdapter(Context context, int resource, List<ScoreBoard> scores) {
        super(context, resource, scores);
        this.context = context;
        this.scores = scores;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.layout_score, null, true);

        TextView nameTextView = (TextView) rowView.findViewById(R.id.textName);
        TextView scoreTextView = (TextView) rowView.findViewById(R.id.textScore);


        nameTextView.setText(scores.get(position).getName());
        scoreTextView.setText(Long.toString(scores.get(position).getScore()));

        return rowView;
    }
}

