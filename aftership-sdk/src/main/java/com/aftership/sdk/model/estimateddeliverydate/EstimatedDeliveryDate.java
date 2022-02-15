package com.aftership.sdk.model.estimateddeliverydate;

import lombok.Data;

/**
 * aftership_estimated_delivery_date information.
 */
@Data
public class EstimatedDeliveryDate {
    /**
     * The estimated arrival date of the shipment.
     *
     * <p>String
     */
    private String estimatedDeliveryDate;

    /**
     * The reliability of the estimated delivery date based on the trend of the transit time
     * for the similar delivery route and the carrier's delivery performance
     * range from 0.0 to 1.0 (Beta feature).
     *
     *
     * <p>Float
     */
    private Float confidenceScore;

    /**
     * Earliest estimated delivery date of the shipment.
     *
     * <p>String
     */
    private String estimatedDeliveryDateMin;

    /**
     * Latest estimated delivery date of the shipment.
     *
     * <p>String
     */
    private String estimatedDeliveryDateMax;
}
