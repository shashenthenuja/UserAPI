package edu.curtin.userapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.curtin.userapi.userdata.Post;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>{

    ArrayList<Post> posts = new ArrayList<>();
    public PostAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.posts_layout,parent,false);
        PostViewHolder myDataVHolder = new PostViewHolder(view);
        return myDataVHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        int index = position + 1;
        holder.postId.setText(String.valueOf(index));
        holder.title.setText(posts.get(position).getTitle());
        holder.body.setText(posts.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
