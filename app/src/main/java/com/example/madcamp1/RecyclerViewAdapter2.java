package com.example.madcamp1;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder> {

    //액티비티의 Context, Data추가
    Context mContext;
    List<ContactItem> mData;
    Dialog mDialog;

    public RecyclerViewAdapter2(Context mContext, List<ContactItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_contact_and;
        private ImageView img;
        private TextView tv_name;
        private TextView tv_phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_contact_and = itemView.findViewById(R.id.contact_item_id_and);
            tv_name = itemView.findViewById(R.id.name_contact_and);
            tv_phone = itemView.findViewById(R.id.phone_contact_and);
            img = itemView.findViewById(R.id.img_contact);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_contact_android, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_phone.setText(mData.get(position).getPhone());

        holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.picture1));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}