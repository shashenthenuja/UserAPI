package edu.curtin.userapi.helpers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.curtin.userapi.R;

public class PostViewHolder extends RecyclerView.ViewHolder{

    public TextView postId;
    public TextView title;
    public TextView body;
    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        postId = itemView.findViewById(R.id.postId);
        title = itemView.findViewById(R.id.postTitle);
        body = itemView.findViewById(R.id.postBody);
    }
}
