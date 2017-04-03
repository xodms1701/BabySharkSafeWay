package com.gonigoni.ui.searchresult.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gonigoni.babysharksafeway.R;
import com.skp.Tmap.TMapPOIItem;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<TMapPOIItem> tmapPOIItems;

    public SearchResultRecyclerViewAdapter(ArrayList<TMapPOIItem> tmapPOIItems) {
        this.tmapPOIItems = tmapPOIItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.resultrecyclerviewitem, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
        listItemViewHolder.title.setText(tmapPOIItems.get(position).getPOIName());
    }

    @Override
    public int getItemCount() {
        return tmapPOIItems.size();
    }

    public TMapPOIItem getItem(int idx) {
        return tmapPOIItems.get(idx);
    }


    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }


    }


}
