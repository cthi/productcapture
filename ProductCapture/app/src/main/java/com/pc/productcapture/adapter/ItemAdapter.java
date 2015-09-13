package com.pc.productcapture.adapter;

import android.content.Context;
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


            Glide.with(mContext)
                    .load(item.thumbnailImage)
                    .into(mImg);
        }

        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(mContext, SongSubsetActivity.class);
//            intent.putExtra(Constants.QUERY_CONSTRAINT, mAlbumList.get(getAdapterPosition()).getTitle());
//            intent.putExtra(Constants.QUERY_TYPE, Constants.QUERY_TYPE_ALBUM);
//            intent.putExtra(Constants.DATA_ALBUM_ID, mAlbumList.get(getAdapterPosition()).getId());
//            ATPApplication.subActivityWillBeVisible();
//            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

