package com.sergey.workapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sergey.workapp.R;
import com.sergey.workapp.dao.DaoManager;
import com.sergey.workapp.dao.Department;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterDep extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Department> items;
    private static ClickListener clickListener;

    public AdapterDep(ArrayList<Department> items) {
        this.items = items;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dep_view, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Department department= items.get(position);
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.title.setText(department.getTitle());
        holder.count.setText(String.valueOf(DaoManager.getInstance().getUsersByDep(department).size()));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        AdapterDep.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(Department department);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @BindView(R.id.item_title)
        TextView title;
        @BindView(R.id.item_count)
        TextView count;

        ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(items.get(getAdapterPosition()));
        }
    }
}