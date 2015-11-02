package com.example.nantes.mines.myChat.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nantes.mines.myChat.R;
import com.example.nantes.mines.myChat.listener.LoginListener;
import com.example.nantes.mines.myChat.listener.RegisterListener;
import com.example.nantes.mines.myChat.task.LoginAsyncTask;
import com.example.nantes.mines.myChat.task.RegisterAsyncTask;
import com.example.nantes.mines.myChat.utils.Data;

/**
 * The login activity
 */
public class LoginActivity extends Activity implements View.OnClickListener, LoginListener, RegisterListener {

    /** The tag (for debugging purpose) */
    private final String TAG = LoginActivity.class.getSimpleName();
    /** The connexion button */
    private Button btn_connect;
    /** The cancel button */
    private Button btn_cancel;
    /** The register image button */
    private ImageButton btn_register;
    /** EditText containing the login of the user */
    private EditText text_login;
    /** EditText containing the password of the user */
    private EditText text_pass;
    /** TextView shown when an error occurred*/
    private TextView text_error;
    /** ProgressBar shown during loading */
    private ProgressBar progressBar;
    /** SharedPreferences used to save some data*/
    private SharedPreferences sp;
    /** The asyncTask for "Login" */
    private LoginAsyncTask loginAsyncTask;
    /** The asyncTask for "Register" */
    private RegisterAsyncTask registerAsyncTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Retrieves elements
        btn_connect = (Button) findViewById(R.id.button_Connect);
        btn_cancel = (Button) findViewById(R.id.button_Cancel);
        btn_register = (ImageButton) findViewById(R.id.btn_register);
        text_login = (EditText) findViewById(R.id.ET_login);
        text_pass = (EditText) findViewById(R.id.ET_pass);
        text_error = (TextView) findViewById(R.id.t_error);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_login);

        // Listeners
        btn_connect.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        // Retrieves shared preferences
        sp = getSharedPreferences(Data.getPreferences(), MODE_PRIVATE);
        if (sp.contains(Data.getPrefLogin())) {
            text_login.setText(sp.getString(Data.getPrefLogin(), ""));
            text_pass.setText(sp.getString(Data.getPrefPass(), ""));
        }
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState) {
        // Save the error text if existing
        if (text_error.getVisibility() == View.VISIBLE) {
            outState.putBoolean(Data.getNameStateLoginError(), true);
        } else {
            outState.putBoolean(Data.getNameStateLoginError(), false);
        }
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the error text if existing
        boolean visible = savedInstanceState.getBoolean(Data.getNameStateLoginError());
        if (visible) {
            text_error.setVisibility(View.VISIBLE);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Connect:
                login();
                break;
            case R.id.button_Cancel:
                cancel();
                break;
            case R.id.btn_register:
                register();
                break;
            default:
                break;
        }
    }


    public void OnLoginSuccess() {
        // Disable the progressBar
        progressBar.setVisibility(View.GONE);
        // Save the credentials using sharedPreferences
        sp.edit()
                .putString(Data.getPrefLogin(), text_login.getText().toString())
                .putString(Data.getPrefPass(), text_pass.getText().toString())
                .apply();
        // Start the main activity
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void OnLoginFail() {
        // Disable the progressBar
        progressBar.setVisibility(View.GONE);
        // Show an error message
        Toast.makeText(getApplicationContext(), getString(R.string.login_activity_toast_login_error),
                Toast.LENGTH_SHORT).show();
    }

    public void OnRegisterSuccess() {
        // Disable the progressBar
        progressBar.setVisibility(View.GONE);
        // Show an error message
        Toast.makeText(getApplicationContext(), getString(R.string.login_activity_toast_register_success),
                Toast.LENGTH_SHORT).show();
    }

    public void OnRegisterFail(String message) {
        // Disable the progressBar
        progressBar.setVisibility(View.GONE);
        // Show an error message
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Performs the login
     */
    private void login() {
        // Check the state of the login task, and create it if needed
        if (loginAsyncTask == null) {
            loginAsyncTask = new LoginAsyncTask(this);
            Log.i(TAG, getString(R.string.app_task_null));
        } else if (loginAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING) ||
                (loginAsyncTask.getStatus().equals(AsyncTask.Status.PENDING))) {
            Log.i(TAG, getString(R.string.app_task_running));
            return;
        } else if (loginAsyncTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            Log.i(TAG, getString(R.string.app_task_finished));
            loginAsyncTask = new LoginAsyncTask(this);
        }
        // Show the progress bar
        progressBar.setVisibility(View.VISIBLE);
        // Record the login & password + execute the login task
        Data.setLogin(text_login.getText().toString());
        Data.setPass(text_pass.getText().toString());
        loginAsyncTask.execute(Data.getLogin(), Data.getPass());
    }

    /**
     * Cancel the login task
     */
    private void cancel() {
        // Cancel the task
        if (loginAsyncTask == null) {
            return;
        } else if (loginAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING) ||
                (loginAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING))) {
            loginAsyncTask.cancel(true);
        }
        // Update screen
        progressBar.setVisibility(View.GONE);
        text_login.setText("");
        text_pass.setText("");
    }

    /**
     * Shows an alert dialog to register an user
     */
    private void register() {
        // Create a specific layout
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText editTextLogin = new EditText(this);
        editTextLogin.setHint(getString(R.string.login_activity_dialog_register_loginHint));
        final EditText editTextPassword = new EditText(this);
        editTextPassword.setHint(R.string.login_activity_dialog_register_passHint);
        layout.addView(editTextLogin);
        layout.addView(editTextPassword);

        // Create the alert dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.login_activity_dialog_register_title));
        alertDialogBuilder
                .setMessage(getString(R.string.login_activity_dialog_register_message))
                .setView(layout)
                .setNegativeButton(getString(R.string.login_activity_dialog_register_negativeBtn), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.login_activity_dialog_register_positiveBtn), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        registerTask(editTextLogin, editTextPassword);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Performs the registration
     * @param editTextLogin         the editText containing the login to register
     * @param editTextPassword      the editText containing the password to register
     */
    private void registerTask(EditText editTextLogin, EditText editTextPassword) {
        // Check the state of the register task, and create it if needed
        if (registerAsyncTask == null) {
            registerAsyncTask = new RegisterAsyncTask(this);
            Log.i(TAG, getString(R.string.app_task_null));
        } else if (registerAsyncTask.getStatus().equals(AsyncTask.Status.RUNNING) ||
                (registerAsyncTask.getStatus().equals(AsyncTask.Status.PENDING))) {
            Log.i(TAG, getString(R.string.app_task_running));
            return;
        } else if (registerAsyncTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            Log.i(TAG, getString(R.string.app_task_finished));
            registerAsyncTask = new RegisterAsyncTask(this);
        }
        // Show the progress bar
        progressBar.setVisibility(View.VISIBLE);
        // Stock the login & password + execute the register task
        String register_login = editTextLogin.getText().toString();
        String register_pass = editTextPassword.getText().toString();
        registerAsyncTask.execute(register_login, register_pass);
    }
}
