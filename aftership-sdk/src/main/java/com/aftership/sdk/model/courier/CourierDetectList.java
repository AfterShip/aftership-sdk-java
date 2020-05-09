package com.aftership.sdk.model.courier;

import com.aftership.sdk.model.tracking.Tracking;
import lombok.Data;

import java.util.List;

/**
 * The response of couriers detect request
 */
@Data
public class CourierDetectList {
    /**
     * Total number of matched couriers
     */
    private Integer total;
    /**
     * Hash describes the tracking information.
     * <p>Hash of Tracking Object</p>
     */
    private Tracking tracking;
    /**
     * A list of matched couriers based on tracking number format.
     * <p>Array of Courier Object</p>
     */
    private List<Courier> couriers;
}
