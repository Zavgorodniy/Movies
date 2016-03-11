package com.zavgorodniy.movies.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.zavgorodniy.movies.Adapter.ItemListAdapter;
import com.zavgorodniy.movies.R;
import com.zavgorodniy.movies.Service.Controller;
import com.zavgorodniy.movies.Service.Item;
import com.zavgorodniy.movies.Service.JsonReq;

import java.util.ArrayList;
import java.util.List;

public class ItemList extends ListFragment implements JsonReq.AsyncResult  {

    List<Item> itemList;
    String[] rangeList;
    ItemListAdapter itemsAdapter;
    ArrayAdapter<String> rangeListAdapter;
    Controller controller;

    public ItemList() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        itemList = new ArrayList<>();
        itemsAdapter = new ItemListAdapter(getActivity(), R.layout.item, itemList);
        setListAdapter(itemsAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list, container, false);

        Spinner rangeFilter = (Spinner) view.findViewById(R.id.sp_range);
        rangeList = getResources().getStringArray(R.array.st_years);
        rangeListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, rangeList);
        rangeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rangeFilter.setAdapter(rangeListAdapter);
        rangeFilter.setOnItemSelectedListener(new OnSpinnerClick());
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item item = (Item) getListView().getItemAtPosition(position);
    }

    public void sendRequest(int request) {
        itemsAdapter.clear();
        controller.start(this, request);
    }

    @Override
    public void onResult(List<Item> Item) {
        itemList = controller.getItems();
        itemsAdapter.addAll(itemList);
        itemsAdapter.notifyDataSetChanged();
    }

    class OnSpinnerClick implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            List<Item> list = new ArrayList<>();

            int from = 0;
            int to = 2050;
            switch(position) {
                case 0:
                    list = itemList;
                    break;
                case 1:
                    from = 1960;
                    to = 1980;
                    break;
                case 2:
                    from = 1980;
                    to = 1990;
                    break;
                case 3:
                    from = 1990;
                    to = 2000;
                    break;
                case 4:
                    from = 2000;
                    to = 2005;
                    break;
                case 5:
                    from = 2005;
                    to = 2010;
                    break;
                case 6:
                    from = 2010;
                    to = 2015;
                    break;
                case 7:
                    from = 2015;
                    to = 2016;
                    break;
            }

            if (position != 0) {
                for (Item item : itemList) {
                    int year = Integer.parseInt(item.getDate().substring(0, 4));
                    if (from < year && year <= to)
                        list.add(item);
                }
            }
            itemsAdapter.clear();
            itemsAdapter.addAll(list);
            itemsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
