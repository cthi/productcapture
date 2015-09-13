package com.pc.productcapture.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pc.productcapture.R;
import com.pc.productcapture.rest.wmModel.Item;

import java.text.DecimalFormat;
import java.util.List;

import static android.content.Intent.*;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItems;
    DecimalFormat df;

    public ItemAdapter(Context context, List<Item> items) {
        mContext = context;
        mItems = items;
        df = new DecimalFormat("$0.00");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wmart, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder, int position) {
        viewholder.bind(mItems.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        TextView mPrice;
        ImageView mImg;
        String url;

        public ViewHolder(View view) {
            super(view);

            mTitle = (TextView) view.findViewById(R.id.title);
            mPrice = (TextView) view.findViewById(R.id.price);
            mImg = (ImageView) view.findViewById(R.id.img);

            view.setOnClickListener(this);
        }

        public void bind(Item item) {
            mTitle.setText(item.name);
            mPrice.setText(df.format(item.salePrice));
            Glide.with(mContext).load(item.thumbnailImage).into(mImg);
            url = item.addToCartUrl;
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(ACTION_VIEW);
            i.setData(Uri.parse(url));
            mContext.startActivity(i);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

