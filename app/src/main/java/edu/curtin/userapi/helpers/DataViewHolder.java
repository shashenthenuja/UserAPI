package edu.curtin.userapi.helpers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.curtin.userapi.R;

public class DataViewHolder extends RecyclerView.ViewHolder{

    public TextView id;
    public TextView name;
    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.id);
        name = itemView.findViewById(R.id.name);
    }
}
