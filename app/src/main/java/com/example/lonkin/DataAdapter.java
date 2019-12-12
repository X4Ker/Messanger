package com.example.lonkin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<ViewHolder> {

    ArrayList<String> messages;
    ArrayList<String> _usr;

    LayoutInflater inflater;

    public DataAdapter(Context context, ArrayList<String> messages, ArrayList<String> _usr) {
        this._usr = _usr;
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_message, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String msg = messages.get(position);
        String usr = _usr.get(position);
        holder.messageUser.setText(usr);
        holder.message.setText(msg);

    }

    @Override
    public int getItemCount() {

        return messages.size();
    }
}
