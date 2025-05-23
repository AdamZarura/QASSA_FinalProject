package com.example.qassa_finalproject.UserFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity; // Import AppCompatActivity for context casting
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

// Make sure these imports are correct based on your project structure
import com.example.qassa_finalproject.AddFragments.AddDetails_User_Fragment; // Keep if still used, otherwise can be removed.
import com.example.qassa_finalproject.BarberShop;
import com.example.qassa_finalproject.R;

import java.util.ArrayList;
import java.util.List;


public class Sh_recyclerViewAdapter extends RecyclerView.Adapter<Sh_recyclerViewAdapter.MyViewHolder> {

    private final ArrayList<BarberShop> mDataset; // Changed to final - Addresses "Field 'mDataset' may be final" warning
    private final Context context; // Store the context - Addresses "Parameter 'context' is never used" warning

    public Sh_recyclerViewAdapter(Context context, @NonNull List<BarberShop> barberList) { // Added @NonNull for barberList
        this.context = context; // Initialize the context
        this.mDataset = new ArrayList<>(barberList);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textV;

        public MyViewHolder(@NonNull View v) { // Added @NonNull
            super(v);
            textV = v.findViewById(R.id.tvUserName); // Assuming this TextView displays the shop name in the list item
        }
    }

    @NonNull // Added @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Added @NonNull
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shop__row_, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Set the text for the current list item's TextView
        holder.textV.setText(mDataset.get(position).getName());

        // Get the current BarberShop object
        // Removed redundant 'myHolder' variable, 'holder' is already MyViewHolder
        final BarberShop currentShop = mDataset.get(position); // Make it final for use in the lambda expression

        // Handle click on the card
        holder.itemView.setOnClickListener(v -> {
            // Show a Toast message with the clicked shop's name
            Toast.makeText(context, "Clicked: " + currentShop.getName(), Toast.LENGTH_SHORT).show(); // Use stored context

            // Check if the context is an instance of AppCompatActivity to safely get FragmentManager
            if (context instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) context;

                // *** CRITICAL FIX: Create BarberShop_View_Fragment using its newInstance method
                // and pass the currentShop object to it. ***
                BarberShop_View_Fragment barberShopViewFragment = BarberShop_View_Fragment.newInstance(currentShop);

                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main, barberShopViewFragment); // Replace the current fragment with the new fragment
                ft.addToBackStack(null); // Add the transaction to the back stack for proper navigation (back button)
                ft.commit(); // Commit the transaction

            } else {
                // Inform if the context is not suitable for fragment transactions
                Toast.makeText(context, "Error: Cannot perform fragment transaction. Context is not an AppCompatActivity.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}