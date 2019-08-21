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
import com.example.toshiba.myapplication.Model.ModelContacts;
import com.example.toshiba.myapplication.R;
import com.example.toshiba.myapplication.ViewHolder.DeleteContactsViewHolder;

import java.util.List;

public class DeleteContactsRecyclerViewAdapter extends RecyclerView.Adapter<DeleteContactsViewHolder> {


    private final Context mContext;
    private final List<ModelContacts> contactsList;

    // --Commented out by Insp// --Commented out by I// --Commented out by Inspection (8/6/2019 12:18 PM):nspection (8/6/2// --Commented out by Inspection (8/6/2019 12:18 PM):019 12:18 PM):ection (8/6/2019 12:18 PM):CheckBox checkBox;



    // --Commented out by I// --Commented out by Inspection (8/6/2019 12:18 PM):nspection (8/6/2019 12:18 PM):private Dialog mDialog,myDialog;

    // --Commented out by Inspection// --Commented out by // --Commented out by Inspection (8/6/2019 12:18 PM):Inspection (8/6/2019 12:18 PM): (8/6/2019 12:18 PM):private ImageView btnCall;

    // --Commented out by Inspection (8/6/2019 12:18 PM):private String callMe,transfer;

    public DeleteContactsRecyclerViewAdapter(Context mContext, List<ModelContacts> contactsList) {
        this.mContext = mContext;
        this.contactsList = contactsList;

    }

    @NonNull
    @Override
    public DeleteContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        View itemVew = LayoutInflater.from(mContext).inflate(R.layout.delete_contacts_layout,parent,false );
        return new DeleteContactsViewHolder(itemVew);
    }



    @Override
    public void onBindViewHolder(@NonNull final DeleteContactsViewHolder holder, final int position) {

        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

        if(Common.currentContacts.getNumber().equals(contactsList.get(position).getNumber()))
        {

            holder.checkBox.setCheckedImmediately(true);
            Common.numberAdded.add(contactsList.get(position).getId());
        }
        else
        {
            holder.checkBox.setCheckedImmediately(false);
            Common.numberAdded.remove(contactsList.get(position).getId());
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked)
                {
                    Common.numberAdded.add(contactsList.get(position).getId());
                }
                else {

                    Common.numberAdded.remove(contactsList.get(position).getId());
                }


            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(boolean isLongClick) {


            }
        });


        String letter;
        if (contactsList.get(position).getName() != null) {
            holder.name.setText(contactsList.get(position).getName());
            letter = String.valueOf(contactsList.get(position).getName().charAt(0));

        } else {
            holder.name.setText(R.string.unnamed);
            letter = String.valueOf(holder.name.getText().charAt(0));
        }

        holder.number.setText(contactsList.get(position).getNumber());

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, colorGenerator.getRandomColor());


        holder.img_contact.setImageDrawable(drawable);
    }


    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
