package com.zavgorodniy.movies;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zavgorodniy.movies.Fragments.ItemList;
import com.zavgorodniy.movies.Fragments.RequestList;
import com.zavgorodniy.movies.Service.RequestItem;

import java.util.List;

public class MainActivity extends FragmentActivity implements RequestList.RequestFragmentListener {

    FragmentManager manager;
    FragmentTransaction transaction;
    ItemList items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ItemList();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fl_fragment, new RequestList());
        transaction.commit();
    }

    @Override
    public void requestFragmentInteraction(int i) {
        Bundle arguments = new Bundle();
        arguments.putInt("request", i);
        items.setArguments(arguments);

        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_fragment, items);
        transaction.commit();
    }


}
