package com.example.nantes.mines.myChat.task;

import android.os.AsyncTask;

import com.example.nantes.mines.myChat.listener.RegisterListener;
import com.example.nantes.mines.myChat.model.RegisterBodyResponse;
import com.example.nantes.mines.myChat.model.RegisterStatus;
import com.example.nantes.mines.myChat.utils.Utils;
import com.example.nantes.mines.myChat.utils.Data;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Asynctask used to register an user
 */
public class RegisterAsyncTask extends AsyncTask<String, Void, RegisterStatus> {
    /** Corresponding listener */
    private RegisterListener rl;

    public RegisterAsyncTask(RegisterListener rl) {
        this.rl = rl;
    }

    protected RegisterStatus doInBackground(String... params) {
        try {
            // Get the URL
            URL url = new URL(Data.getRegisterUrl() + params[0] + "/" + params[1]);
            url.toURI();
            // Prepare the request
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url.toString());
            httpPost.setHeader("Content-Type", "application/JSON");
            // Execute the request
            String result = "";
            HttpResponse response = httpclient.execute(httpPost);
            // Map the JSON response to a string
            InputStream content = response.getEntity().getContent();
            if (content != null) {
                result = Utils.convertInputStreamToString(content);
            }
            // Convert the string to a RegisterBodyResponse & return the result
            ObjectMapper mapper = new ObjectMapper();
            RegisterBodyResponse bodyResponse = mapper.readValue(result, RegisterBodyResponse.class);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return new RegisterStatus(true, bodyResponse.getMessage());
            } else {
                return new RegisterStatus(false, bodyResponse.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new RegisterStatus(false, "Credential error");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new RegisterStatus(false, "Credential error");
        }
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    /**
     * Handle the return of the asynctask
     * @param registerStatus    the result as a Registerstatus object.
     */
    protected void onPostExecute(RegisterStatus registerStatus) {
        if (registerStatus.isRegistered()) {
            rl.OnRegisterSuccess();
        } else {
            rl.OnRegisterFail(registerStatus.getMessage());
        }
    }
}