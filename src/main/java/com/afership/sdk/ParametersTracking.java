package com.afership.sdk;

import com.afership.sdk.query.GetTrackingsParameters;

/**
 * Keep the information for get trackings from the server, and interact with the results
 * Created by User on 13/6/14.
 */
public class ParametersTracking extends GetTrackingsParameters {
    public ParametersTracking() {
        this.setPage(1);
        this.setLimit(100);
    }

    /**
    * Create a QueryString with all the fields of this class different of Null
    *
    * @return the String with the param codified in the QueryString
    */
    public String generateQueryString() {

        QueryString qs = new QueryString("page", Integer.toString(this.getPage()));
        qs.add("limit",Integer.toString(this.getLimit()));

        if (this.getKeyword() != null) qs.add("keyword", this.getKeyword());
        if (this.getCreatedAtMin() != null) qs.add("created_at_min", DateMethods.toString(this.getCreatedAtMin()));
        if (this.getCreatedAtMax() != null) qs.add("created_at_max", DateMethods.toString(this.getCreatedAtMax()));
        if (this.getLang() != null)qs.add("lang", this.getLang());

        if (this.getSlugs() != null) qs.add("slug",this.getSlugs());

        if (this.getOrigin() != null) qs.add("origin", this.getOrigin());

        if (this.getDestinations() != null) qs.add("destination",this.getDestinations());

        if (this.getTags() != null) qs.add("tag",this.getTags());

        if (this.getFields() != null) qs.add("fields",this.getFields());

        //globalJSON.put("tracking", trackingJSON);

        return qs.getQuery();
    }
}

