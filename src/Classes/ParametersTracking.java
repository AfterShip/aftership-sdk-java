package Classes;

import Enums.Field;
import Enums.ISO3Country;
import Enums.StatusTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/**
 * Created by User on 13/6/14.
 */
public class ParametersTracking implements Iterable<Tracking> {

    /**
     * Page to show. (Default: 1)
     */
    private int page;

    /**
     * Number of trackings each page contain. (Default: 100)
     */
    private int limit;

    /**
     * Search the content of the tracking record fields: trackingNumber, title, orderId, customerName,
     * customFields, orderId, emails, smses
     */
    private String keyword;

    /**
     * Unique courier code Use comma for multiple values. (Example: dhl,ups,usps)
     */
    private List<String> slugs;

    /**
     * Origin country of trackings. Use ISO Alpha-3 (three letters). Use comma for multiple values. (Example: USA,HKG)
     */
    private List<ISO3Country> origin;

    /**
     * Destination country of trackings. Use ISO Alpha-3 (three letters). Use comma for multiple values. (Example: USA,HKG)
     */
    private List<ISO3Country> destination;

    /**
     * Current status of tracking.
     */
    private List<StatusTag> tags;

    /**
     * Start date and time of trackings created. AfterShip only stores data of 90 days. (Defaults: 30 days ago, Example: 2013-03-15T16:41:56+08:00)
     */
    private String createdAtMin;

    /**
     * End date and time of trackings created. (Defaults: now, Example: 2013-04-15T16:41:56+08:00)
     */
    private String createdAtMax;

    /**
     * List of fields to include in the response. Use comma for multiple values. Fields to include: title, orderId, tag, checkpoints,
     * checkpointTime, message, countryName. (Defaults: none, Example: title,orderId)
     */
    private List<Field> fields;

    /**
     * Default: '' / Example: 'en' Support Chinese to English translation for  china-ems  and  china-post  only
     */
    private String lang;

    private List<Tracking> buffer;

    private int position;

    private int total;

    private ConnectionAPI connectionApi;

    public ParametersTracking() {
        this.page = 1;
        this.limit = 100;
    }

    public Iterator<Tracking> iterator() {
        // TODO Auto-generated method stub
        return new Iterator<Tracking>() {

            @Override
            public boolean hasNext() {

                return position<total;

            }

            @Override
            public Tracking next(){

                position++;
                if(position<page*limit) {
                    return ParametersTracking.this.buffer.get(position - (page - 1) * limit);
                }
                else{
                    try{//I dont like this at all!! look another way
                    page++;
                    ParametersTracking.this.connectionApi.getTracking(ParametersTracking.this);
                    }catch (Exception e){
                         System.out.println(e.getMessage());
                    }
                    return ParametersTracking.this.buffer.get(position - (page - 1) * limit);

                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

    }

    public void addSlug(String slug) {

        if (this.slugs == null) {
            this.slugs = new ArrayList<String>();
            this.slugs.add(slug);
        } else {
            this.slugs.add(slug);
        }
    }

    public void deleteSlug(String slug) {
        if (this.slugs != null) {
            this.slugs.remove(slug);
        }
    }

    public void deleteSlug() {
        this.slugs = null;
    }

    public void addOrigin(ISO3Country origin) {

        if (this.origin == null) {
            this.origin = new ArrayList<ISO3Country>();
            this.origin.add(origin);
        } else {
            this.origin.add(origin);
        }
    }

    public void deleteOrigin(ISO3Country origin) {
        if (this.origin != null) {
            this.origin.remove(origin);
        }
    }

    public void deleteOrigin() {
        this.origin = null;
    }

    public void addDestination(ISO3Country destination) {

        if (this.destination == null) {
            this.destination = new ArrayList<ISO3Country>();
            this.destination.add(destination);
        } else {
            this.destination.add(destination);
        }
    }

    public void deleteDestination(ISO3Country destination) {
        if (this.destination != null) {
            this.destination.remove(destination);
        }
    }

    public void deleteDestination() {
        this.destination = null;
    }

    public void addTag(StatusTag tag) {

        if (this.tags == null) {
            this.tags = new ArrayList<StatusTag>();
            this.tags.add(tag);
        } else {
            this.tags.add(tag);
        }
    }

    public void deleteTag(StatusTag tag) {
        if (this.tags != null) {
            this.tags.remove(tag);
        }
    }

    public void deleteTags() {
        this.tags = null;
    }

    public void addField(Field field) {

        if (this.fields == null) {
            this.fields = new ArrayList<Field>();
            this.fields.add(field);
        } else {
            this.fields.add(field);
        }
    }

    public void deleteField(Field field) {
        if (this.fields != null) {
            this.fields.remove(field);
        }
    }

    public void deleteFields() {
        this.fields = null;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCreatedAtMin() {
        return createdAtMin;
    }

    public void setCreatedAtMin(String createdAtMin) {
        this.createdAtMin = createdAtMin;
    }

    public String getCreatedAtMax() {
        return createdAtMax;
    }

    public void setCreatedAtMax(String createdAtMax) {
        this.createdAtMax = createdAtMax;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<Tracking> getBuffer() {
        return buffer;
    }

    public void setBuffer(List<Tracking> buffer) {
        this.buffer = buffer;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ConnectionAPI getConnectionApi() {
        return connectionApi;
    }

    public void setConnectionApi(ConnectionAPI connectionApi) {
        this.connectionApi = connectionApi;
    }

    public String generateQueryString() {

        QueryString qs = new QueryString("page", Integer.toString(this.page));
        qs.add("limit",Integer.toString(this.limit));

        if (this.keyword != null) qs.add("keyword", this.keyword);
        if (this.createdAtMin != null) qs.add("created_at_min", this.createdAtMin);
        if (this.createdAtMax != null) qs.add("created_at_max", this.createdAtMax);
        if (this.lang != null)qs.add("lang", this.lang);

        if (this.slugs != null) qs.add("slug",this.slugs);

        if (this.origin != null) qs.add("origin", this.origin);

        if (this.destination != null) qs.add("destination",this.destination);

        if (this.tags != null) qs.add("tag",this.tags);

        if (this.fields != null) qs.add("fields",this.fields);

        //globalJSON.put("tracking", trackingJSON);

        return qs.getQuery();
    }
}


class QueryString {

    private String query = "";

    public QueryString(String name, String value) {
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
            query +=URLEncoder.encode(name, "UTF-8");
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
