package com.example.kelvin.demomenu;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by darshanz on 7/6/15.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.DrawerViewHolder> {

    private ArrayList<DrawerItem> drawerMenuList;

    private OnItemSelecteListener mListener;

    public MenuAdapter(ArrayList<DrawerItem> drawerMenuList) {
        this.drawerMenuList = drawerMenuList;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_item, parent, false);

        return new DrawerViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {

        //holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.gray));

        holder.title.setText(drawerMenuList.get(position).getTitle());
        holder.icon.setImageResource(drawerMenuList.get(position).getIcon());
        holder.leftView.setBackgroundColor(drawerMenuList.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return drawerMenuList.size();
    }

    class DrawerViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        View leftView;
        ImageView icon;

        public DrawerViewHolder(View itemView, int viewType) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            leftView = (View) itemView.findViewById(R.id.leftView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());

                }
            });
        }
    }


    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemSelecteListener {
        public void onItemSelected(View v, int position);
    }
}