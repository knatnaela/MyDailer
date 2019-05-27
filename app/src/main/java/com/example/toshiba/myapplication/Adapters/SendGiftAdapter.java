package com.example.toshiba.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.toshiba.myapplication.R;
import com.example.toshiba.myapplication.ViewHolder.SendGiftViewHolder;

public class SendGiftAdapter extends RecyclerView.Adapter<SendGiftViewHolder>{


    Context context;

    public SendGiftAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SendGiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.layout_gift,parent,false);

        return new SendGiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendGiftViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
