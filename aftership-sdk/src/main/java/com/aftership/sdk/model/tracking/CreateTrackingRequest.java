package com.aftership.sdk.model.tracking;

import lombok.Data;

/**
 * Request body for CreateTracking
 */
@Data
public class CreateTrackingRequest {
    /**
     * NewTracking Object
     */
    private NewTracking tracking;
}
