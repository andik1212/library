package com.example.andik1212.helper;

import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Vadim
 * Date: 19.11.12
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class Article {
    private String title;
    private String date;
    private String content;

    private String id;



    public Article(JSONObject jo){
        setTitle(jo.optJSONObject("title").optString("$t"));
        setDate(jo.optJSONObject("published").optString("$t"));
        setContent(jo.optJSONObject("content").optString("$t"));
        setId(jo.optJSONObject("id").optString("$t"));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}