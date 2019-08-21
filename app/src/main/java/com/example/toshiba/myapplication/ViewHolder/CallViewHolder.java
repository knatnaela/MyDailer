package com.example.toshiba.myapplication.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toshiba.myapplication.Interface.ItemClickListener;
import com.example.toshiba.myapplication.R;

public class CallViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener,View.OnLongClickListener
    {
      public final TextView name;
        public final TextView number;
        public final TextView date;
        public TextView r;
       public final ImageView btn_call;
        public final ImageView img_contact;
       public final ImageView btn_received;
        public final ImageView btn_fav;

        private final LinearLayout item_call;

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public CallViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            btn_call = itemView.findViewById(R.id.btn_call);

            number = itemView.findViewById(R.id.phone_number);img_contact = itemView.findViewById(R.id.img_calls);
            date = itemView.findViewById(R.id.Date);
            btn_received = itemView.findViewById(R.id.btn_received);
            item_call =  itemView.findViewById(R.id.call_item_id);
            btn_fav = itemView.findViewById(R.id.fav);

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(true );
            return false;
        }
    }
