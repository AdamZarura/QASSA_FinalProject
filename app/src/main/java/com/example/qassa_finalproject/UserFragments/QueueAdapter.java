package com.example.qassa_finalproject.UserFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qassa_finalproject.R;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {
    private String[] queueSlots;

    public QueueAdapter(String[] queueSlots) {
        this.queueSlots = queueSlots;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_queue_slot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.slotText.setText(queueSlots[position]);
    }

    @Override
    public int getItemCount() {
        return queueSlots.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView slotText;
        public ViewHolder(View itemView) {
            super(itemView);
            slotText = itemView.findViewById(R.id.slotText);
        }
    }
}

