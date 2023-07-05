package com.example.contact;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;
import android.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.contact.databinding.RecyclerListBinding;

import java.util.ArrayList;
import java.util.List;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ContactViewHolder> implements Filterable {

    ArrayList<Contact> contactArrayList = new ArrayList<>();
    private OnItemClickListener listener;
    ArrayList<Contact> backup;


    public ContactRecyclerAdapter(ArrayList<Contact> contactArrayList) {
        this.contactArrayList = contactArrayList;
        backup = new ArrayList<>(contactArrayList);
    }

    @NonNull
    @Override
    public ContactRecyclerAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerListBinding recyclerListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recycler_list, parent, false);
        return new ContactViewHolder(recyclerListBinding);


    }


    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact currentContact = contactArrayList.get(position);
        holder.recyclerListBinding.setContact(currentContact);
    }

    @Override
    public int getItemCount() {
        return null != contactArrayList ? contactArrayList.size() : 0;
    }

    public void setContact(ArrayList<Contact> contacts) {
        this.contactArrayList = contacts;
        notifyDataSetChanged();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {

        RecyclerListBinding recyclerListBinding;

        public ContactViewHolder(@NonNull RecyclerListBinding recyclerListBinding) {
            super(recyclerListBinding.getRoot());

            this.recyclerListBinding = recyclerListBinding;
            recyclerListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    if (listener != null && clickedPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(contactArrayList.get(clickedPosition));
                    }
                }
            });
        }

    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);

    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Contact> filterData = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filterData.addAll(backup);
                Log.d("emp", "empty");

            } else {
//                backup = new ArrayList<>(contactArrayList);
                for (Contact contact : backup) {
                    String s = charSequence.toString().toLowerCase();
                    if (contact.getFirstName().toLowerCase().toString().contains(s)){
                        filterData.add(contact);
                    }
                }

            }

            FilterResults results = new FilterResults();
            results.values = filterData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            contactArrayList.clear();
            contactArrayList.addAll((ArrayList<Contact>) filterResults.values);
            notifyDataSetChanged();

        }
    };


}
