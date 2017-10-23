package com.gappydevelopers.unlimitedwhatsappgroups;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by yash on 17/9/17.
 */

public class WhatsappListAdapter extends RecyclerView.Adapter<WhatsappListAdapter.WhatsappListViewHolder>{

    private ArrayList<WhatsappModel> dataSet;
    public ImageLoader imageLoader;
    Context mContext;


    public WhatsappListAdapter(ArrayList<WhatsappModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
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

        ImageView image = holder.group_image;
        imageLoader.DisplayImage(issuedBooksModel.getGp_image(), image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JoinGroup.class);
                intent.putExtra("link", issuedBooksModel.getGp_link());
                intent.putExtra("name", issuedBooksModel.getGp_name());
                intent.putExtra("image", issuedBooksModel.getGp_image());
                intent.putExtra("category", issuedBooksModel.getGp_category());
                mContext.startActivity(intent);
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
            category= (TextView) itemView.findViewById(R.id.category);
            name = (TextView) itemView.findViewById(R.id.name);
            group_image = (ImageView) itemView.findViewById(R.id.group_image);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
        }
    }
}