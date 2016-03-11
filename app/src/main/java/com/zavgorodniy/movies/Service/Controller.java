package com.zavgorodniy.movies.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 02.03.16.
 */
public class Controller {
    static private Controller controller;

    private static List<Item> items;

    private Controller(){

    }

    public void start(JsonReq.AsyncResult asyncResult, int value){
        items = new ArrayList<>();
        JsonReq jsonReq  = new JsonReq(asyncResult);
        jsonReq.execute(value);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(Item items) {
        this.items.add(items);
    }

    public static Controller getInstance() {
        return controller == null ? (controller = new Controller()) : controller;
    }
}
