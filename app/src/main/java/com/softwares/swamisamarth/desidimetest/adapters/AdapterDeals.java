package com.softwares.swamisamarth.desidimetest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softwares.swamisamarth.desidimetest.Models.Deal;
import com.softwares.swamisamarth.desidimetest.R;

import java.util.ArrayList;

/**
 * Created by narendra on 10/8/15.
 */
public class AdapterDeals extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflator;
    private ArrayList<Deal> deals;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public AdapterDeals(Context context, ArrayList<Deal> deals) {
        inflator = LayoutInflater.from(context);
        this.deals = deals;
        this.context = context;


    }

    private class DealsViewHolder extends RecyclerView.ViewHolder {

        ImageView dealImageView;
        TextView dealTitle;

        public DealsViewHolder(View itemView) {
            super(itemView);
            dealImageView = (ImageView) itemView.findViewById(R.id.deal_imageView);
            dealTitle = (TextView) itemView.findViewById(R.id.dealTitle_textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(getLayoutPosition(), view);
                    }
                }
            });
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.item_deals, parent, false);
        return new DealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DealsViewHolder DealsViewHolder = (DealsViewHolder) holder;
        Deal deal = deals.get(position);
        Log.d("Deal", deal.getTitle());
        DealsViewHolder.dealTitle.setText(deal.getTitle());
        if(deal.getImagePath() != null){
            String url = "http://cdn0.desidime.com"+ deal.getImagePath();
            Log.d("Narendra", position + "  " + url);
            Glide.with(context).load(url).into(DealsViewHolder.dealImageView);
        }

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        DealsViewHolder DealsViewHolder = (DealsViewHolder) holder;
        DealsViewHolder.dealImageView.setImageBitmap(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
       void onItemClick(int position, View view);
    }

}
