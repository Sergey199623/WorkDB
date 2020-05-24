package com.sergey.workapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sergey.workapp.R;
import com.sergey.workapp.dao.DaoManager;
import com.sergey.workapp.dao.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterUser extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<User> items;
    private static ClickListener clickListener;

    public AdapterUser(ArrayList<User> items) {
        this.items = items;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_view, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        User user = items.get(position);
        ItemHolder holder = (ItemHolder) viewHolder;
        holder.title.setText(user.getFamily() + " " + user.getName());
        holder.role.setText(user.getRole());
        holder.role.setVisibility(user.getRole()!=null && user.getRole().length()>0?View.VISIBLE:View.GONE);
        DaoManager.getInstance().refreshDep(user.getDepartment());
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        AdapterUser.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(User department);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_title)
        TextView title;
        @BindView(R.id.item_role)
        TextView role;

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