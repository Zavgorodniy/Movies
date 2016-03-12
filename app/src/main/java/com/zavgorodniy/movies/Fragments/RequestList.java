package com.zavgorodniy.movies.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.zavgorodniy.movies.Adapter.RequestListAdapter;
import com.zavgorodniy.movies.R;
import com.zavgorodniy.movies.Service.RequestItem;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class RequestList extends ListFragment {

    List<RequestItem> requestItems;

    RequestFragmentListener rfl;

    public RequestList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requestItems = initRequestList();

        RequestListAdapter requestsAdapter = new RequestListAdapter(getActivity(),
                R.layout.request_item, requestItems);
        setListAdapter(requestsAdapter);

        return inflater.inflate(R.layout.request_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RequestItem requestItem = (RequestItem) getListView().getItemAtPosition(position);
        int req = Integer.parseInt(requestItem.request);
        if (req == 0)
            Toast.makeText(getActivity(), "Доступно в платном обновлении!", Toast.LENGTH_SHORT).show();
        else {
            rfl = (RequestFragmentListener) getActivity();
            rfl.requestFragmentInteraction(req);
        }
    }

    /** Function to fill initial list with request items from xml file */
    private List<RequestItem> initRequestList() {
        List<RequestItem> requestItems = new ArrayList<>();

        try {
            XmlPullParser parser = getResources().getXml(R.xml.requests);

            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("request")) {
                    requestItems.add(new RequestItem(parser.getAttributeValue(0),
                            parser.getAttributeValue(1),
                            parser.getAttributeValue(2)));
                }
                parser.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return requestItems;
    }

    public interface RequestFragmentListener {
        void requestFragmentInteraction(int i);
    }
}
