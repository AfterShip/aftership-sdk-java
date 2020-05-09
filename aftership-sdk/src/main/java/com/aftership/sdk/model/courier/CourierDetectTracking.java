package com.aftership.sdk.model.courier;

import com.aftership.sdk.model.annotation.IndefiniteString;
import lombok.Data;

import java.util.List;

/**
 * The tracking object in couriers detect request
 */
@Data
public class CourierDetectTracking {
    /**
     * Tracking number of a shipment. (Required)
     * <p>String</p>
     */
    private String trackingNumber;
    /**
     * The postal code of receiver's address. Required by some couriers, such asdeutsch-post
     * <p>String</p>
     */
    private String trackingPostalCode;
    /**
     * Shipping date inYYYYMMDDformat. Required by some couriers, such asdeutsch-post
     * <p>String</p>
     */
    private String trackingShipDate;
    /**
     * Account number of the shipper for a specific courier. Required by some couriers, such asdynamic-logistics
     * <p>String</p>
     */
    private String trackingAccountNumber;
    /**
     * Key of the shipment for a specific courier. Required by some couriers, such assic-teliway
     * <p>String</p>
     */
    private String trackingKey;
    /**
     * Destination Country of the shipment for a specific courier. Required by some couriers, such aspostnl-3s
     * <p>String</p>
     */
    private String trackingDestinationCountry;
    /**
     * If not specified, Aftership will automatically detect the courier based on the tracking number format and your selected couriers.
     * Use array or comma separated to input a list of couriers for auto detect.
     * <p><b>Array or String</b></p>
     */
    @IndefiniteString
    private List<String> slug;
}
