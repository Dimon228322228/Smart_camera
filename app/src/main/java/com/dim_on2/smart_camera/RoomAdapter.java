package com.dim_on2.smart_camera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.dim_on2.smart_camera.database.Room;

import java.util.List;

public class RoomAdapter extends ArrayAdapter<Room> {

    private LayoutInflater inflater;
    private int layout;
    private List<Room> productList;

    public RoomAdapter(@NonNull Context context, int resource, @NonNull List<Room> objects) {
        super(context, resource, objects);
        this.productList = objects;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final int product = productList.get(position).getId();

        viewHolder.button.setClickable(false);
        viewHolder.button.setText(product);

        return convertView;
    }

    private class ViewHolder{
        final Button button;
        ViewHolder(View v){
            button = v.findViewById(R.id.room_button);
        }
    }

}
