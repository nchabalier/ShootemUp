package com.example.nicolas.shootemup;

/**
 * Created by Nicolas on 11/12/2016.
 */


import java.util.ArrayList;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

public class UpgradeAdapter extends ArrayAdapter<UpgradeItem> {

    private final Context context;
    private final ArrayList<UpgradeItem> itemsArrayList;

    public UpgradeAdapter(Context context, ArrayList<UpgradeItem> itemsArrayList) {

        super(context, R.layout.row_upgrade, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row_upgrade, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.upgradeLabel);
        TextView valueView = (TextView) rowView.findViewById(R.id.upgradeValue);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getTitle());
        valueView.setText(itemsArrayList.get(position).getPrice());

        // 5. return rowView
        return rowView;
    }

    public UpgradeItem onItemClicked(int position){
        return itemsArrayList.get(position);
    }
}
