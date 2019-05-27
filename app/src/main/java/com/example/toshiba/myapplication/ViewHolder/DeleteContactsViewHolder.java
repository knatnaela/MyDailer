package com.example.toshiba.myapplication.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toshiba.myapplication.Interface.ItemClickListener;
import com.example.toshiba.myapplication.R;
import com.rey.material.widget.CheckBox;

import java.util.List;

public class DeleteContactsViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
      public   TextView name;
        public TextView number;
        public TextView date;
        public TextView r;
        public ImageView img_contact;

        public List<String> item_call;

      public   CheckBox checkBox;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public DeleteContactsViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            checkBox = itemView.findViewById(R.id.ckBox);
            number = itemView.findViewById(R.id.phone_number);
            img_contact = itemView.findViewById(R.id.img_calls);
            date = itemView.findViewById(R.id.Date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }


    }
