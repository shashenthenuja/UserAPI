package edu.curtin.userapi;

import android.app.Activity;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

    // Implemented from Lecture 8 sample codes
    // Lecture 08 | Android-Executor-Network-Call/RemoteUtilities.java
    public class RemoteUtils {
        public static RemoteUtils remoteUtils = null;
        private Activity uiActivity;

        public RemoteUtils(Activity uiActivity) {
            this.uiActivity = uiActivity;
        }

        public void setUiActivity(Activity uiActivity){
            this.uiActivity = uiActivity;
        }

        public static RemoteUtils getInstance(Activity uiActivity){
            if(remoteUtils == null){
                remoteUtils = new RemoteUtils(uiActivity);
            }
            remoteUtils.setUiActivity(uiActivity);
            return remoteUtils;
        }

        public HttpURLConnection openConnection(String urlString)  {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(conn == null){
                uiActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(uiActivity,"Check Internet",Toast.LENGTH_LONG).show();
                    }
                });

            }
            return conn;
        }

        public boolean isConnectionOkay(HttpURLConnection conn){
            try {
                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                uiActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(uiActivity,"Problem with API Endpoint",Toast.LENGTH_LONG).show();
                    }
                });
            }
            return false;
        }

        public String getResponseString(HttpURLConnection conn){
            String data = null;
            try {
                InputStream inputStream = conn.getInputStream();
                byte[] byteData = IOUtils.toByteArray(inputStream);
                data = new String(byteData, StandardCharsets.UTF_8);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }
}
