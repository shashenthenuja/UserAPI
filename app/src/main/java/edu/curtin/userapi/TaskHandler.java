package edu.curtin.userapi;

import android.app.Activity;
import android.content.Context;
import android.view.View;
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

import edu.curtin.userapi.userdata.Address;
import edu.curtin.userapi.userdata.Company;
import edu.curtin.userapi.userdata.Geo;
import edu.curtin.userapi.userdata.User;

public class TaskHandler implements Runnable{

    Activity uiActivity;
    Context context;
    ArrayList<User> user = new ArrayList<>();

    public TaskHandler(Activity uiActivity, Context context) {
        this.uiActivity = uiActivity;
        this.context = context;
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        UserAPIData searchTask = new UserAPIData(uiActivity);
        Future<String> searchResponsePlaceholder = executorService.submit(searchTask);
        String searchResult = waitingForSearch(searchResponsePlaceholder);
        user = getEndpoint(searchResult);
        if (user.size() > 0 ) {
            uiActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RecyclerView rv= uiActivity.findViewById(R.id.recView);
                    rv.setLayoutManager(new LinearLayoutManager(uiActivity));
                    DataAdapter adapter = new DataAdapter(user);
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

    public String waitingForSearch(Future<String> searchResponsePlaceholder){
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        showToast("Search Starts");
        String searchResponseData =null;
        try {
            searchResponseData = searchResponsePlaceholder.get(6000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
            showError(1, "Search");
        } catch (InterruptedException e) {
            e.printStackTrace();
            showError(2, "Search");
        } catch (TimeoutException e) {
            e.printStackTrace();
            showError(3, "Search");
        }
        showToast("Search Ends");
        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

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

    public ArrayList<User> getUser() {
        return user;
    }
}