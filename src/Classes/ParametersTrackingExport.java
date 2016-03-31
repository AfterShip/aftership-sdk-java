package Classes;

import Classes.query.GetTrackingsParameters;

/**
 * Request parameters builder for the /exports route
 */
public final class ParametersTrackingExport extends GetTrackingsParameters {
    private String cursor;

    public ParametersTrackingExport() {}

    public ParametersTrackingExport(String cursor) {
        this.cursor = cursor;
    }

    public String getCursor() {
        return this.cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    /**
     * Create a QueryString with all the fields of this class different of Null
     *
     * @return the String with the param codified in the QueryString
     */
    public String generateQueryString() {
        QueryString qs = new QueryString();

        // build query
        if(this.cursor != null) qs.add("cursor", this.cursor);
        if(this.getLimit() > 0) qs.add("limit",Integer.toString(this.getLimit()));
        if (this.getKeyword() != null) qs.add("keyword", this.getKeyword());
        if (this.getSlugs() != null) qs.add("slug",this.getSlugs());
        if (this.getOrigin() != null) qs.add("origin", this.getOrigin());
        if (this.getDestinations() != null) qs.add("destination",this.getDestinations());
        if (this.getTags() != null) qs.add("tag",this.getTags());
        if (this.getCreatedAtMin() != null) qs.add("created_at_min", DateMethods.toString(this.getCreatedAtMin()));
        if (this.getCreatedAtMax() != null) qs.add("created_at_max", DateMethods.toString(this.getCreatedAtMax()));
        if (this.getFields() != null) qs.add("fields",this.getFields());
        if (this.getLang() != null)qs.add("lang", this.getLang());

        String generatedQuery = qs.getQuery();
        if(!generatedQuery.isEmpty()) {
            generatedQuery = '?' + generatedQuery;
            generatedQuery = generatedQuery.replaceFirst("&", "");
        }
        return generatedQuery;
    }
}
