package edu.curtin.userapi.api;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.curtin.userapi.R;
import edu.curtin.userapi.helpers.DataAdapter;
import edu.curtin.userapi.userdata.Address;
import edu.curtin.userapi.userdata.Company;
import edu.curtin.userapi.userdata.Geo;
import edu.curtin.userapi.userdata.Post;
import edu.curtin.userapi.userdata.User;

public class TaskHandler implements Runnable{

    Activity uiActivity;
    Context context;
    ProgressBar progressBar;
    ArrayList<User> user = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();

    // Implemented from Lecture 9 sample codes
    // Lecture 09 | Android-Executor-Network-Call/BackgroundTaskHandler.java
    public TaskHandler(Activity uiActivity, Context context, ProgressBar progressBar) {
        this.uiActivity = uiActivity;
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        UserAPIData userData = new UserAPIData(uiActivity);
        Future<String> userDataResponse = executorService.submit(userData);
        String searchResult = waitingForData(userDataResponse);
        user = getEndpoint(searchResult);

        PostsAPIData postData = new PostsAPIData(uiActivity);
        Future<String> postsResponse = executorService.submit(postData);
        String searchResult2 = waitingForPosts(postsResponse);
        posts = getPostEndPoint(searchResult2);

        if (user.size() > 0 ) {
            uiActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RecyclerView rv= uiActivity.findViewById(R.id.recView);
                    rv.setLayoutManager(new LinearLayoutManager(uiActivity));
                    DataAdapter adapter = new DataAdapter(user, posts, uiActivity);
                    rv.setAdapter(adapter);
                }
            });
        }
    }

    private ArrayList<User> getEndpoint(String data){
        ArrayList<User> users = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(data);
            System.out.println(json.length());
            if(json.length()>0){
                for (int i = 0; i < json.length(); i++) {
                    JSONObject object = json.getJSONObject(i);
                    int id = object.getInt("id");
                    String name = object.getString("name");
                    String username = object.getString("username");
                    String email = object.getString("email");
                    double lat = object.getJSONObject("address").getJSONObject("geo").getDouble("lat");
                    double lng = object.getJSONObject("address").getJSONObject("geo").getDouble("lng");
                    Geo geo = new Geo(lat, lng);
                    String street = object.getJSONObject("address").getString("street");
                    String suite = object.getJSONObject("address").getString("suite");
                    String city = object.getJSONObject("address").getString("city");
                    String zipcode = object.getJSONObject("address").getString("zipcode");
                    Address address = new Address(street, suite, city, zipcode, geo);
                    String phone = object.getString("phone");
                    String website = object.getString("website");
                    String companyName = object.getJSONObject("company").getString("name");
                    String catchPhrase = object.getJSONObject("company").getString("catchPhrase");
                    String bs = object.getJSONObject("company").getString("bs");
                    Company company = new Company(companyName, catchPhrase, bs);
                    users.add(new User(id, name, username, email, address, phone, website, company));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    private ArrayList<Post> getPostEndPoint(String data){
        ArrayList<Post> posts = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(data);
            System.out.println(json.length());
            if(json.length()>0){
                for (int i = 0; i < json.length(); i++) {
                    JSONObject object = json.getJSONObject(i);
                    int userId = object.getInt("userId");
                    int id = object.getInt("id");
                    String title = object.getString("title");
                    String body = object.getString("body");
                    posts.add(new Post(userId, id, title, body));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public String waitingForData(Future<String> searchResponsePlaceholder){
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        showToast("Retrieving User Data");
        String searchResponseData =null;
        try {
            searchResponseData = searchResponsePlaceholder.get(6000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
            showError(1, "User Data");
        } catch (InterruptedException e) {
            e.printStackTrace();
            showError(2, "User Data");
        } catch (TimeoutException e) {
            e.printStackTrace();
            showError(3, "User Data");
        }
        showToast("User Data Retrieved");
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return  searchResponseData;
    }

    public String waitingForPosts(Future<String> searchResponsePlaceholder){
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        showToast("Retrieving Posts");
        String searchResponseData =null;
        try {
            searchResponseData = searchResponsePlaceholder.get(6000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
            showError(1, "Post");
        } catch (InterruptedException e) {
            e.printStackTrace();
            showError(2, "Post");
        } catch (TimeoutException e) {
            e.printStackTrace();
            showError(3, "Post");
        }
        showToast("Posts Retrieved");
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return  searchResponseData;
    }

    public void showError(int code, String taskName){
        if(code ==1){
            showToast(taskName+ " Task Execution Exception");
        }
        else if(code ==2){
            showToast(taskName+" Task Interrupted Exception");
        }
        else if(code ==3){
            showToast(taskName+" Task Timeout Exception");
        }
        else{
            showToast(taskName+" Task could not be performed. Restart!!");
        }
    }

    public void showToast(String text){
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(uiActivity,text,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
