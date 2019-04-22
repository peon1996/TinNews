package com.company.homework9.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.homework9.R;
import com.company.homework9.SimilarItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarItemAdapter extends RecyclerView.Adapter<SimilarItemAdapter.ViewHolder> {
    private List<SimilarItem> data;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private Context context;

    public SimilarItemAdapter(Context context, List<SimilarItem> list) {
        this.inflater = LayoutInflater.from(context);
        this.data = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.similar_item, parent, false);
        return new SimilarItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimilarItem item = data.get(position);
        Picasso.get().load(item.getImageURL()).into(holder.imageView);
        holder.title.setText(item.getTitle());

        if(item.getPrice().equals("N/A")) {
            holder.price.setText(item.getPrice());
        } else {
            holder.price.setText("$" + item.getPrice());
        }

        if(item.getShipping().equals("N/A")) {
            holder.shipping.setText(item.getShipping());
        } else {
            String shippingToPut;
            if(Float.parseFloat(item.getShipping()) == 0) {
                shippingToPut = "Free Shipping";
            } else {
                shippingToPut = "$" + item.getShipping();
            }
            holder.shipping.setText(shippingToPut);
        }

        if(item.getDays().equals("N/A")) {
            holder.days.setText(item.getDays());
        } else {
            int indexp = item.getDays().indexOf("P");
            int indexd = item.getDays().indexOf("D");
            String str = item.getDays().substring(indexp + 1, indexd);
            if(str.equals("0") || str.equals("1")) {
                String s = str + " Day Left";
                holder.days.setText(s);
            } else {
                String s = str + " Days Left";
                holder.days.setText(s);
            }
        }

        holder.webURL = item.getWebURL();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView title;
        TextView shipping;
        TextView days;
        TextView price;
        String webURL;

        ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.similar_photo);
            title = v.findViewById(R.id.similar_title);
            shipping = v.findViewById(R.id.similar_shipping);
            days = v.findViewById(R.id.similar_days);
            price = v.findViewById(R.id.similar_cost);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(webURL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null) {
                clickListener.onItemClick(v, getAdapterPosition(), webURL);
            }
        }
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position, String url);
    }
}
