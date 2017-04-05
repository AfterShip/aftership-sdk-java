package com.aftership.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Class with different methods to construct a QueryString
 * Created by User on 13/6/14.
 */
class QueryString {

    private String query = "";

    //careful, this constructor creates the first element with &
    public QueryString(){}
    public QueryString(String name, String value) {
        encode(name, value);
    }
    public QueryString(String name, List<?> list) {
        String value = list.toString().replace("[", "").replace("]","").replace(" ","");
        encode(name, value);
    }

    public void add(String name, List<?> list) {
        query += "&";
        String value = list.toString().replace("[", "").replace("]","");
        encode(name, value);
    }

    public void add(String name, String value) {
        query += "&";
        encode(name, value);
    }

    private void encode(String name, String value) {
        try {
            query += URLEncoder.encode(name, "UTF-8");
            query += "=";
            query += URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    public String getQuery() {
        return query;
    }

    public String toString() {
        return getQuery();
    }
}
