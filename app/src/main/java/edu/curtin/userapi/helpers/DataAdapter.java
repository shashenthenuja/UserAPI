package edu.curtin.userapi.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.curtin.userapi.R;
import edu.curtin.userapi.UserDetails;
import edu.curtin.userapi.userdata.Post;
import edu.curtin.userapi.userdata.User;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();
    Activity uiActivity;

    public DataAdapter(ArrayList<User> users, ArrayList<Post> posts, Activity uiActivity) {
        this.users = users;
        this.posts = posts;
        this.uiActivity = uiActivity;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_layout,parent,false);
        DataViewHolder myDataVHolder = new DataViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(uiActivity, UserDetails.class);
                intent.putExtra("list", users);
                intent.putExtra("posts", posts);
                intent.putExtra("id", Integer.parseInt(myDataVHolder.id.getText().toString()));
                uiActivity.startActivity(intent);
            }
        });
        return myDataVHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.id.setText(String.valueOf(users.get(position).getId()));
        holder.name.setText(users.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
