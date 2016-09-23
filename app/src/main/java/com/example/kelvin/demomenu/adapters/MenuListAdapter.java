package com.example.kelvin.demomenu.adapters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.kelvin.demomenu.ClubRequestManager;
import com.example.kelvin.demomenu.MainActivity;
import com.example.kelvin.demomenu.R;
import com.example.kelvin.demomenu.entities.Category;

import java.util.List;


/**
 * Created by kelvin on 07/09/16.
 */
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.CustomViewHolder> {

    private List<Category> categoryList;
    private MenuListAdapter.OnItemSelecteListener mListener;

    public MenuListAdapter(List<Category> categories) {

        this.categoryList = categories;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_row, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {

        final Category category = categoryList.get(position);

        holder.textView.setText(category.getNombre());
        holder.textView.setTypeface(MainActivity.typeFace);
        holder.colorView.setBackgroundColor(Color.parseColor(category.getColor()));

        ImageLoader imageLoader = ClubRequestManager.getInstance(holder.colorView.getContext()).getImageLoader();

        imageLoader.get(category.getIcono(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                if (bitmap != null) {

                    holder.imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;
        public View colorView;

        public CustomViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.text);
            imageView = (ImageView) view.findViewById(R.id.image);
            colorView = view.findViewById(R.id.colorView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(MenuListAdapter.OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemSelecteListener {
        void onItemSelected(View v, int position);
    }
}