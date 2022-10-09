package edu.curtin.userapi;

import android.app.Activity;

import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class PostsAPIData implements Callable<String> {

    private RemoteUtils remoteUtils;
    private String userUrl = "https://jsonplaceholder.typicode.com/posts";
    private Activity uiActivity;

    public PostsAPIData(Activity uiActivity) {
        this.uiActivity = uiActivity;
        remoteUtils = RemoteUtils.getInstance(uiActivity);
    }

    @Override
    public String call() throws Exception {
        String response=null;
        String userEndPoint = userUrl;
        HttpURLConnection connection = remoteUtils.openConnection(userEndPoint);
        if(connection!=null){
            if(remoteUtils.isConnectionOkay(connection)==true){
                response = remoteUtils.getResponseString(connection);
                connection.disconnect();
                try {
                    Thread.sleep(3000);
                }
                catch (Exception e){

                }
            }
        }
        return response;
    }
}
