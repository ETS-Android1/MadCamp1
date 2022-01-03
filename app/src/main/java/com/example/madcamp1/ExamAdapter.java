package com.example.madcamp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExampleViewHoler>{

    private Context context;
    private ArrayList<ExamItem> examItemList = new ArrayList<>();

    public ExamAdapter() {
    }

    public ExamAdapter(Context context, ArrayList<ExamItem> examItemList) {

        this.context = context;
        this.examItemList = examItemList;
    }

    @Override
    public ExampleViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ExampleViewHoler(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHoler holder, int position) {

        ExamItem currentItem = examItemList.get(position);

        String imageurl = currentItem.getImageUrl();

        Picasso.with(context).load(imageurl).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return examItemList.size();
    }


    public void removeAllItem(){
        examItemList.clear();
    }

    public class ExampleViewHoler extends RecyclerView.ViewHolder{

        public ImageView imageView;


        public ExampleViewHoler(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
