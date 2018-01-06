package com.pappydevelopers.groupsforwhatsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yash on 17/9/17.
 */

public class WhatsappListAdapter extends RecyclerView.Adapter<WhatsappListAdapter.WhatsappListViewHolder>{

    private ArrayList<WhatsappModel> dataSet;
    public ImageLoader imageLoader;
    static Context context;


    public WhatsappListAdapter(ArrayList<WhatsappModel> data, Context context) {
        this.dataSet = data;
        imageLoader = new ImageLoader(context);

    }

    @Override
    public WhatsappListAdapter.WhatsappListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.grouplist,parent,false);
        WhatsappListViewHolder viewHolder=new WhatsappListViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(WhatsappListAdapter.WhatsappListViewHolder holder, int position) {

        final WhatsappModel  issuedBooksModel = dataSet.get(position);

        holder.category.setText(issuedBooksModel.getGp_category().toString());
        holder.name.setText(issuedBooksModel.getGp_name().toString());


        if (issuedBooksModel.getGp_category().toString().equals("Friendship & Dating")) {
            holder.group_image.setImageResource(R.drawable.friendship);
        } else if (issuedBooksModel.getGp_category().toString().equals("Education")) {
            holder.group_image.setImageResource(R.drawable.education);
        } else if (issuedBooksModel.getGp_category().toString().equals("News")) {
            holder.group_image.setImageResource(R.drawable.news);
        } else if (issuedBooksModel.getGp_category().toString().equals("Sports")) {
            holder.group_image.setImageResource(R.drawable.sports);
        } else if (issuedBooksModel.getGp_category().toString().equals("Adult 18+")) {
            holder.group_image.setImageResource(R.drawable.adult);
        } else if (issuedBooksModel.getGp_category().toString().equals("Health & Fitness")) {
            holder.group_image.setImageResource(R.drawable.clubs);
        } else if (issuedBooksModel.getGp_category().toString().equals("All Groups")) {
            holder.group_image.setImageResource(R.drawable.all);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JoinGroup.class);
                intent.putExtra("link", issuedBooksModel.getGp_link());
                intent.putExtra("name", issuedBooksModel.getGp_name());
                intent.putExtra("image", issuedBooksModel.getGp_image());
                intent.putExtra("category", issuedBooksModel.getGp_category());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class WhatsappListViewHolder extends RecyclerView.ViewHolder{

        protected TextView category,name;
        protected ImageView group_image;
        protected CardView cardView;

        public WhatsappListViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            category= (TextView) itemView.findViewById(R.id.category);
            name = (TextView) itemView.findViewById(R.id.name);
            group_image = (ImageView) itemView.findViewById(R.id.group_image);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
        }
    }
}