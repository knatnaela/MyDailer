package com.example.toshiba.myapplication.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.toshiba.myapplication.Interface.ItemClickListener;
import com.example.toshiba.myapplication.R;
import com.github.abdularis.civ.AvatarImageView;

import org.w3c.dom.Text;

public class CallViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener,View.OnLongClickListener
    {
      public   TextView name,number,date,r;
       public ImageView btn_call;
        public ImageView img_contact;
       public ImageView btn_received,btn_fav;

        public LinearLayout item_call;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public CallViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            btn_call = itemView.findViewById(R.id.btn_call);

            number = itemView.findViewById(R.id.phone_number);
            img_contact = itemView.findViewById(R.id.img_calls);
            date = itemView.findViewById(R.id.Date);
            btn_received = itemView.findViewById(R.id.btn_received);
            item_call =  itemView.findViewById(R.id.call_item_id);
            btn_fav = itemView.findViewById(R.id.fav);

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true );
            return false;
        }
    }
