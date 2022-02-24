package com.aftership.sdk.model.tracking;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * UpdateTracking represents an update to Tracking details
 */
@Data
public class UpdateTracking {
    /**
     * Email address(es) to receive email notifications. Accept either array or comma separated as
     * input.
     */
    private String[] emails;

    /**
     * Phone number(s) to receive sms notifications. Enter+ andarea code before phone number. Accept
     * either array or comma separated as input.
     */
    private String[] smses;

    /**
     * Title of the tracking. Default value astracking_numbers
     */
    private String title;

    /**
     * Customer name of the tracking.
     */
    private String customerName;

    /**
     * Enter ISO Alpha-3 (three letters) to specify the destination of the shipment (e.g. USA for
     * United States). If you use postal service to send international shipments, AfterShip will
     * automatically get tracking results at destination courier as well.
     */
    private String destinationCountryIso3;

    /**
     * Text field for order ID
     */
    private String orderId;

    /**
     * Text field for order path
     */
    private String orderIdPath;

    /**
     * Text field for order number
     *
     * <p>String
     */
    private String orderNumber;

    /**
     * Date and time of the order created
     *
     * <p>Date
     */
    private Date orderDate;

    /**
     * Custom fields that accept a hash with string, boolean or number fields
     */
    private Map<String, String> customFields;

    /**
     * ext field for the note
     */
    private String note;

    /**
     * Enter ISO 639-1 Language Code to specify the store, customer or order language.
     */
    private String language;

    /**
     * Promised delivery date of an order inYYYY-MM-DDformat.
     */
    private String orderPromisedDeliveryDate;

    /**
     * Shipment delivery type
     *
     * <p>pickup_at_store pickup_at_courier door_to_door
     */
    private String deliveryType;

    /**
     * Shipment pickup location for receiver
     */
    private String pickupLocation;

    /**
     * Shipment pickup note for receiver
     */
    private String pickupNote;
}
