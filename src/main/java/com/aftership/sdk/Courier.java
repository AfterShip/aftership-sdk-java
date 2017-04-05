package com.aftership.sdk;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Define a Courier
 * Created by User on 10/6/14.
 */
public class Courier {

    /** Unique code of courier */
    private String slug;
    /** Name of courier */
    private String name;
    /** Contact phone number of courier */
    private String phone;
    /** Other name of courier, if several they will be separated by commas */
    private String other_name;
    /** Website link of courier */
    private String web_url;
    /** Require fields for this courier */
    private List<String> requireFields;

    /** Default constructor with all the fields of the class */
    public Courier(String web_url, String slug, String name, String phone, String other_name) {
        this.web_url = web_url;
        this.slug = slug;
        this.name = name;
        this.phone = phone;
        this.other_name = other_name;
    }

    /**
     * Constructor, creates a Courier from a JSONObject with the information of the Courier,
     * if any field is not specified it will be ""
     *
     * @param jsonCourier   A JSONObject with information of the Courier
     * by the API.
     **/
    public Courier(JSONObject jsonCourier) throws JSONException {
        this.web_url = jsonCourier.has("web_url")  && !jsonCourier.isNull("web_url")?jsonCourier.getString("web_url"):"";
        this.slug =  jsonCourier.has("slug")  && !jsonCourier.isNull("slug")?jsonCourier.getString("slug"):"";
        this.name = jsonCourier.has("name") && !jsonCourier.isNull("name")?jsonCourier.getString("name"):"";
        this.phone = jsonCourier.has("phone") && !jsonCourier.isNull("phone")?jsonCourier.getString("phone"):"";
        this.other_name = jsonCourier.has("other_name") && !jsonCourier.isNull("other_name")?jsonCourier.getString("other_name"):"";

        JSONArray requireFieldsArray =jsonCourier.isNull("required_fields")?null:jsonCourier.getJSONArray("required_fields");
        if(requireFieldsArray !=null && requireFieldsArray.length()!=0){
            this.requireFields = new ArrayList<String>();
            for (int i=0;i<requireFieldsArray.length();i++){
                this.requireFields.add(requireFieldsArray.get(i).toString());
            }
        }

    }

    @Override
    public String toString() {
        return "Courier{" +
                "slug='" + slug + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", other_name='" + other_name + '\'' +
                ", web_url='" + web_url + '\'' +
                '}';
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public List<String> getRequireFields() {
        return this.requireFields;
    }

    public void addRequierField(String requierField) {

        if (this.requireFields == null) {
            this.requireFields = new ArrayList<String>();
            this.requireFields.add(requierField);
        } else
            this.requireFields.add(requierField);
    }

    public void deleteRequierField(String requierField) {
        if (this.requireFields != null) {
            this.requireFields.remove(requierField);
        }
    }

    public void deleteRequierFields() {
        this.requireFields = null;
    }

}
