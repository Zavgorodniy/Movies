package com.zavgorodniy.movies;

import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.zavgorodniy.movies.Fragments.ItemList;
import com.zavgorodniy.movies.Fragments.RequestList;

public class MainActivity extends FragmentActivity
        implements RequestList.RequestFragmentListener, ItemList.ItemFragmentListener{

    ItemList itemList;
    int request = 0;
    int filter = -1;
    RequestList requestList;
    boolean active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            request = savedInstanceState.getInt("request");
            filter = savedInstanceState.getInt("filter");
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            requestList = new RequestList();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, requestList).commit();
        } else {
            showItemList(request, filter);
        }
    }

    public void showItemList(int req, int filt) {
//        itemList = (ItemList) getSupportFragmentManager().findFragmentById(R.id.container);
        if (itemList == null || itemList.getRequest() != req) {
            itemList = ItemList.newInstance(req, filt);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, itemList).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("request", request);
        outState.putInt("filter", filter);
    }

    @Override
    public void requestFragmentInteraction(int i) {
        request = i;
        showItemList(i, filter);
    }

    @Override
    public void itemFragmentInteraction(int i) {
        filter = i;
        showItemList(request, i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
