package com.zavgorodniy.movies;

import android.app.Activity;
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

public class ItemInfo extends Activity {

    public ItemInfo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);

        StringBuilder buf = new StringBuilder();

        new DownloadImageTask((ImageView) findViewById(R.id.iv_image)).execute("http://image.tmdb.org/t/p/w300/" + getIntent().getStringExtra("imageId"));

        TextView description = (TextView) findViewById(R.id.tv_description);

        if (!getIntent().getStringExtra("description").equals("null"))
            description.setText(getIntent().getStringExtra("description"));
        else
            description.setText(R.string.st_no_description);

        TextView name = (TextView) findViewById(R.id.tv_name);
        name.setText(getIntent().getStringExtra("name"));

        buf.append(getResources().getString(R.string.st_rating));
        buf.append(" ");
        buf.append(getIntent().getStringExtra("rating"));
        TextView rating = (TextView) findViewById(R.id.tv_rating);
        rating.setText(buf);

        buf.setLength(0);
        buf.append(getResources().getString(R.string.st_genre));
        buf.append(" ");
        buf.append(getIntent().getStringExtra("genre"));
        TextView genre = (TextView) findViewById(R.id.tv_genre);
        genre.setText(buf);

        buf.setLength(0);
        buf.append(getResources().getString(R.string.st_date));
        buf.append(" ");
        buf.append(getIntent().getStringExtra("date"));
        TextView date = (TextView) findViewById(R.id.tv_date);
        date.setText(buf);

        Button back = (Button) findViewById(R.id.bt_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
