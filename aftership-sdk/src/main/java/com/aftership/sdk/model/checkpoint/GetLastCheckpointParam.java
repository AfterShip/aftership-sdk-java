package com.aftership.sdk.model.checkpoint;

import java.util.HashMap;
import java.util.Map;
import com.aftership.sdk.endpoint.StringMap;
import lombok.Data;

/**
 * GetLastCheckpointParam is the additional parameters in getLastCheckpoint
 */
@Data
public class GetLastCheckpointParam implements StringMap {
    /**
     * List of fields to include in the response. Use comma for multiple values. Fields to include:slug,created_at,
     * checkpoint_time,city,coordinates,country_iso3,
     * country_name,message,state,tag,zip
     * Default: none, Example: city,tag
     */
    private String fields;
    /**
     * Support Chinese to English translation for china-ems  and  china-post  only
     * (Example: en)
     */
    private String lang;

    /**
     * Generate a Map dictionary.
     *
     * @return Map<String, String>
     */
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("fields", this.getFields());
        map.put("lang", this.getLang());
        return map;
    }
}
