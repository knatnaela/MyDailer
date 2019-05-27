package com.example.toshiba.myapplication.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.toshiba.myapplication.R;

import br.com.bloder.magic.view.MagicButton;

public class SendGiftViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

   public MagicButton btnSms,btnInternet,btnVoice;

    public SendGiftViewHolder(View itemView) {
        super(itemView);
        btnInternet = itemView.findViewById(R.id.magic_button_internet);
        btnSms = itemView.findViewById(R.id.magic_button_SMS);
        btnVoice = itemView.findViewById(R.id.magic_button_voice);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
