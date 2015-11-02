package com.example.nantes.mines.myChat.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nantes.mines.myChat.R;

/**
 * Draw activity
 */
public class DrawActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_quit:
                quitApp();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Shows an alert dialog to quit the activity
     */
    private void quitApp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DrawActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.app_dialog_quit_title));
        alertDialogBuilder
                .setNegativeButton(getString(R.string.app_dialog_quit_cancelBtn), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.app_dialog_quit_quitBtn), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DrawActivity.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
