package com.aftership.sdk.model.tracking;

import lombok.Data;

import java.util.Map;

/**
 * UpdateTracking represents an update to Tracking details
 */
@Data
public class UpdateTracking {
  /**
   * Phone number(s) to receive sms notifications. Enter+ andarea code before phone number. Accept
   * either array or comma separated as input.
   */
  private String[] smses;

  /**
   * Email address(es) to receive email notifications. Accept either array or comma separated as
   * input.
   */
  private String[] emails;

  /**
   * Title of the tracking. Default value astracking_numbers
   */
  private String title;

  /**
   * Customer name of the tracking.
   */
  private String customerName;

  /**
   * Text field for order ID
   */
  private String orderId;

  /**
   * Text field for order path
   */
  private String orderIdPath;

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

  /**
   * Unique code of each courier. Provide a single courier.
   */
  private String slug;

  /**
   * Account number of the shipper for a specific courier. Required by some couriers, such
   * asdynamic-logistics
   *
   * <p>String
   */
  private String trackingAccountNumber;

  /**
   * Origin Country of the shipment for a specific courier. Required by some couriers, such asdhl
   *
   * <p>String
   */
  private String trackingOriginCountry;

  /**
   * Destination Country of the shipment for a specific courier. Required by some couriers, such
   * aspostnl-3s
   *
   * <p>String
   */
  private String trackingDestinationCountry;

  /**
   * Key of the shipment for a specific courier. Required by some couriers, such assic-teliway
   *
   * <p>String>
   */
  private String trackingKey;

  /**
   * The postal code of receiver's address. Required by some couriers, such asdeutsch-post
   *
   * <p>String
   */
  private String trackingPostalCode;

  /**
   * Shipping date inYYYYMMDDformat. Required by some couriers, such asdeutsch-post
   *
   * <p>String
   */
  private String trackingShipDate;

  /**
   * Located state of the shipment for a specific courier. Required by some couriers, such
   * asstar-track-courier
   *
   * <p>String
   */
  private String trackingState;

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
  private String orderDate;

  /**
   * The carrier’s shipment type. When you input this field, AfterShip will not get updates from the carrier.
   */
  private String shipmentType;
}
