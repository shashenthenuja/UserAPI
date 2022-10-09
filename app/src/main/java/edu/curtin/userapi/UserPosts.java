package edu.curtin.userapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import edu.curtin.userapi.userdata.Post;

public class UserPosts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        ArrayList<Post> posts = (ArrayList<Post>) getIntent().getSerializableExtra("posts");
        int idVal = getIntent().getIntExtra("id", 0);

        RecyclerView rv= this.findViewById(R.id.recPosts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        PostAdapter adapter = new PostAdapter(posts);
        rv.setAdapter(adapter);
    }
}