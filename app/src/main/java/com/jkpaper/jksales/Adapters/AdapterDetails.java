package com.jkpaper.jksales.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkpaper.jksales.Models.Detail;
import com.jkpaper.jksales.R;

import java.util.List;

public class AdapterDetails extends RecyclerView.Adapter<AdapterDetails.MyViewHolder> {
    private List<Detail> details;
    private Context context;
    public AdapterDetails(Context context, List<Detail> details) {
        this.details = details;
        this.context = context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetail1, tvDetail2, tvDetail3, tvDetail4, tvDetail5, tvDetail6, tvDetail7, tvDetail8, tvDetail9;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvDetail1 = (TextView)itemView.findViewById(R.id.tv_detail1);
            tvDetail2 = (TextView)itemView.findViewById(R.id.tv_detail2);
            tvDetail3 = (TextView)itemView.findViewById(R.id.tv_detail3);
            tvDetail4 = (TextView)itemView.findViewById(R.id.tv_detail4);
            tvDetail5 = (TextView)itemView.findViewById(R.id.tv_detail5);
            tvDetail6 = (TextView)itemView.findViewById(R.id.tv_detail6);
            tvDetail7 = (TextView)itemView.findViewById(R.id.tv_detail7);
            tvDetail8 = (TextView)itemView.findViewById(R.id.tv_detail8);
            tvDetail9 = (TextView)itemView.findViewById(R.id.tv_detail9);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail, parent, false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvDetail1.setText(details.get(position).getName());
        holder.tvDetail2.setText(details.get(position).getTgt());
        holder.tvDetail3.setText(details.get(position).getOrder());
        holder.tvDetail4.setText(details.get(position).getSls());
        holder.tvDetail5.setText(details.get(position).getShortfall());
        holder.tvDetail6.setText(details.get(position).getAchieved());
        holder.tvDetail7.setText(details.get(position).getSlsLast());
        holder.tvDetail8.setText(details.get(position).getSlsAvg());
        holder.tvDetail9.setText(details.get(position).getSlsLy());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }


}
