package com.example.kelvin.demomenu.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kelvin.demomenu.R;
import com.example.kelvin.demomenu.entities.MonthSaving;

import java.util.List;

/**
 * Created by kelvin on 20/09/16.
 */

public class ConsumptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<MonthSaving> monthSavings;

    public ConsumptionsAdapter(List<MonthSaving> list) {
        monthSavings = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_header, parent, false);
            return new ListHeader(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_list_item, parent, false);
            return new ListItem(v);
        }
        return null;
    }


    private MonthSaving getItem(int position) {
        return monthSavings.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ListHeader) {

        } else if (holder instanceof ListItem) {

            ListItem item = (ListItem) holder;

            item.txtBenefit.setText(monthSavings.get(position - 1).getNombre());
            item.txtDate.setText(monthSavings.get(position - 1).getFechaConsumo());

            item.txtSavings.setText(item.txtSavings.getContext().getResources().getString(R.string.money_format,
                                                                monthSavings.get(position - 1).getMontoDescontado()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return monthSavings.size() + 1;
    }

    class ListHeader extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public ListHeader(View itemView) {
            super(itemView);
            //this.txtTitle = (TextView)itemView.findViewById(R.id.txtHeader);
        }
    }

    class ListItem extends RecyclerView.ViewHolder {

        TextView txtBenefit;
        TextView txtDate;
        TextView txtSavings;

        public ListItem(View itemView) {
            super(itemView);

            txtBenefit = (TextView) itemView.findViewById(R.id.txtBenefit);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtSavings = (TextView) itemView.findViewById(R.id.txtSaving);
        }
    }
}
