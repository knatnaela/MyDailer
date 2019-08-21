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
import com.example.toshiba.myapplication.Database.ModelDB.Favorites;
import com.example.toshiba.myapplication.DeleteContactsActivity;
import com.example.toshiba.myapplication.Interface.ItemClickListener;
import com.example.toshiba.myapplication.Model.ModelContacts;
import com.example.toshiba.myapplication.R;
import com.example.toshiba.myapplication.SendGiftActivity;
import com.example.toshiba.myapplication.ViewHolder.ContactViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.List;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactViewHolder>{

    private final Context mContext;
    // --Commented out by Inspection (8/6/2019 12:18 PM):private LayoutInflater inflater;
    private final List<ModelContacts> mListContacts;

    private Dialog mDialog,Dialog;

    private String callMe;


    public ContactsRecyclerViewAdapter(Context mContext, List<ModelContacts> callsList) {
        this.mContext = mContext;
        this.mListContacts = callsList;
    }



    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemVew = LayoutInflater.from(mContext).inflate(R.layout.items_contacts,parent,false );
        return new ContactViewHolder(itemVew);
    }


    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, final int position) {

        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;


        String letter;
        if (mListContacts.get(position).getName() !=null )
        {

            holder.contact_name.setText(mListContacts.get(position).getName());
            letter = String.valueOf(holder.contact_name.getText().charAt(0));
        }
        else
        {

            holder.contact_name.setText(R.string.unnamed);
            letter = String.valueOf(holder.contact_name.getText().charAt(0));
        }

        holder.contact_number.setText(mListContacts.get(position).getNumber());

        holder.btn_call_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                call((String) holder.contact_number.getText());
            }
        });

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter,colorGenerator.getRandomColor());

            holder.img_contact.setImageDrawable(drawable);



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(boolean isLongClick) {

               if (isLongClick)
               {
                   Common.currentContacts = mListContacts.get(position);
                   mContext.startActivity(new Intent(mContext, DeleteContactsActivity.class));
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
                   final ImageView dialog_fav = mDialog.findViewById(R.id.btn_fav);

                   dialog_name.setText(mListContacts.get(position).getName());
                   dialog_phone.setText(mListContacts.get(position).getNumber());

                   String id = mListContacts.get(position).getNumber();
                   if (mListContacts.get(position).getNumber().contains(" ") ||
                           mListContacts.get(position).getNumber().contains("-") ||
                           mListContacts.get(position).getNumber().contains("+251") ||
                           mListContacts.get(position).getNumber().contains("+")) {
                       id = mListContacts.get(position).getNumber().trim();

                       id = id.replace("+251", "0");
                       id = id.replaceAll("-", "");
                       id = id.replace("+", "");
                       id = id.replaceAll("\\s+", "");
                   }
                      try{
                          if (Common.favoritesRepository.isFavorite(Long.parseLong(id)) == 1)
                              dialog_fav.setImageResource(R.drawable.ic_star_golden_24dp);
                          else
                              dialog_fav.setImageResource(R.drawable.ic_star_border_black_24dp);

                      }catch (Exception e){
                       e.printStackTrace();
                      }


                   dialog_fav.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {


                           fav(position,dialog_fav,dialog_name,dialog_phone);

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
                           Intent intent = new Intent(mContext,SendGiftActivity.class);
                           Common.number = mListContacts.get(position).getNumber();
                           Common.name = mListContacts.get(position).getName();
                           Common.imageView = mListContacts.get(position).getPhoto();
                           Common.giftActivity = true;
                           mContext.startActivity(intent);
                           mDialog.dismiss();

                       }
                   });
                   mDialog.show();
               }
            }
        });

    }

    private void addOrRemoveToFavorite(ModelContacts modelContacts, boolean isAdd) {

        String id = modelContacts.getNumber();
        if (id.contains(" ") ||
                id.contains("-") ||
                id.contains("+251") ||
                id.contains("+")) {

            id = modelContacts.getNumber().trim();
            id = id.replace("+251", "0");
            id = id.replaceAll("-", "");
            id = id.replace("+", "");
            id = id.replaceAll("\\s+", "");
        }

            Favorites favorites = new Favorites();
            favorites.id = Long.parseLong(id);
            favorites.name = modelContacts.name;
            favorites.number = modelContacts.number;

            if (isAdd)
                Common.favoritesRepository.insertFav(favorites);
            else
                Common.favoritesRepository.delete(favorites);


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
        builder.setTitle(R.string.send_call_me_back);
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
                     //   Toast.makeText(mContext, ""+phone, Toast.LENGTH_SHORT).show();

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
                        //   Toast.makeText(mContext, ""+phone, Toast.LENGTH_SHORT).show();

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


    private void transferDialog(final TextView dialog_phone, final TextView dialog_name) {



        Dialog = new Dialog(mContext);
        Dialog.setContentView(R.layout.dialog_transfer);
        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final MaterialEditText editAmount = Dialog.findViewById(R.id.edtAmount);
        Button btnSend = Dialog.findViewById(R.id.dialog_btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editAmount.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.transfer_money);
                    builder.setMessage(mContext.getString(R.string.please_enter_birr));
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

    private void transfer(final TextView dialog_phone, final MaterialEditText editAmount, TextView dialog_name) {


        String transfer;
        if (dialog_name.getText().toString().isEmpty() || dialog_name.getText().toString().equals("Unnamed")
                || dialog_name.getText().toString().equals("ስም የሌለው"))
        {
            transfer = dialog_phone.getText().toString();
        }
        else
        {
            transfer = dialog_name.getText().toString();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.transfer_money);
        builder.setMessage(mContext.getString(R.string.want_to_transfer)+ editAmount.getText().toString()+ mContext.getString(R.string.birr)+ transfer + " ?");
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

    private void fav(int position, ImageView dialog_fav, TextView dialogPhone, TextView dialog_name) {

        String id = mListContacts.get(position).getNumber();
        if (id.contains(" ") ||
                id.contains("-") ||
                id.contains("+251") ||
                id.contains("+")) {
            id = mListContacts.get(position).getNumber().trim();

            id = id.replace("+251", "");
            id = id.replaceAll("-", "");
            id = id.replace("+", "");
            id = id.replaceAll("\\s+", "");
        }

           try{
               if (Common.favoritesRepository.isFavorite(Long.parseLong(id)) != 1) {
                   addOrRemoveToFavorite(mListContacts.get(position),true);
                   dialog_fav.setImageResource(R.drawable.ic_star_golden_24dp);

                   if (dialog_name.getText().toString().isEmpty() ||dialog_name.getText().toString().equals("Unnamed")
                           || dialog_name.getText().toString().equals("ስም የሌለው"))
                   {
                       callMe = dialogPhone.getText().toString();
                   }
                   else
                   {
                       callMe = dialog_name.getText().toString();
                   }

                   Toast.makeText(mContext, callMe+" "+ mContext.getString(R.string.was_added_to_fav), Toast.LENGTH_SHORT).show();
               }
               else
               {
                   addOrRemoveToFavorite(mListContacts.get(position),false);
                   dialog_fav.setImageResource(R.drawable.ic_star_border_black_24dp);
                   Toast.makeText(mContext, callMe + mContext.getString(R.string.removed_from_fav), Toast.LENGTH_SHORT).show();
               }
           }catch (Exception e){
               e.printStackTrace();
           }
    }

    private void call(String number) {


        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(String.valueOf(Uri.parse("tel:" +number))));
        {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

            }
            mContext.startActivity(intent);



        }
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }
}
