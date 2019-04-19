package fi.tuni.challengecalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom ArrayAdapter for Challenges.
 */
public class ChallengeListAdapter extends ArrayAdapter<Challenge> {
    private Context mContext;
    int mResource;

    /**
     * Constructor for ChallengeListAdapter.
     *
     * @param context Application context.
     * @param resource Id used to set the layout(xml file) for list items which have a text view.
     * @param objects Array of Challenges.
     */
    public ChallengeListAdapter(Context context, int resource, ArrayList<Challenge> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    /**
     * Returns the view with array items, that will be displayed
     * as a list in the user interface.
     *
     * @param position Position for a single list item.
     * @param convertView Inflatable view for list items.
     * @param parent Reference to the parent view, that this view if child for.
     * @return List view with all the items inside of it.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int id = getItem(position).getId();
        String name = getItem(position).getName();
        String date = getItem(position).getDate();
        int points = getItem(position).getPoints();

        Challenge challenge = new Challenge(id, name, date, points);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView2);

        tvName.setText(name);
        tvDate.setText(date);

        return convertView;
    }
}
