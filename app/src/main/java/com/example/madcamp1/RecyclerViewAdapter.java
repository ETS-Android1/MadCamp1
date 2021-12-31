package com.example.madcamp1;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    //액티비티의 Context, Data추가
    Context mContext;
    List<Contact> mData;
    Dialog mDialog;

    public RecyclerViewAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_contact;
        private ImageView item_call;
        private ImageView item_msg;
        private ImageView img;
        private TextView tv_name;
        private TextView tv_phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_contact = itemView.findViewById(R.id.contact_item_id);
            item_call = itemView.findViewById(R.id.call_img);
            item_msg = itemView.findViewById(R.id.msg_img);
            tv_name = itemView.findViewById(R.id.name_contact);
            tv_phone = itemView.findViewById(R.id.phone_contact);
            img = itemView.findViewById(R.id.img_contact);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);


        vHolder.item_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = mData.get(vHolder.getAdapterPosition()).getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone_number));
                mContext.startActivity(intent);
            }
        });
        vHolder.item_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = mData.get(vHolder.getAdapterPosition()).getPhone();
                Uri smsUri = Uri.parse("sms:" + phone_number);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
                mContext.startActivity(sendIntent);

            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_phone.setText(mData.get(position).getPhone());
        holder.img.setImageResource(mData.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}