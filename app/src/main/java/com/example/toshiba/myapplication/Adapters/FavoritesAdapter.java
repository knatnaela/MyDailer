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
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.toshiba.myapplication.Database.DataSource.FavoritesRepository;
import com.example.toshiba.myapplication.Database.Local.FavoritesDataSource;
import com.example.toshiba.myapplication.Database.ModelDB.Favorites;
import com.example.toshiba.myapplication.R;
import com.example.toshiba.myapplication.SendGiftActivity;
import com.example.toshiba.myapplication.ViewHolder.FavoritesViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder>{

    final Context context;
    final List<Favorites> favoritesList;

    private Dialog mDialog,Dialog;

    private String callMe;


    public FavoritesAdapter(Context context, List<Favorites> favoritesList) {
        this.context = context;
        this.favoritesList = favoritesList;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.item_fav,parent,false);

        final FavoritesViewHolder favoritesViewHolder = new FavoritesViewHolder(view);

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.dialog_call);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        favoritesViewHolder.fav_contact_item_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView dialog_name = (TextView) mDialog.findViewById(R.id.dialog_name);
                final TextView dialog_phone = (TextView) mDialog.findViewById(R.id.dialog_phone);
                Button dialog_call_me_back = mDialog.findViewById(R.id.btn_callMe);
                Button dialog_messenger_transfer = mDialog.findViewById(R.id.btn_transfer);
                Button dialog_send_gift = mDialog.findViewById(R.id.btn_gift);
                final ImageView dialog_fav = mDialog.findViewById(R.id.btn_fav);

                dialog_name.setText(favoritesList.get(favoritesViewHolder.getAdapterPosition()).name);
                dialog_phone.setText(favoritesList.get(favoritesViewHolder.getAdapterPosition()).number);




                if (Common.favoritesRepository.isFavorite(Long.parseLong(String.valueOf(favoritesList.get(favoritesViewHolder.getAdapterPosition()).id))) == 1)
                    dialog_fav.setImageResource(R.drawable.ic_star_golden_24dp);
                else
                    dialog_fav.setImageResource(R.drawable.ic_star_border_black_24dp);


                dialog_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (Common.favoritesRepository.isFavorite(Long.parseLong(String.valueOf(favoritesList.get(favoritesViewHolder.getAdapterPosition()).id))) !=1)
                        {
                            addOrRemoveToFavorite(favoritesList.get(favoritesViewHolder.getAdapterPosition()),true);
                            dialog_fav.setImageResource(R.drawable.ic_star_golden_24dp);

                            Toast.makeText(context, favoritesList.get(favoritesViewHolder.getAdapterPosition()).name+  context.getString(R.string.was_added_to_fav), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            addOrRemoveToFavorite(favoritesList.get(favoritesViewHolder.getAdapterPosition()),false);
                            dialog_fav.setImageResource(R.drawable.ic_star_border_black_24dp);

                            if (dialog_name.getText().toString().isEmpty() || dialog_name.getText().toString().equals("Unnamed")
                                    || dialog_name.getText().toString().equals("ስም የሌለው"))
                            {
                                callMe = dialog_phone.getText().toString();
                            }
                            else
                            {
                                callMe = dialog_name.getText().toString();
                            }
                            Toast.makeText(context, callMe  +  context.getString(R.string.removed_from_fav), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



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



                dialog_send_gift.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,SendGiftActivity.class);
                        Common.number = favoritesList.get(favoritesViewHolder.getAdapterPosition()).name;
                        Common.name = favoritesList.get(favoritesViewHolder.getAdapterPosition()).number;
                        Common.giftActivity = true;
                        context.startActivity(intent);
                        mDialog.dismiss();

                    }
                });
                mDialog.show();
            }
        });

        return favoritesViewHolder;
    }

    private void addOrRemoveToFavorite(Favorites modelContacts, boolean isAdd) {

        Favorites favorites = new Favorites();
        favorites.id = modelContacts.id;
        favorites.name  = modelContacts.name;
        favorites.number = modelContacts.number;

        if (isAdd)
            Common.favoritesRepository.insertFav(favorites);
        else
            Common.favoritesRepository.delete(favorites);

    }


    private void transferDialog(final TextView dialog_phone, final TextView dialog_name) {



        Dialog = new Dialog(context);
        Dialog.setContentView(R.layout.dialog_transfer);
        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final MaterialEditText editAmount = Dialog.findViewById(R.id.edtAmount);
        Button btnSend = Dialog.findViewById(R.id.dialog_btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editAmount.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.transfer_money);
                    builder.setMessage(R.string.please_enter_birr);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    }).show();
                }
                else {
                    transfer(dialog_phone, editAmount,dialog_name);
                    Dialog.dismiss();
                }
            }
        });

        Dialog.show();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.send_call_me_back);
        builder.setMessage(context.getString(R.string.sure_to_send_call_me)+callMe+" ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                String phoneno = dialog_phone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Uri.parse("tel:" +"*807"+"*"+phoneno)+Uri.encode("#")));
                {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){


                    }
                    context.startActivity(intent);


                }
            }
        }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();

    }

    private void transfer(final TextView dialog_phone, final MaterialEditText editAmount, TextView name) {


        String transfer;
        if (name.getText().toString().isEmpty() || name.getText().toString().equals("Unnamed")
                || name.getText().toString().equals("ስም የሌለው"))
        {
            transfer = dialog_phone.getText().toString();
        }
        else
        {
            transfer = name.getText().toString();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.transfer_money);
        builder.setMessage(context.getString(R.string.want_to_transfer)+ editAmount.getText().toString()+ " Birr to "+ transfer + " ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
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
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                        }
                        context.startActivity(intent);

                    }

                }
                else
                {
                    intent.setData(Uri.parse(Uri.parse("tel:" + "*806*" + phoneno + amount) + Uri.encode("#")));
                    {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        }
                        context.startActivity(intent);

                    }
                }
            }
        }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();


    }
    @Override
    public void onBindViewHolder(@NonNull final FavoritesViewHolder holder, int position) {

        Common.favoritesRepository = FavoritesRepository.getInstance(FavoritesDataSource.getInstance(Common.gebetaRoomDatabase.favoritesDAO()));


        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;


        if (Common.favoritesRepository.isFavorite(Long.parseLong(String.valueOf(favoritesList.get(position).id))) == 1)
        {
            String letter;
            if (favoritesList.get(position).name !=null )
            {

                holder.fav_contact_name.setText(favoritesList.get(position).name);
                letter = String.valueOf(holder.fav_contact_name.getText().charAt(0));
            }
            else
            {

                holder.fav_contact_name.setText(R.string.unnamed);
                letter = String.valueOf(holder.fav_contact_name.getText().charAt(0));
            }

            holder.fav_contact_number.setText(favoritesList.get(position).number);

            holder.btn_call_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    call((String) holder.fav_contact_number.getText());
                }
            });

            holder.btn_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sms((String) holder.fav_contact_number.getText());
                }
            });

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(letter,colorGenerator.getRandomColor());

            holder.img_contact.setImageDrawable(drawable);
        }


    }

    private void sms(String text) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("vnd.android-dir/mms-sms");
        intent.setData(Uri.parse("sms:"+text));
        context.startActivity(intent);

    }

    private void call(String number) {


        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(String.valueOf(Uri.parse("tel:" +number))));
        {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

            }
            context.startActivity(intent);



        }
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }
}
