package com.example.qassa_finalproject.UserFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qassa_finalproject.R;
import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueViewHolder> {
    private List<String> timeSlots;
    private OnTimeSlotClickListener listener;
    private int selectedPosition = -1;

    public interface OnTimeSlotClickListener {
        void onTimeSlotClick(String time);
    }

    public QueueAdapter(List<String> timeSlots, OnTimeSlotClickListener listener) {
        this.timeSlots = timeSlots;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QueueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_queue_slot, parent, false);
        return new QueueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QueueViewHolder holder, int position) {
        String time = timeSlots.get(position);
        holder.timeButton.setText(time);
        holder.timeButton.setSelected(position == selectedPosition);
        holder.timeButton.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            listener.onTimeSlotClick(time);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    static class QueueViewHolder extends RecyclerView.ViewHolder {
        Button timeButton;

        QueueViewHolder(View itemView) {
            super(itemView);
            timeButton = itemView.findViewById(R.id.btnTimeSlot);
        }
    }
}