package com.example.toshiba.myapplication.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toshiba.myapplication.R;

public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public final TextView fav_contact_name;
    public final TextView fav_contact_number;
    public final ImageView btn_call_contact;
    public final ImageView img_contact;
    public final ImageView btn_message;

    public AdapterView.OnItemClickListener itemClickListener;

    public final LinearLayout fav_contact_item_id;

    public FavoritesViewHolder(View itemView) {
        super(itemView);

        fav_contact_name = itemView.findViewById(R.id.fav_contact_name);
        fav_contact_number = itemView.findViewById(R.id.fav_phone_number);
        btn_call_contact = itemView.findViewById(R.id.btn_call);
        btn_message = itemView.findViewById(R.id.btn_sms);
        fav_contact_item_id = (LinearLayout) itemView.findViewById(R.id.fav_contact_item_id);
        LinearLayout btn_call_me_back = itemView.findViewById(R.id.btn_callMe);
        LinearLayout btn_transfer = itemView.findViewById(R.id.btn_transfer);
        LinearLayout btn_gift = itemView.findViewById(R.id.btn_gift);
        img_contact = itemView.findViewById(R.id.img_fav_contact);
        LinearLayout btnSend = itemView.findViewById(R.id.dialog_btn_send);


        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}
