package com.example.nantes.mines.myChat.task;

import android.os.AsyncTask;

import com.example.nantes.mines.myChat.listener.MessageSenderListener;
import com.example.nantes.mines.myChat.model.Message;
import com.example.nantes.mines.myChat.utils.Data;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Asynctask used to send a message
 */
public class MessageSenderAsyncTask extends AsyncTask<Message, Void, Boolean> {
    /** Corresponding listener */
    private MessageSenderListener msl;

    public MessageSenderAsyncTask(MessageSenderListener msl) {
        this.msl = msl;
    }

    protected Boolean doInBackground(Message... message) {
        try {
            // Get the URL
            URL url = new URL(Data.getSendMessageUrl() + Data.getLogin() + "/" + Data.getPass());
            url.toURI();
            // Prepare the JSON containing the message
            ObjectMapper mapper = new ObjectMapper();
            String messageJSON = mapper.writer().writeValueAsString(message[0]);
            // Prepare the request
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(url.toString());
            StringEntity entity = new StringEntity(messageJSON);
            entity.setContentType("application/JSON;charset=UTF-8");
            httpPut.setEntity(entity);
            // Execute the request
            HttpResponse response = httpclient.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }


    protected void onProgressUpdate(Integer... progress) {
    }

    /**
     * Handle the return of the asynctask
     * @param bool true in case of success, false otherwise
     */
    protected void onPostExecute(Boolean bool) {
        if (bool) {
            msl.OnMessageSendSuccess();
        } else {
            msl.OnMessageSendFail();
        }
    }
}