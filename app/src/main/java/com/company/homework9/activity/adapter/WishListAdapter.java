package com.company.homework9.activity.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.homework9.Item;
import com.company.homework9.R;
import com.company.homework9.activity.MainActivity;
import com.company.homework9.activity.fragment.MainActivity.WishListFragment;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.CardViewHolder> {
    private List<Item> list;
    private LayoutInflater mInflator;
    private ItemClickListener mclickListener;
    private Context mContext;

    public WishListAdapter(Context context, List<Item> list) {
        this.mInflator = LayoutInflater.from(context);
        this.list = list;
        mContext = context;

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflator.inflate(R.layout.item_card, parent, false);
        return new WishListAdapter.CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        final Item current = list.get(position);
        Picasso.get().load(current.getImageUrl()).into(holder.item_image);
        String title = current.getTitle();
        holder.title = title;
        holder.item = current;
        String titleToPut;
        if(title.length() >= 60) {
            titleToPut = title.substring(0, 60) + "...";
        } else if(title.length() <=30){
            titleToPut = title + "\n\n";
        } else {
            titleToPut = title + "\n";
        }
        String toastName;
        if(title.length() >= 60) {
            toastName = title.substring(0, 60) + "...";
        } else {
            toastName = title;
        }
        holder.item_title.setText(titleToPut);
        String zip = "Zip: " + current.getZipCode();
        holder.item_zip.setText(zip);
        String shipping;
        if(current.getShippingCost().equals("0.0")) {
            shipping = "Free Shipping";
        } else {
            shipping = "$" + current.getShippingCost();
        }
        holder.item_shipping_fee.setText(shipping);
        String condition;
        if(current.getCondition().length() <= 20) {
            condition = "\n" + current.getCondition();
        } else {
            condition = current.getCondition();
        }
        holder.item_condition.setText(condition);
        holder.item_cost.setText(" \n$" + current.getCost());
//        holder.wish_list.setImageResource(R.drawable.cart_plus);
        holder.id = current.getId();

        if(WishListFragment.wishListMap.containsKey(current.getId())) {
            holder.wish_list.setImageResource(R.drawable.cart_remove);
            holder.wish_list.setColorFilter(ContextCompat.getColor(mContext, R.color.btn_color), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            holder.wish_list.setImageResource(R.drawable.cart_plus);
            holder.wish_list.setColorFilter(ContextCompat.getColor(mContext, R.color.cart_color), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        holder.wish_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(WishListFragment.wishListMap.containsKey(current.getId())) {
                    WishListFragment.wishListMap.remove(current.getId());
                    holder.wish_list.setImageResource(R.drawable.cart_plus);
                    Toast.makeText(mContext,toastName +" was removed from wish list",Toast.LENGTH_SHORT).show();
                    holder.wish_list.setColorFilter(ContextCompat.getColor(mContext, R.color.cart_color), android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    WishListFragment.wishListMap.put(current.getId(), current);
                    holder.wish_list.setImageResource(R.drawable.cart_remove);
                    holder.wish_list.setColorFilter(ContextCompat.getColor(mContext, R.color.btn_color), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                MainActivity.getViewPager().getAdapter().notifyDataSetChanged();
            }
        });
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_image;
        TextView item_title;
        TextView item_zip;
        TextView item_shipping_fee;
        TextView item_condition;
        TextView item_cost;
        ImageView wish_list;
        String id;
        String title;
        Item item;

        CardViewHolder(View v) {
            super(v);
            item_image = v.findViewById(R.id.item_image);
            item_title = v.findViewById(R.id.item_title);
            item_zip = v.findViewById(R.id.item_zip_code);
            item_shipping_fee = v.findViewById(R.id.item_shipping_fee);
            item_condition = v.findViewById(R.id.item_condition);
            item_cost = v.findViewById(R.id.item_cost);
            wish_list = v.findViewById(R.id.wish_list);
            id = "";
            title = "";
            item = null;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mclickListener != null) {
                mclickListener.onItemClick(view, getAdapterPosition(), id, title, item);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position, String item_id, String item_title, Item item);
    }

    public void setMclickListener(ItemClickListener listener) {
        this.mclickListener = listener;
    }

}
