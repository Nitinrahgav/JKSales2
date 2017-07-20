package com.jkpaper.jksales.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jkpaper.jksales.Activity.DetailActivity;
import com.jkpaper.jksales.Activity.DetailsActivity;
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
        CardView cardMenu;
        public MyViewHolder(View itemView) {
            super(itemView);
            textName = (TextView)itemView.findViewById(R.id.tv_android);
            cardMenu = (CardView)itemView.findViewById(R.id.card_menus);

        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textName.setText(menus.get(position).getMenuName());
        if(menus.get(position).getMenuAccess() == 0){
            holder.cardMenu.setCardBackgroundColor(Color.parseColor("#cfd8dc"));
        }else {
            int colorRes = 0;
            switch(position % 6) {
                case 0: colorRes = R.color.colour1;
                    break;
                case 1: colorRes = R.color.colour2;
                    break;
                case 2: colorRes = R.color.colour3;
                    break;
                case 3: colorRes = R.color.colour6;
                    break;
                case 4: colorRes = R.color.colour4;
                    break;
                case 5: colorRes = R.color.colour5;
                    break;
            }
            holder.cardMenu.setBackgroundColor(ContextCompat.getColor(context, colorRes));
            holder.cardMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = menus.get(position).getMenuApi();
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("url",url);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("title",menus.get(position).getMenuName());
                    context.startActivity(intent);
                    //Toast.makeText(context,"Clicked  "+position,Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



    @Override
    public int getItemCount() {
        return menus.size();
    }


}
