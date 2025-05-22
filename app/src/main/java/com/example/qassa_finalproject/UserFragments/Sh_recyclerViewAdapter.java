package com.example.qassa_finalproject.UserFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qassa_finalproject.BarberShop;
import com.example.qassa_finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class Sh_recyclerViewAdapter extends RecyclerView.Adapter<Sh_recyclerViewAdapter.MyViewHolder> {

    private ArrayList<BarberShop> mDataset;

    public Sh_recyclerViewAdapter(Context context, List<BarberShop> barberList) {
        this.mDataset = new ArrayList<>(barberList);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textV;

        public MyViewHolder(View v) {
            super(v);
            textV = v.findViewById(R.id.tvShopNameRow);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shop__row_, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textV.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
