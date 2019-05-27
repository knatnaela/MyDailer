package com.example.toshiba.myapplication.Adapters;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.toshiba.myapplication.Common.Common;

import com.example.toshiba.myapplication.Database.ModelDB.Favorites;
import com.example.toshiba.myapplication.DeleteActivity;
import com.example.toshiba.myapplication.Interface.ItemClickListener;
import com.example.toshiba.myapplication.Model.ModelCalls;
import com.example.toshiba.myapplication.R;
import com.example.toshiba.myapplication.SendGiftActivity;
import com.example.toshiba.myapplication.ViewHolder.CallViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

public class CallsRecyclerViewAdapter extends RecyclerView.Adapter<CallViewHolder> {


    private Context mContext;
    private List<ModelCalls> callsList;



    private Dialog mDialog,myDialog;

    private ImageView btnCall;

    private String letter;

    private String callMe,transfer;

    public CallsRecyclerViewAdapter(Context mContext, List<ModelCalls> callsList) {
        this.mContext = mContext;
        this.callsList = callsList;
    }

    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        initLayoutReferences();
        View itemVew = LayoutInflater.from(mContext).inflate(R.layout.item_calls,parent,false );
        return new CallViewHolder(itemVew);


    }

    private void initLayoutReferences() {


    }


    @Override
    public void onBindViewHolder(@NonNull final CallViewHolder holder, final int position) {

        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

        String id = callsList.get(position).getNumber();
        if (id.contains(" ") ||
                id.contains("-") ||
                id.contains("+251")||
                id.contains("+")) {
            id = id.trim();

            id = id.replace("+251", "0");
            id = id.replaceAll("-", "");
            id = id.replace("+", "");
            id = id.replaceAll("\\s+", "");
        }

        if (Common.favoritesRepository.isFavorite(Long.parseLong(id)) == 1)
            holder.btn_fav.setVisibility(View.VISIBLE);
        else
            holder.btn_fav.setVisibility(View.INVISIBLE);


       if (callsList.get(position).getName() !=null)
        {
            holder.name.setText(callsList.get(position).getName());
            letter = String.valueOf(callsList.get(position).getName().charAt(0));
        }
        else
        {
                holder.name.setText(R.string.unnamed);
                letter = String.valueOf(holder.name.getText().charAt(0));
        }

        holder.number.setText(callsList.get(position).getNumber());
        holder.date.setText(callsList.get(position).getDate());


       holder.btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                call(holder.number.getText().toString());
            }
        });

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter,colorGenerator.getRandomColor());


          holder.img_contact.setImageDrawable(drawable);



       switch (callsList.get(position).getType()) {
            case "1":

                holder.btn_received.setImageResource(R.drawable.ic_call_received_black_24dp);
                break;
            case "2":
                holder.btn_received.setImageResource(R.drawable.ic_call_made_black_24dp);
                break;
            case "3":
                holder.btn_received.setImageResource(R.drawable.ic_phone_missed_black_24dp);
                break;
        }

        //Implement item Click
        holder.setItemClickListener(new ItemClickListener() {

            @Override
            public void onClick(View view, int adapterPosition, boolean isLongClick) {

                if (isLongClick)
                {
                    Common.currentCall = callsList.get(position);
                    mContext.startActivity(new Intent(mContext, DeleteActivity.class));
                }
                else
                {
                    mDialog = new Dialog(mContext);
                    mDialog.setContentView(R.layout.dialog_call);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    final TextView dialog_name = (TextView) mDialog.findViewById(R.id.dialog_name);
                    final TextView dialog_phone = (TextView) mDialog.findViewById(R.id.dialog_phone);
                    Button dialog_call_me_back = mDialog.findViewById(R.id.btn_callMe);
                    Button dialog_messenger_transfer = mDialog.findViewById(R.id.btn_transfer);
                    Button dialog_send_gift = mDialog.findViewById(R.id.btn_gift);
                    ImageView dialog_add_contact = mDialog.findViewById(R.id.btn_add);
                    final ImageView dialog_fav = mDialog.findViewById(R.id.btn_fav);

                    String id = callsList.get(position).getNumber();
                    if (id.contains(" ") ||
                            id.contains("-") ||
                            id.contains("+251") ||
                            id.contains("+")) {
                        id = id.trim();

                        id = id.replace("+251", "0");
                        id = id.replaceAll("-", "");
                        id = id.replace("+", "");
                        id = id.replaceAll("\\s+", "");
                    }

                        if (Common.favoritesRepository.isFavorite(Long.parseLong(id)) == 1)
                            dialog_fav.setImageResource(R.drawable.ic_star_golden_24dp);
                        else
                            dialog_fav.setImageResource(R.drawable.ic_star_border_black_24dp);



                    dialog_fav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            fav(position,dialog_fav,dialog_name,dialog_phone);

                        }
                    });


                    if (callsList.get(position).getName() !=null) {



                        dialog_add_contact.setVisibility(View.INVISIBLE);
                        dialog_name.setText(callsList.get(position).getName());

                    }
                    else {
                        dialog_add_contact.setVisibility(View.VISIBLE);
                        dialog_name.setText(R.string.unnamed);
                    }

                    dialog_phone.setText(callsList.get(position).getNumber());

                    dialog_call_me_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            sendCallmeBack(dialog_phone,dialog_name);
                            mDialog.dismiss();
                        }
                    });

                    dialog_messenger_transfer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            transferDialog(dialog_phone,dialog_name);
                            mDialog.dismiss();
                        }
                    });

                    dialog_add_contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                            String ph = dialog_phone.getText().toString();
                            Intent intent = new Intent(Intent.ACTION_INSERT);
                            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                            intent.putExtra(ContactsContract.Intents.Insert.PHONE, ph);
                            mContext.startActivity(intent);


                        }
                    });

                    dialog_send_gift.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext,SendGiftActivity.class);
                            Common.number = String.valueOf(callsList.get(position).getNumber());
                            Common.name = callsList.get(position).getName();
                            Common.imageView = callsList.get(position).getPhoto();
                            mContext.startActivity(intent);
                            mDialog.dismiss();

                        }
                    });

                    mDialog.show();
                }
            }
        });

    }

    private void call(String adapterPosition) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(String.valueOf(Uri.parse("tel:" +adapterPosition))));
        {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

            }
            mContext.startActivity(intent);

        }

    }

    private void transferDialog(final TextView dialog_phone, final TextView dialog_name) {


            myDialog = new Dialog(mContext);
            myDialog.setContentView(R.layout.dialog_transfer);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            final MaterialEditText editAmount = myDialog.findViewById(R.id.edtAmount);
            Button btnSend = myDialog.findViewById(R.id.dialog_btn_send);

            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (editAmount.getText().toString().isEmpty())
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle(mContext.getString(R.string.transfer_money));
                        builder.setMessage(R.string.please_enter_birr);
                        builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        }).show();
                    }
                    else {
                        transfer(dialog_phone, editAmount,dialog_name);
                        myDialog.dismiss();
                    }

                }
            });

            myDialog.show();
        }

    private void transfer(final TextView dialog_phone, final MaterialEditText editAmount, TextView name) {

        if (name.getText().toString().isEmpty() || name.getText().toString().equals("Unnamed")
                || name.getText().toString().equals("ስም የሌለው"))
        {
            transfer = dialog_phone.getText().toString();
        }
        else
        {
            transfer = name.getText().toString();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.transfer_money);
        builder.setMessage(mContext.getString(R.string.want_to_transfer)+ editAmount.getText().toString()+ mContext.getString(R.string.birr)+ transfer+ " ?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                String phoneno = dialog_phone.getText().toString();
                String amount = editAmount.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                if (phoneno.contains("+251"))
                {
                    String phone  = phoneno.replace("+251", "0");
                    intent.setData(Uri.parse(Uri.parse("tel:" +"*806"+"*"+phone+"*"+amount)+Uri.encode("#")));
                    {
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                        }
                        mContext.startActivity(intent);

                    }

                    Toast.makeText(mContext, ""+phone, Toast.LENGTH_SHORT).show();

                }
                else
                {
                    intent.setData(Uri.parse(Uri.parse("tel:" + "*806*" + phoneno + amount) + Uri.encode("#")));
                    {
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        }
                        mContext.startActivity(intent);

                    }
                }
            }
        }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();


    }


    private void sendCallmeBack(final TextView dialog_phone, TextView dialog_name) {


            if (dialog_name.getText().toString().isEmpty() || dialog_name.getText().toString().equals("Unnamed")
                    || dialog_name.getText().toString().equals("ስም የሌለው"))
            {
                callMe = dialog_phone.getText().toString();
            }
            else
            {
                callMe = dialog_name.getText().toString();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(mContext.getString(R.string.send_call_me_back));
            builder.setMessage(mContext.getString(R.string.sure_to_send_call_me)+callMe+" ?");

            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    String phoneno = dialog_phone.getText().toString();

                    if (phoneno.contains("+251")) {

                        String phone = phoneno.replace("+251", "0");

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*807" + "*" + phone) + Uri.encode("#")));
                        {
                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                            }
                            mContext.startActivity(intent);
                           // Toast.makeText(mContext, ""+phone, Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                    {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*807" + "*" + phoneno) + Uri.encode("#")));
                        {
                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                            }
                             mContext.startActivity(intent);
                           // Toast.makeText(mContext, ""+phoneno, Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();


    }

    private void fav(int position, ImageView dialog_fav, TextView dialogPhone, TextView dialog_name) {

            String id = callsList.get(position).getNumber();
            if (id.contains(" ") ||
                    id.contains("-") ||
                    id.contains("+251")||
                    id.contains("+")) {
                id = callsList.get(position).getNumber().trim();

                id = id.replace("+251", "");
                id = id.replaceAll("-", "");
                id = id.replace("+", "");
                id = id.replaceAll("\\s+", "");
            }



                if (Common.favoritesRepository.isFavorite(Long.parseLong(id)) != 1)
                {

                    checkName(dialog_name.getText().toString(),dialogPhone.getText().toString());
                    addOrRemoveToFavorite(callsList.get(position),true);
                    dialog_fav.setImageResource(R.drawable.ic_star_golden_24dp);



                    Toast.makeText(mContext, callMe+" "+ mContext.getString(R.string.was_added_to_fav), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    checkName(dialog_name.getText().toString(),dialogPhone.getText().toString());
                    addOrRemoveToFavorite(callsList.get(position),false);
                    dialog_fav.setImageResource(R.drawable.ic_star_border_black_24dp);
                    Toast.makeText(mContext, callMe+ " " + mContext.getString(R.string.removed_from_fav), Toast.LENGTH_SHORT).show();
                }


    }

    private void checkName(String phone, String name) {
        if (name.isEmpty() || name.equals("Unnamed")
                || name.equals("ስም የሌለው"))
        {
            callMe = phone;
        }
        else
        {
            callMe = name;
        }
    }

    private void addOrRemoveToFavorite(ModelCalls modelCalls, boolean isAdd) {

            String id = modelCalls.getNumber();
            if (id.contains(" ") ||
                    id.contains("-") ||
                    id.contains("+251") ||
                    id.contains("+")
                    ) {

                id = modelCalls.getNumber().trim();
                id = id.replace("+251", "0");
                id = id.replaceAll("-", "");
                id = id.replace("+", "");
                id = id.replaceAll("\\s+", "");


                Favorites favorites = new Favorites();
                favorites.id = Long.parseLong(id);
                favorites.name = modelCalls.getName();
                favorites.number = modelCalls.getNumber();

                if (isAdd)
                    Common.favoritesRepository.insertFav(favorites);
                else
                    Common.favoritesRepository.delete(favorites);

            }
            else
            {

                Favorites favorites = new Favorites();
                favorites.id = Long.parseLong(modelCalls.getNumber());
                favorites.name = modelCalls.getName();
                favorites.number = modelCalls.getNumber();

                if (isAdd)
                    Common.favoritesRepository.insertFav(favorites);
                else
                    Common.favoritesRepository.delete(favorites);

            }

    }

    @Override
    public int getItemCount() {
        return callsList.size();
    }
}
