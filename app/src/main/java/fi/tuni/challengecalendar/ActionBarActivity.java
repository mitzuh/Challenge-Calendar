package fi.tuni.challengecalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class ActionBarActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(this);
    }

    /**
     * Initializes the menu bar on top of the screen.
     * <p>
     *     Loads the created menu item from the .xml and enables it.
     * </p>
     *
     * @param menu Menu to be initialized.
     * @return True.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem m = menu.findItem(R.id.settings);
        m.setEnabled(true);
        return true;
    }

    /**
     * Inflates the menu item to be displayed in the menu bar.
     *
     * @param menu Menu to be inflated.
     * @return True.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);
        return true;
    }

    /**
     * Does action, when menu item is clicked.
     *
     * <p>
     *     When settings menu item is clicked on the action bar, display
     *     it's submenu, which is clear data.
     *     When clearData menu item is clicked, call method for displaying
     *     a popup window for data clearing.
     * </p>
     *
     * @param item Clicked menu item.
     * @return False.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case (R.id.settings):
                item.getSubMenu();
                return true;

            case (R.id.clearData):
                showPopupWindow(getWindow().getDecorView().findViewById(android.R.id.content));
                return true;
        }

        return false;
    }

    /**
     * Displays a popup window on top of the current activity.
     *
     * <p>
     *     Creates a popup window on top of the screen.
     * </p>
     *
     * @param v ViewGroup of the entire content area of an activity; root element.
     */
    public void showPopupWindow(View v) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);

        // show the popup window
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // Disable background
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // Popup window buttons
        Button clearDataButton = (Button) popupView.findViewById(R.id.clearDataButton);
        Button dismissButton = (Button) popupView.findViewById(R.id.dismissPopupButton);

        clearDataButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Clears all data from the database, enables the main layout and
             * restarts the current activity.
             *
             * @param v Clicked button.
             */
            @Override
            public void onClick(View v) {
                databaseHandler.onUpgrade(databaseHandler.getWritableDatabase(), 1, 1);
                popupWindow.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                finish();
                startActivity(getIntent());
            }
        });

        dismissButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Dismisses the popup window and enables the main layout.
             *
             * @param v Clicked Button.
             */
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }
}
