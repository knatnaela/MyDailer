package com.example.toshiba.myapplication.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toshiba.myapplication.R;

public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView fav_contact_name,fav_contact_number;
    public ImageView btn_call_contact,img_contact,btn_message;

    public LinearLayout btn_call_me_back,btn_transfer,btn_gift,btnSend;

    public AdapterView.OnItemClickListener itemClickListener;

    public LinearLayout fav_contact_item_id;

    public FavoritesViewHolder(View itemView) {
        super(itemView);

        fav_contact_name = itemView.findViewById(R.id.fav_contact_name);
        fav_contact_number = itemView.findViewById(R.id.fav_phone_number);
        btn_call_contact = itemView.findViewById(R.id.btn_call);
        btn_message = itemView.findViewById(R.id.btn_sms);
        fav_contact_item_id = (LinearLayout) itemView.findViewById(R.id.fav_contact_item_id);
        btn_call_me_back = itemView.findViewById(R.id.btn_callMe);
        btn_transfer = itemView.findViewById(R.id.btn_transfer);
        btn_gift = itemView.findViewById(R.id.btn_gift);
        img_contact = itemView.findViewById(R.id.img_fav_contact);
        btnSend = itemView.findViewById(R.id.dialog_btn_send);


        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}
