package edu.curtin.userapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.curtin.userapi.userdata.Post;
import edu.curtin.userapi.userdata.User;

public class UserDetails extends AppCompatActivity {

    TextView id;
    TextView name;
    TextView userName;
    TextView email;
    TextView street;
    TextView suite;
    TextView city;
    TextView zipcode;
    TextView lat;
    TextView lng;
    TextView phone;
    TextView website;
    TextView compName;
    TextView cp;
    TextView bs;
    Button postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        id = findViewById(R.id.userId);
        name = findViewById(R.id.userName);
        userName = findViewById(R.id.userUserName);
        email = findViewById(R.id.userEmail);
        street = findViewById(R.id.userStreet);
        suite = findViewById(R.id.userSuite);
        city = findViewById(R.id.userCity);
        zipcode = findViewById(R.id.userZipCode);
        lat = findViewById(R.id.userLat);
        lng = findViewById(R.id.userLong);
        phone = findViewById(R.id.userPhone);
        website = findViewById(R.id.userWebsite);
        compName = findViewById(R.id.userCompName);
        cp = findViewById(R.id.userCompCp);
        bs = findViewById(R.id.userCompBs);
        postBtn = findViewById(R.id.userPosts);

        ArrayList<User> users = (ArrayList<User>) getIntent().getSerializableExtra("list");
        ArrayList<Post> posts = (ArrayList<Post>) getIntent().getSerializableExtra("posts");
        int idVal = getIntent().getIntExtra("id", 0);

        if (users.size() > 0) {
            for (User u:users
            ) {
                if (u.getId() == idVal) {
                    id.setText(String.valueOf(u.getId()));
                    name.setText("Name : " + u.getName());
                    userName.setText("User Name : " + u.getUsername());
                    email.setText("Email : " + u.getEmail());
                    street.setText("Street : " + u.getAddress().getStreet());
                    suite.setText("Suite : " + u.getAddress().getSuite());
                    city.setText("City : " +  u.getAddress().getCity());
                    zipcode.setText("Zip Code : " + u.getAddress().getZipcode());
                    lat.setText("Latitude : " + String.valueOf(u.getAddress().getGeo().getLatitude()));
                    lng.setText("Longitude : " + String.valueOf(u.getAddress().getGeo().getLongitude()));
                    phone.setText("Phone : " + u.getPhone());
                    website.setText("Website : " + u.getWebsite());
                    compName.setText("Company : " + u.getCompany().getName());
                    cp.setText("CP : " + u.getCompany().getCatchPhrase());
                    bs.setText("BS : " + u.getCompany().getBs());
                    break;
                }
            }
        }

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Post> userPosts = new ArrayList<>();
                if (posts.size() > 0) {
                    for (Post p:posts
                         ) {
                        if (p.getUserId() == idVal) {
                            userPosts.add(p);
                        }
                    }
                }
                if (userPosts.size() > 0) {
                    Intent intent = new Intent(UserDetails.this, UserPosts.class);
                    intent.putExtra("posts", userPosts);
                    UserDetails.this.startActivity(intent);
                }else{
                    Toast.makeText(UserDetails.this, "No Posts From Current User!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}