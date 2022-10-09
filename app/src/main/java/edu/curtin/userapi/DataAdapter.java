package edu.curtin.userapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.curtin.userapi.userdata.User;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {

    ArrayList<User> users = new ArrayList<>();

    public DataAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_layout,parent,false);
        DataViewHolder myDataVHolder = new DataViewHolder(view);
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
