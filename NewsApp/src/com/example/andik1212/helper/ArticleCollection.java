package com.example.andik1212.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Vadim
 * Date: 19.11.12
 * Time: 22:25
 * To change this template use File | Settings | File Templates.
 */
public class ArticleCollection extends Vector{
    public static void setLength(int length) {
        length = length;
    }

    public int length = 0;
    public static ArticleCollection fromJson(JSONObject jo) throws JSONException {
        ArticleCollection collection = new ArticleCollection();

        JSONObject jsonFeed = jo.getJSONObject("feed");
        JSONArray jsonEntry = jsonFeed.getJSONArray("entry");

        for (int i = 0; i < jsonEntry.length(); i++) {
            collection.add(new Article(jsonEntry.getJSONObject(i)));
            collection.elementAt(i);
            ArticleCollection.setLength(i);
        }

        return collection;
    }

}
