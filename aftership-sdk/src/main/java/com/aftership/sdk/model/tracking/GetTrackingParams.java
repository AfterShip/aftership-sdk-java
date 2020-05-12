package com.aftership.sdk.model.tracking;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * GetTrackingParams is the additional parameters in single tracking query
 */
@Data
public class GetTrackingParams {
    /**
     * List of fields to include in the response.
     * Use comma for multiple values. Fields to include:
     * tracking_postal_code,tracking_ship_date,
     * tracking_account_number,tracking_key,
     * tracking_origin_country,tracking_destination_country,
     * tracking_state,title,order_id,tag,checkpoints,
     * checkpoint_time, message, country_name
     * Defaults: none, Example: title,order_id
     */
    private String fields;
    /**
     * Support Chinese to English translation for china-ems  and  china-post  only
     * (Example: en)
     */
    private String lang;

    /**
     * Generate a Map dictionary.
     * @return Map<String, String>
     */
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("fields", this.getFields());
        map.put("lang", this.getLang());
        return map;
    }
}
