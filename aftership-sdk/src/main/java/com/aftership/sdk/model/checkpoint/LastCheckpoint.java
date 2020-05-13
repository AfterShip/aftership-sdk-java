package com.aftership.sdk.model.checkpoint;

import lombok.Data;

/**
 * LastCheckpoint is the last checkpoint API response
 */
@Data
public class LastCheckpoint {
    /**
     * id of tracking
     */
    private String id;
    /**
     * Tracking number.
     */
    private String trackingNumber;
    /**
     * Unique code of courier.
     */
    private String slug;
    /**
     * Current status of tracking. Values include
     * Pending
     * InfoReceived
     * InTransit
     * OutForDelivery
     * AttemptFail
     * Delivered
     * AvailableForPickup
     * Exception
     * Expired
     * (See tag definition)
     */
    private String tag;
    /**
     * Current subtag of tracking. (See subtag definition)
     */
    private String subtag;
    /**
     * Normalized tracking message. (See subtag message definition)
     */
    private String subtagMessage;
    /**
     * Hash describes the checkpoint information.
     */
    private Checkpoint checkpoint;
}
