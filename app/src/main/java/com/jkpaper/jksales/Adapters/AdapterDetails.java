package com.jkpaper.jksales.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkpaper.jksales.Models.Detail;
import com.jkpaper.jksales.Models.Menu;
import com.jkpaper.jksales.R;

import java.util.List;

/**
 * Created by ashish on 28/6/17.
 */

public class AdapterDetails extends RecyclerView.Adapter<AdapterDetails.MyViewHolder> {
    private List<Detail> details;
    private Context context;
    public AdapterDetails(Context context, List<Detail> details) {
        this.details = details;
        this.context = context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return details.size();
    }


}
