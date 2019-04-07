package fi.tuni.challengecalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChallengeListAdapter extends ArrayAdapter<Challenge> {
    private Context mContext;
    int mResource;

    public ChallengeListAdapter(Context context, int resource, ArrayList<Challenge> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int id = getItem(position).getId();
        String name = getItem(position).getName();
        String date = getItem(position).getDate();

        Challenge challenge = new Challenge(id, name, date);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView2);

        tvName.setText(name);
        tvDate.setText(date);

        return convertView;
    }
}
