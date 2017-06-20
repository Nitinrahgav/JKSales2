package com.jkpaper.jksales.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkpaper.jksales.Models.Menu;
import com.jkpaper.jksales.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {
    private List<Menu> menus;
    private Context context;
    public MenuAdapter(Context context, List<Menu> menus) {
        this.menus = menus;
        this.context = context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        public MyViewHolder(View itemView) {
            super(itemView);
            textName = (TextView)itemView.findViewById(R.id.tv_android);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textName.setText(menus.get(position).getMenuName());
    }



    @Override
    public int getItemCount() {
        return menus.size();
    }


}
