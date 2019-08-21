package com.example.toshiba.myapplication.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.toshiba.myapplication.Common.Common;
import com.example.toshiba.myapplication.Interface.ItemClickListener;
import com.example.toshiba.myapplication.Model.ModelCalls;
import com.example.toshiba.myapplication.R;
import com.example.toshiba.myapplication.ViewHolder.DeleteViewHolder;

import java.util.List;

public class DeleteRecyclerViewAdapter extends RecyclerView.Adapter<DeleteViewHolder> {


    private final Context mContext;
    private final List<ModelCalls> callsList;

    public DeleteRecyclerViewAdapter(Context mContext, List<ModelCalls> callsList) {
        this.mContext = mContext;
        this.callsList = callsList;

    }

    @NonNull
    @Override
    public DeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        View itemVew = LayoutInflater.from(mContext).inflate(R.layout.delete_layout,parent,false );
        return new DeleteViewHolder(itemVew);
    }



    @Override
    public void onBindViewHolder(@NonNull final DeleteViewHolder holder, final int position) {

        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

        if(Common.currentCall.getNumber().equals(callsList.get(position).getNumber()))
        {

            holder.checkBox.setCheckedImmediately(true);
            Common.numberAdded.add(callsList.get(position).getNumber());
        }
        else
        {
            holder.checkBox.setCheckedImmediately(false);
            Common.numberAdded.remove(callsList.get(position).getNumber());
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked)
                {
                    Common.numberAdded.add(holder.number.getText().toString());
                }
                else {

                    Common.numberAdded.remove(holder.number.getText().toString());
                }


            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(boolean isLongClick) {

            }
        });


        String letter;
        if (callsList.get(position).getName() != null) {
            holder.name.setText(callsList.get(position).getName());
            letter = String.valueOf(callsList.get(position).getName().charAt(0));

        } else {
            holder.name.setText(R.string.unnamed);
            letter = String.valueOf(holder.name.getText().charAt(0));
        }

        holder.number.setText(callsList.get(position).getNumber());

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, colorGenerator.getRandomColor());


        holder.img_contact.setImageDrawable(drawable);
    }


    @Override
    public int getItemCount() {
        return callsList.size();
    }
}
