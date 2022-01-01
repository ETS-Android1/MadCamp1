package com.example.madcamp1;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder> {

    Context mContext;
    List<ContactItem> mData;
    Dialog mDialog;

    public RecyclerViewAdapter2(Context mContext, List<ContactItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_contact_and;
        private ImageView item_call;
        private ImageView item_msg;
        private ImageView img;
        private TextView tv_name;
        private TextView tv_phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_contact_and = itemView.findViewById(R.id.contact_item_id_and);
            item_call = itemView.findViewById(R.id.call_img_and);
            item_msg = itemView.findViewById(R.id.msg_img_and);
            tv_name = itemView.findViewById(R.id.name_contact_and);
            tv_phone = itemView.findViewById(R.id.phone_contact_and);
            img = itemView.findViewById(R.id.img_contact_and);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_contact_android, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);


        vHolder.item_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = mData.get(vHolder.getAdapterPosition()).getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_number));
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
        ContactItem contactItem = mData.get(position);
        holder.tv_name.setText(contactItem.getName());
        holder.tv_phone.setText(contactItem.getPhone());

        holder.img.setImageResource(R.mipmap.ic_launcher_round);
        Bitmap profile = loadContactPhoto(mContext.getContentResolver(), contactItem.getPerson_id(), contactItem.getPhoto_id());
        if (profile != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                holder.img.setBackground(new ShapeDrawable(new OvalShape()));
                holder.img.setClipToOutline(true);
            }
            holder.img.setImageBitmap(profile);
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                holder.img.setClipToOutline(false);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
        byte[] photoBytes = null;
        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO},
                null, null, null);
        try {
            if (c.moveToFirst())
                photoBytes = c.getBlob(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        if (photoBytes != null) {
            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
        } else
            Log.d("<<CONTACT_PHOTO>>", "second try also failed");

        return null;

    }

    public Bitmap resizingBitmap(Bitmap oBitmap) {
        if (oBitmap == null) {
            return null;
        }

        float width = oBitmap.getWidth();
        float height = oBitmap.getHeight();
        float resizing_size = 120;

        Bitmap rBitmap = null;
        if (width > resizing_size) {
            float mWidth = (float) (width / 100);
            float fScale = (float) (resizing_size / mWidth);
            width *= (fScale / 100);
            height *= (fScale / 100);

        } else if (height > resizing_size) {
            float mHeight = (float) (height / 100);
            float fScale = (float) (resizing_size / mHeight);

            width *= (fScale / 100);
            height *= (fScale / 100);
        }

        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int) width, (int) height, true);
        return rBitmap;
    }

}