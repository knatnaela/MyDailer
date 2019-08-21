package com.example.toshiba.myapplication.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toshiba.myapplication.Interface.ItemClickListener;
import com.example.toshiba.myapplication.R;
import com.rey.material.widget.CheckBox;

import java.util.List;

public class DeleteContactsViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
      public final TextView name;
        public final TextView number;
        public TextView r;
        public final ImageView img_contact;
        public List<String> item_call;

      public final CheckBox checkBox;

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public DeleteContactsViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            checkBox = itemView.findViewById(R.id.ckBox);
            number = itemView.findViewById(R.id.phone_number);
            img_contact = itemView.findViewById(R.id.img_calls);
            TextView date = itemView.findViewById(R.id.Date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(false);
        }


    }
