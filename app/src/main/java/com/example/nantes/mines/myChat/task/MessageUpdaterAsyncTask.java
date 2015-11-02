package com.example.nantes.mines.myChat.task;

import android.os.AsyncTask;

import com.example.nantes.mines.myChat.listener.MessageUpdaterListener;
import com.example.nantes.mines.myChat.model.Message;
import com.example.nantes.mines.myChat.utils.Utils;
import com.example.nantes.mines.myChat.utils.Data;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Asynctask used to get all the message
 */
public class MessageUpdaterAsyncTask extends AsyncTask<String, Void, List<Message>> {
    /** Corresponding listener */
    private MessageUpdaterListener mul;

    public MessageUpdaterAsyncTask(MessageUpdaterListener mul) {
        this.mul = mul;
    }

    protected List<Message> doInBackground(String... params) {
        try {
            // Get the URL
            URL url = new URL(Data.getGetMessageUrl() + params[0] + "/" + params[1]);
            url.toURI();
            // Prepare the request
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url.toString());
            httpGet.setHeader("Content-Type", "application/JSON");
            // Execute the request
            String result = "";
            HttpResponse response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // Map the JSON response to a string
                InputStream content = response.getEntity().getContent();
                if (content != null) {
                    result = Utils.convertInputStreamToString(content);
                }
                // Convert the string to messages & return the result
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
                Message[] arrayString = mapper.readValue(result, Message[].class);
                return new ArrayList<Message>(Arrays.asList(arrayString));
            }else {
                return new ArrayList<Message>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<Message>();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new ArrayList<Message>();
        }
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    /**
     * Handle the return of the asynctask
     * @param messageList   the list of messages get by the request to the server
     */
    protected void onPostExecute(List<Message> messageList) {
        if (messageList.size() > 0) {
            mul.OnMessageUpdateSuccess(messageList);
        } else {
            mul.OnMessageUpdateFail();
        }
    }
}