package com.example.madcamp1;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder> implements Filterable {

    Context mContext;
    List<ContactItem> unfilteredList;
    List<ContactItem> filteredList;
    Dialog mDialog;

    public RecyclerViewAdapter2(Context mContext, List<ContactItem> mData) {
        this.mContext = mContext;
        this.unfilteredList = mData;
        this.filteredList = mData;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredList = unfilteredList;
                } else {
                    List<ContactItem> filteringList = new ArrayList<>();
                    for (int i = 0; i < unfilteredList.size(); i++) {
                        ContactItem item = unfilteredList.get(i);
                        if (item.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(item);
                            System.out.println("newItem - " + item.getName());
                        }
                    }
                    filteredList = filteringList;
                    System.out.print("filteredList: ");
                    for (int i = 0; i < filteredList.size(); i++) {
                        System.out.print(filteredList.get(i).getName() + " ");
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<ContactItem>) results.values;
                notifyDataSetChanged();
            }
        };
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

        //Dialog init
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_contact);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //------------------------------------------------------------------------------------------
        vHolder.item_contact_and.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv = mDialog.findViewById(R.id.dialog_name);
                TextView dialog_phone_tv = mDialog.findViewById(R.id.dialog_phone);
                ImageView dialog_contact_img = mDialog.findViewById(R.id.dialog_img);
                dialog_name_tv.setText(filteredList.get(vHolder.getAdapterPosition()).getName());
                dialog_phone_tv.setText(filteredList.get(vHolder.getAdapterPosition()).getPhone());

                dialog_contact_img.setImageBitmap(filteredList.get(vHolder.getAdapterPosition()).getPhoto());

                mDialog.show();
            }
        });
        //------------------------------------------------------------------------------------------

        vHolder.item_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = filteredList.get(vHolder.getAdapterPosition()).getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_number));
                mContext.startActivity(intent);
            }
        });
        vHolder.item_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = filteredList.get(vHolder.getAdapterPosition()).getPhone();
                Uri smsUri = Uri.parse("sms:" + phone_number);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
                mContext.startActivity(sendIntent);
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContactItem contactItem = filteredList.get(position);
        holder.tv_name.setText(contactItem.getName());
        holder.tv_phone.setText(contactItem.getPhone());

        holder.img.setImageResource(R.mipmap.ic_launcher_round);
        Bitmap profile = loadContactPhoto(mContext.getContentResolver(), contactItem.getPerson_id(), contactItem.getPhoto_id());
        if (profile != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                holder.img.setBackground(new ShapeDrawable(new OvalShape()));
                holder.img.setClipToOutline(true);
            }
            filteredList.get(holder.getAdapterPosition()).setPhoto(profile);
            holder.img.setImageBitmap(resizingBitmap(profile));
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                holder.img.setClipToOutline(false);
            }
        }

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public Bitmap loadContactPhoto(ContentResolver cr, long person_id, long photo_id) {
        Uri my_contact_Uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, person_id);
        InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(cr, my_contact_Uri, true);
        BufferedInputStream buf = new BufferedInputStream(photo_stream);
        Bitmap my_btmp = BitmapFactory.decodeStream(buf);
        try {
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return my_btmp;
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