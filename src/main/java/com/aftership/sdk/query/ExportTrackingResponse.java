package com.aftership.sdk.query;

import com.aftership.sdk.Tracking;

import java.util.List;

/**
 * Tracking response for the /exports endpoint.
 */
public class ExportTrackingResponse implements TrackingResponse {
    private List<Tracking> trackings;
    private String cursor;

    /**
     * @constructor
     * @param trackings - tracking list
     * @param cursor - pointer to the last retrieved tracking. This should be passed in subsequent /exports requests
     */
    public ExportTrackingResponse(List<Tracking> trackings, String cursor) {
        this.trackings = trackings;
        this.cursor = cursor;
    }

    public List<Tracking> getTrackingData() {
        return trackings;
    }

    public String getCursor() {
        String cursor = null;

        // api returns empty string cursor when there is no data left
        if(this.cursor != null && !this.cursor.isEmpty()) {
            cursor = this.cursor;
        }

        return cursor;
    }
}
