package com.example.zingme.babble.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zingme.babble.Main2Activity;
import com.example.zingme.babble.R;
import com.example.zingme.babble.appdata.AppData;

public class FeaturesAdapter extends BaseAdapter {
    Context context;

    public FeaturesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return AppData.getData().features.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        TextView title, description; ImageButton imgBtn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh=new ViewHolder();
        convertView = View.inflate(context, R.layout.row_features, null);
        vh.title=convertView.findViewById(R.id.feature_title);
        vh.description=convertView.findViewById(R.id.feature_description);
        vh.imgBtn=convertView.findViewById(R.id.feature_img);
        vh.title.setText(AppData.getData().features.get(position).getTitle());
        vh.description.setText(AppData.getData().features.get(position).getDescription());
        vh.imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Main2Activity.class);
                i.putExtra("feature", position);
                context.startActivity(i);
            }
        });

        return convertView;
    }
}
