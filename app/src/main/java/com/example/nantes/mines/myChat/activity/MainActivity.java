package com.example.nantes.mines.myChat.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nantes.mines.myChat.R;
import com.example.nantes.mines.myChat.adapter.MessageAdapter;
import com.example.nantes.mines.myChat.listener.MessageSenderListener;
import com.example.nantes.mines.myChat.listener.MessageUpdaterListener;
import com.example.nantes.mines.myChat.model.Message;
import com.example.nantes.mines.myChat.task.MessageSenderAsyncTask;
import com.example.nantes.mines.myChat.task.MessageUpdaterAsyncTask;
import com.example.nantes.mines.myChat.utils.Data;

import java.util.List;
import java.util.UUID;

/**
 * Main activity of the application. Show the message list and allow to send some message
 */
public class MainActivity extends Activity implements View.OnClickListener, MessageUpdaterListener, MessageSenderListener {
    /** The tag (for debugging purpose) */
    private final String TAG = LoginActivity.class.getSimpleName();
    /** ListView containing the messages */
    private ListView listView;
    /** The send imageButton */
    private ImageButton btn_send;
    /** The refresh imageButton */
    private ImageButton btn_refresh;
    /** TextView used to type a message*/
    private TextView tv_message;
    /** The asyncTask for action "getMessage" */
    private MessageUpdaterAsyncTask messageUpdater;
    /** The asyncTask for action "sendMessage" */
    private MessageSenderAsyncTask messageSender;
    /** ProgressBar shown during loading */
    private ProgressBar progressBar;
    /** The current position in the listView */
    private int currentPosition = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieves elements
        this.btn_send = (ImageButton) findViewById(R.id.btn_send);
        this.btn_refresh = (ImageButton) findViewById(R.id.btn_refresh);
        this.tv_message = (TextView) findViewById(R.id.tv_message);
        this.listView = (ListView) findViewById(R.id.listView);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar_chat);

        // Listeners
        this.btn_send.setOnClickListener(this);
        this.btn_refresh.setOnClickListener(this);

        // Retrieves different parameters
        if (savedInstanceState == null){
            Toast.makeText(getApplicationContext(), getString(R.string.chat_activity_toast_welcome) + " " +
                    Data.getLogin(), Toast.LENGTH_SHORT).show();
        }else {
            this.currentPosition = savedInstanceState.getInt(Data.getNameStateList());
        }
        // Get the messages from the server
        updateMessages();
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

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(Data.getNameStateList(), listView.getFirstVisiblePosition());
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                sendMessage();
                break;
            case R.id.btn_refresh:
                updateMessages();
                break;
            default:
                break;
        }
    }

    public void OnMessageUpdateSuccess(List<Message> messages) {
        this.progressBar.setVisibility(View.GONE);
        if (this.listView.getAdapter() == null) {
            this.listView.setAdapter(new MessageAdapter(this, messages));
        } else {
            ((MessageAdapter) this.listView.getAdapter()).refill(messages);
        }
        if (this.currentPosition == 0) {
            this.listView.setSelection(this.listView.getCount() - 1);
        }else {
            this.listView.setSelection(this.currentPosition);
        }

    }

    public void OnMessageUpdateFail() {
        this.progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.chat_activity_toast_error_messageUpdated),
                Toast.LENGTH_SHORT).show();
    }

    public void OnMessageSendSuccess() {
        this.progressBar.setVisibility(View.GONE);
        tv_message.setText("");
        updateMessages();
    }

    public void OnMessageSendFail() {
        this.progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.chat_activity_toast_error_messageSend),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Performs the sending of a message
     */
    private void sendMessage() {
        // Check the state of the sending task, and create it if needed
        if (messageSender == null) {
            messageSender = new MessageSenderAsyncTask(this);
            Log.i(TAG, getString(R.string.app_task_null));
        } else if (messageSender.getStatus().equals(AsyncTask.Status.RUNNING) ||
                (messageSender.getStatus().equals(AsyncTask.Status.PENDING))) {
            Log.i(TAG, getString(R.string.app_task_running));
            return;
        } else if (messageSender.getStatus().equals(AsyncTask.Status.FINISHED)) {
            Log.i(TAG, getString(R.string.app_task_finished));
            messageSender = new MessageSenderAsyncTask(this);
        }
        // Show the progress bar
        this.progressBar.setVisibility(View.VISIBLE);
        // Record the message + execute the sending task
        String message = tv_message.getText().toString();
        if (message.equals(Data.getNameEasterEggDraw())){
            tv_message.setText("");
            Intent i = new Intent(this, DrawActivity.class);
            startActivity(i);
        }else{
            messageSender.execute(new Message(UUID.randomUUID().toString(), Data.getLogin(), message));
        }
    }

    /**
     * Performs the receipt of messages
     */
    private void updateMessages() {
        // Check the state of the receiving task, and create it if needed
        if (messageUpdater == null) {
            this.messageUpdater = new MessageUpdaterAsyncTask(this);
            Log.i(TAG, getString(R.string.app_task_null));
        } else if (messageUpdater.getStatus().equals(AsyncTask.Status.RUNNING) ||
                (messageUpdater.getStatus().equals(AsyncTask.Status.PENDING))) {
            Log.i(TAG, getString(R.string.app_task_running));
            return;
        } else if (messageUpdater.getStatus().equals(AsyncTask.Status.FINISHED)) {
            Log.i(TAG, getString(R.string.app_task_finished));
            this.messageUpdater = new MessageUpdaterAsyncTask(this);
        }
        // Show the progress bar
        this.progressBar.setVisibility(View.VISIBLE);
        // Execute the message updater task
        this.messageUpdater.execute(Data.getLogin(), Data.getPass());
    }
}