package com.example.toshiba.myapplication.Adapters;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toshiba.myapplication.Common.Common;
import com.example.toshiba.myapplication.Model.Packages;
import com.example.toshiba.myapplication.R;
import com.example.toshiba.myapplication.ViewHolder.VoiceViewHolder;

import java.util.List;

public class SendGiftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    public static final int VIEWTYPE_GROUP = 0;
    public static final int VIEWTYPE_GIFT = 1;
    private final List<Packages> packagesList;
    private final Context context;

    
    private String setUSSID = "";

    public SendGiftAdapter(List<Packages> packagesList, Context context) {
        this.packagesList = packagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == VIEWTYPE_GROUP){

            ViewGroup group = (ViewGroup)inflater.inflate(R.layout.group_layout,viewGroup,false);
            return new GroupViewHolder(group);
        }
        else if (viewType == VIEWTYPE_GIFT){

            ViewGroup group = (ViewGroup)inflater.inflate(R.layout.internet_gift_layout,viewGroup,false);
            return new VoiceViewHolder(group);
        }
        else{
            ViewGroup group = (ViewGroup)inflater.inflate(R.layout.group_layout,viewGroup,false);
            return new GroupViewHolder(group);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return packagesList.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {

        if (viewHolder instanceof GroupViewHolder){
            GroupViewHolder groupViewHolder = (GroupViewHolder)viewHolder;
            groupViewHolder.text_group_title.setText(packagesList.get(i).getDuration());
        }
        else if (viewHolder instanceof VoiceViewHolder){
            ((VoiceViewHolder)viewHolder).price.setText(packagesList.get(i).getCost());
            ((VoiceViewHolder)viewHolder).internet.setText(packagesList.get(i).getInternet());
            ((VoiceViewHolder)viewHolder).sms.setText(packagesList.get(i).getSms());
            ((VoiceViewHolder)viewHolder).voice.setText(packagesList.get(i).getVoice());
            ((VoiceViewHolder)viewHolder).addition.setText(packagesList.get(i).getAdditional());
            ((VoiceViewHolder)viewHolder).duration.setText(packagesList.get(i).getDuration());

            ((VoiceViewHolder)viewHolder).send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder  = new AlertDialog.Builder(context);
                    builder.setTitle("One More Step!");
                    builder.setMessage("Are you sure to continue?");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (Common.giftActivity)
                            {
                                setUSSID = "2";
                                switch (Common.currentTab) {
                                    case "voice":
                                        switch (packagesList.get(viewHolder.getAdapterPosition()).getDuration()) {
                                            case "Night":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3.49 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*"+ setUSSID +"*1*1*1" + Common.number +"*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "4.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*"+ setUSSID +"*1*1*2" + Common.number+ "*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "6.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*" + setUSSID +"*1*1*3" + Common.number+ "*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "9.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*1*1*4 " + Common.number+ " *1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Daily":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1* 2*1*2*1" + Common.number+"*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "5 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*" +setUSSID +"*1*2*2"+ Common.number+"*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "10 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*"+ setUSSID +"*1*2*3" + Common.number+"*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;

                                            case "Monthly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "40 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4*1" + Common.number+"*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "60 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4*2" + Common.number+"*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "100 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4*3" + Common.number+"*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "140 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.parse("*4" + Common.number+"*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "150 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.parse("*5" + Common.number+"*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "200 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.parse("*6" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "250 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.parse("*7" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "270 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.parse("*8" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "300 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.parse("*9" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "350 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+ Uri.parse("*10*1"+ Common.number)+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "400 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+ Uri.parse("*11"+ Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "450 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+ Uri.parse("*12" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "500 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+Uri.encode("#")+ Uri.parse("*13"+ Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "540 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+Uri.encode("#")+ Uri.parse("*14" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "600 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+Uri.encode("#")+ Uri.parse("*15"+ Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "1350 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+Uri.encode("#")+ Uri.parse("*16" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Weekly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "15 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*3*1" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "20 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*3*2" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "10 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*2*3" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                        }
                                        break;
                                    case "internet":
                                        switch (packagesList.get(viewHolder.getAdapterPosition()).getDuration()) {
                                            case "Night":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*2*4*1" + Common.number+ "*1") + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }

                                                        break;
                                                    }
                                                    case "5 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*2*4*2"+ Common.number+ "*1") + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "7 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*2*4*3" + Common.number+ "*1") + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }

                                                }
                                                break;
                                            case "Daily":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*1" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "5 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*2" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "10 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*3" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "15 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*4" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "35 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*5" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Monthly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "55 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3*1" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "100 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3*2" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "190 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3*3" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "350 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3*4" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "600 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3")+Uri.encode("#")+Uri.parse("*5" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }

                                                        break;
                                                    }
                                                    case "700 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3")+Uri.encode("#")+Uri.parse("*6" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "1,300 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3")+Uri.encode("#")+Uri.parse("*7" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "1,800 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3")+Uri.encode("#")+Uri.parse("*8" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "4,900 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3")+Uri.encode("#")+Uri.parse("*9" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Weekly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "27 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*2*1*1"+ Common.number)+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "50 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*2*2*1"+ Common.number)+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "60 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*2*3*1"+ Common.number)+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "80 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*2*4*1"+ Common.number)+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Weekend":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "35 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*2*5*1*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }

                                                        break;
                                                    }
                                                    case "60 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*2*5*2*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "110 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*2*5*3*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                        }
                                        break;
                                    case "sms":
                                        switch (packagesList.get(viewHolder.getAdapterPosition()).getDuration()) {
                                            case "Night":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3.49 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*1*1*1"+ Common.number+ "*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "4.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*1*1*2" + Common.number+ "*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "6.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*1*1*3" + Common.number+ "*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "9.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*1*1*4" + Common.number+ "*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Daily":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "2 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*1*1" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "3 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*1*2" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "5 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*1*3" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Monthly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "30 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*3*1" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);
                                                        }

                                                        break;
                                                    }
                                                    case "50 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*3*1" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);
                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Weekly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "10 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*2*1" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);
                                                        }
                                                        break;
                                                    }
                                                    case "15 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*2*2" + Common.number+ "*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);
                                                        }
                                                        break;
                                                    }

                                                }
                                                break;
                                        }
                                        break;
                                }
                            }
                            else if (!Common.giftActivity)
                            {
                                setUSSID = "1";
                                switch (Common.currentTab) {
                                    case "voice":
                                        switch (packagesList.get(viewHolder.getAdapterPosition()).getDuration()) {
                                            case "Night":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3.49 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*"+ setUSSID +"*1*1*1*1") + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);
                                                        }
                                                        break;
                                                    }
                                                    case "4.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*"+ setUSSID +"*1*1*2*1") + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "6.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*" + setUSSID +"*1*1*3*1") + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "9.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*1*1*4*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Daily":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*2*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "5 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*" +setUSSID +"*1*2*2*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "10 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*"+ setUSSID +"*1*2*3*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;

                                            case "Monthly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "40 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "60 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4*2*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "100 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4*3*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "140 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.parse("*4*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "150 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.parse("*5*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "200 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.parse("*6*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "250 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.parse("*7*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "270 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.parse("*8*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "300 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.parse("*9*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "350 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+ Uri.parse("*10*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "400 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+ Uri.parse("*11*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "450 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+ Uri.parse("*12*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "500 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+Uri.encode("#")+ Uri.parse("*13*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "540 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+Uri.encode("#")+ Uri.parse("*14*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "600 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+Uri.encode("#")+ Uri.parse("*15*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "1350 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                                                +Uri.encode("#")+Uri.encode("#")+Uri.encode("#")+ Uri.parse("*16*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Weekly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "15 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*3*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "20 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*3*2*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "10 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*2*3*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                        }
                                        break;
                                    case "internet":
                                        switch (packagesList.get(viewHolder.getAdapterPosition()).getDuration()) {
                                            case "Night":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*4*1*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }

                                                        break;
                                                    }
                                                    case "5 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*4*2*1") + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "7 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*4*3*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }

                                                }
                                                break;
                                            case "Daily":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "5 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*2*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "10 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*3*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "15 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*4*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "35 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*5*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Monthly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "55 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "100 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3*2*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "190 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3*3*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "350 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3*4*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "600 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3")+Uri.encode("#")+Uri.parse("*5*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }

                                                        break;
                                                    }
                                                    case "700 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3")+Uri.encode("#")+Uri.parse("*6*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "1,300 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3")+Uri.encode("#")+Uri.parse("*7*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "1,800 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3")+Uri.encode("#")+Uri.parse("*8*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "4,900 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3")+Uri.encode("#")+Uri.parse("*9*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Weekly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "50 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*2*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "60 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*2*2*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "80 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*2*3*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Weekend":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "35 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*5*1*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }

                                                        break;
                                                    }
                                                    case "60 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*5*2*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "110 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*5*3*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                        }
                                        break;
                                    case "sms":
                                        switch (packagesList.get(viewHolder.getAdapterPosition()).getDuration()) {
                                            case "Night":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "3.49 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*1*1*1*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "4.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*1*1*2*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "6.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*1*1*3*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "9.99 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*1*1*4*1" ) + Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Daily":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "2 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*1*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "3 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*1*2*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                    case "5 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*1*3*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);

                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Monthly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "30 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*3*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);
                                                        }

                                                        break;
                                                    }
                                                    case "50 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*3*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);
                                                        }
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Weekly":
                                                switch (packagesList.get(viewHolder.getAdapterPosition()).getCost()) {
                                                    case "10 Birr": {

                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*2*1*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);
                                                        }
                                                        break;
                                                    }
                                                    case "15 Birr": {
                                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*2*2*1")+Uri.encode("#")));
                                                        {
                                                            permission();
                                                            context.startActivity(intent);
                                                        }
                                                        break;
                                                    }

                                                }
                                                break;
                                        }
                                        break;
                                }
                            }
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    
                }
            });

        }


    }
    private void permission() {

        ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
    }

    @Override
    public int getItemCount() {
        return packagesList.size();
    }

    private class GroupViewHolder extends RecyclerView.ViewHolder{

        final TextView text_group_title;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            text_group_title = itemView.findViewById(R.id.group);
        }
    }
}
