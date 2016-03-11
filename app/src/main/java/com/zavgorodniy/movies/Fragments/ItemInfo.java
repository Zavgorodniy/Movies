package com.zavgorodniy.movies.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zavgorodniy.movies.R;
import com.zavgorodniy.movies.Service.Item;

import java.io.InputStream;

public class ItemInfo extends Fragment {

    Item item;

    public ItemInfo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_info, container, false);

        item = (Item) savedInstanceState.get("item");
        StringBuilder buf = new StringBuilder();

        new DownloadImageTask((ImageView) view.findViewById(R.id.iv_image)).execute("http://image.tmdb.org/t/p/w300/" + item.getImageId());

        TextView description = (TextView) view.findViewById(R.id.tv_description);

        if (item.getDescription() != null)
            description.setText(item.getDescription());
        else
            description.setText(R.string.st_no_description);

        TextView name = (TextView) view.findViewById(R.id.tv_name);
        name.setText(item.getName());

        buf.append(R.string.st_rating);
        buf.append(item.getRating());
        TextView rating = (TextView) view.findViewById(R.id.tv_rating);
        rating.setText(buf);

        buf.setLength(0);
        buf.append(R.string.st_genre);
        buf.append(item.getRating());
        TextView genre = (TextView) view.findViewById(R.id.tv_genre);
        genre.setText(buf);

        buf.setLength(0);
        buf.append(R.string.st_date);
        buf.append(item.getRating());
        TextView date = (TextView) view.findViewById(R.id.tv_date);
        date.setText(buf);

        Button back = (Button) view.findViewById(R.id.bt_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
