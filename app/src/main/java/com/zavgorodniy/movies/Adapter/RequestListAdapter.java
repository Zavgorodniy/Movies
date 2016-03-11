package com.zavgorodniy.movies.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zavgorodniy.movies.R;
import com.zavgorodniy.movies.Service.RequestItem;

import java.util.List;

public class RequestListAdapter extends ArrayAdapter<RequestItem> {

    private List<RequestItem> mItems;
    private Context mContext;


    public RequestListAdapter(Context context, int resource, List<RequestItem> items) {
        super(context, resource, items);
        mItems = items;
        mContext = context;
    }

    @Override
    public RequestItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getPosition(RequestItem item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.request_item, null);
        }
        int id = mContext.getResources().getIdentifier(mItems.get(position).image, "drawable", mContext.getPackageName());

        TextView mText = (TextView) convertView.findViewById(R.id.tv_request_name);
        mText.setText(mItems.get(position).name);
        ImageView mImage = (ImageView) convertView.findViewById(R.id.iv_request_image);
        mImage.setImageDrawable(mContext.getResources().getDrawable(id));

        return convertView;
    }
}
