package code;

import org.json.JSONObject;

/**
 * Created by User on 10/6/14.
 */
public class Courier {
    private String slug;
    private String name;
    private String phone;
    private String other_name;
    private String web_url;

    public Courier(String web_url, String slug, String name, String phone, String other_name) {
        this.web_url = web_url;
        this.slug = slug;
        this.name = name;
        this.phone = phone;
        this.other_name = other_name;
    }
    public Courier(JSONObject jsonCourier){
       this.web_url = jsonCourier.has("web_url")?jsonCourier.getString("web_url"):"";
        this.slug =  jsonCourier.has("slug")?jsonCourier.getString("slug"):"";
        this.name = jsonCourier.has("name")?jsonCourier.getString("name"):"";
        this.phone = jsonCourier.has("phone")?jsonCourier.getString("phone"):"";
        this.other_name = jsonCourier.has("other_name")?jsonCourier.getString("other_name"):"";

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
}
