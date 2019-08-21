package com.example.toshiba.myapplication.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toshiba.myapplication.Interface.ItemClickListener;
import com.example.toshiba.myapplication.R;

public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        public final TextView contact_name;
    public final TextView contact_number;
       public final ImageView btn_call_contact;
    public final ImageView img_contact;
    private final ImageView btn_fav;
    private final LinearLayout btn_call_me_back;
    private final LinearLayout btn_transfer;
    private final LinearLayout btn_gift;
    private final LinearLayout btnSend;


    private final LinearLayout item_contact;


    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

  public ContactViewHolder(View itemView) {
        super(itemView);

        contact_name = itemView.findViewById(R.id.contact_name);
        contact_number = itemView.findViewById(R.id.phone_number);
        btn_call_contact = itemView.findViewById(R.id.btn_call);
       item_contact = (LinearLayout) itemView.findViewById(R.id.contact_item_id);
       btn_call_me_back = itemView.findViewById(R.id.btn_callMe);
      btn_transfer = itemView.findViewById(R.id.btn_transfer);
      btn_gift = itemView.findViewById(R.id.btn_gift);
      img_contact = itemView.findViewById(R.id.img_contact);
      btnSend = itemView.findViewById(R.id.dialog_btn_send);
      btn_fav = itemView.findViewById(R.id.fav);

        itemView.setOnClickListener(this);

      itemView.setOnLongClickListener(this);

        }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(false);
        }

    @Override
    public boolean onLongClick(View view)
    {
        itemClickListener.onClick(true );
        return false;
    }
}
