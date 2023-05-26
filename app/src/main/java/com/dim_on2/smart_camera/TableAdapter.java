package com.dim_on2.smart_camera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private Context ctx;
    List<TableModel> items;

    public TableAdapter(Context ctx, List<TableModel> items) {
        this.ctx = ctx;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (items != null && items.size() > 0){
            TableModel model = items.get(position);
            holder.name_table.setText(model.getName());
            holder.id_table.setText(model.getId());
            holder.time_table.setText(model.getTime());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_table, id_table, time_table;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_table = itemView.findViewById(R.id.name_table);
            id_table = itemView.findViewById(R.id.id_table);
            time_table = itemView.findViewById(R.id.time_table);
        }
    }
}
