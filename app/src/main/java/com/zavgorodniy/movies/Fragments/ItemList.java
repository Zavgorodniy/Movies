package com.zavgorodniy.movies.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.zavgorodniy.movies.Adapter.ItemListAdapter;
import com.zavgorodniy.movies.ItemInfo;
import com.zavgorodniy.movies.R;
import com.zavgorodniy.movies.Service.Controller;
import com.zavgorodniy.movies.Service.Item;
import com.zavgorodniy.movies.Service.JsonReq;
import com.zavgorodniy.movies.Service.RequestItem;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class ItemList extends ListFragment implements JsonReq.AsyncResult {

    List<Item> items = new ArrayList<>();
    String[] rangeList;
    ItemListAdapter itemsAdapter;
    ArrayAdapter<String> rangeListAdapter;
    Controller controller;
    static int request;
    static int filter;

    public ItemList() {
    }

    public static ItemList newInstance(int req, int filt) {
        request = req;
        filter = filt;
        ItemList itemList = new ItemList();
        Bundle args = new Bundle();
        args.putInt("request", req);
        args.putInt("filter", filt);
        itemList.setArguments(args);
        return itemList;
    }

    public int getRequest() {
        return getArguments().getInt("request", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list, container, false);

        if (request != 0)
            sendRequest();

        itemsAdapter = new ItemListAdapter(getActivity(), R.layout.item, items);
        setListAdapter(itemsAdapter);

        Spinner rangeFilter = (Spinner) view.findViewById(R.id.sp_range);
        rangeList = getResources().getStringArray(R.array.st_years);
        rangeListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, rangeList);
        rangeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rangeFilter.setAdapter(rangeListAdapter);
        rangeFilter.setOnItemSelectedListener(new OnSpinnerClick());

        return view;
    }

    public void sendRequest() {
        controller = Controller.getInstance();
        controller.start(this, request);
    }

    @Override
    public void onResult(List<Item> Item) {
        items = controller.getItems();
        doFilter(filter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item item = (Item) getListView().getItemAtPosition(position);
        Intent itemInfo = new Intent(getActivity(), ItemInfo.class);

        itemInfo.putExtra("name", item.getName());
        itemInfo.putExtra("genre", parseGenres(item.getGenre()));
        itemInfo.putExtra("date", item.getDate());
        itemInfo.putExtra("rating", item.getRating());
        itemInfo.putExtra("description", item.getDescription());
        itemInfo.putExtra("imageId", item.getImageId());

        startActivity(itemInfo);
    }

    class OnSpinnerClick implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            doFilter(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private String parseGenres(String st) {
        StringBuilder genreSt = new StringBuilder();
        String[] genresId = st.substring(1, st.length() - 1).split(",");

        try {
            XmlPullParser parser = getResources().getXml(R.xml.requests);

            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("request")) {
                    for (String genre: genresId) {
                        if (parser.getAttributeValue(2).equals(genre)) {
                            genreSt.append(parser.getAttributeValue(0));
                            genreSt.append(", ");
                        }
                    }
                }
                parser.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        genreSt.setLength(genreSt.length() - 2);
        return genreSt.toString();
    }

    public void doFilter(int position) {
        List<Item> list = new ArrayList<>();

        int from = 0;
        int to = 2050;
        switch(position) {
            case 0:
                list = items;
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
            for (Item item : items) {
                int year = Integer.parseInt(item.getDate().substring(0, 4));
                if (from < year && year <= to)
                    list.add(item);
            }
        }
        itemsAdapter.clear();
        itemsAdapter.addAll(list);
        itemsAdapter.notifyDataSetChanged();
    }

    public interface ItemFragmentListener {
        void itemFragmentInteraction(int i);
    }
}
