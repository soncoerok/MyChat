package com.example.nantes.mines.myChat.task;

import android.os.AsyncTask;

import com.example.nantes.mines.myChat.listener.LoginListener;
import com.example.nantes.mines.myChat.utils.Data;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Asynctask used for login the user
 */
public class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {
    /** Corresponding listener */
    private LoginListener ll;

    public LoginAsyncTask(LoginListener ll) {
        this.ll = ll;
    }

    /**
     *  Action running in background
     * @param params    two params, the login and the password
     * @return  true if the user is user is registered, false otherwise
     */
    protected Boolean doInBackground(String... params) {
        try {
            // Get the URL
            URL url = new URL(Data.getConnectUrl() + params[0] + "/" + params[1]);
            url.toURI();
            // Prepare the request
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url.toString());
            httpGet.setHeader("Content-Type", "application/JSON");
            // Execute the request
            HttpResponse response = httpclient.execute(httpGet);
            return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
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
     * @param bool  true in case of success, false otherwise
     */
    protected void onPostExecute(Boolean bool) {
        if (bool) {
            ll.OnLoginSuccess();
        } else {
            ll.OnLoginFail();
        }
    }
}

