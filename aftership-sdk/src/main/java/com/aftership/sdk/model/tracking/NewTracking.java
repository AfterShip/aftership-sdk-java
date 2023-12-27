package com.aftership.sdk.model.tracking;

import java.util.Map;

import lombok.Data;

/**
 * NewTracking provides parameters for new Tracking API request
 */
@Data
public class NewTracking {

  /**
   * Tracking number of a shipment.
   *
   * <p>Duplicated tracking numbers, tracking numbers with invalid tracking number format will not
   * be accepted.
   *
   * <p>We only accept tracking numbers with length from 4 to 100
   *
   * <p>We currently support the following characters in a tracking number:
   *
   * <p>A - Z 0 - 9 - (Hyphen) . (Period) _ (Underscore) / (Slash)
   */
  private String trackingNumber;

  /**
   * Unique code of each courier. Provide a single courier or array for a list of couriers. If you
   * do not specify a slug, Aftership will automatically detect the courier based on the tracking
   * number format and your selected couriers. Get a list of courier slug using GET /couriers
   */
  private String[] slug;

  /**
   * Title of the tracking. Default value astracking_numbers
   */
  private String title;

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
   * Account number of the shipper for a specific courier. Required by some couriers, such
   * asdynamic-logistics
   */
  private String trackingAccountNumber;

  /**
   * Origin Country of the shipment for a specific courier. Required by some couriers, such asdhl
   */
  private String trackingOriginCountry;

  /**
   * Destination Country of the shipment for a specific courier. Required by some couriers, such
   * aspostnl-3s
   */
  private String trackingDestinationCountry;

  /**
   * Key of the shipment for a specific courier. Required by some couriers, such assic-teliway
   */
  private String trackingKey;

  /**
   * The postal code of receiver's address. Required by some couriers, such asdeutsch-post
   */
  private String trackingPostalCode;

  /**
   * Shipping date inYYYYMMDDformat. Required by some couriers, such asdeutsch-post
   */
  private String trackingShipDate;

  /**
   * Located state of the shipment for a specific courier. Required by some couriers, such
   * asstar-track-courier
   */
  private String trackingState;

  /**
   * Apple iOS device IDs to receive the push notifications. Accept either array or comma separated
   * as input.
   */
  private String[] ios;

  /**
   * Google cloud message registration IDs to receive the push notifications. Accept either array or
   * comma separated as input.
   */
  private String[] android;

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
   * Customer name of the tracking.
   */
  private String customerName;

  /**
   * Enter ISO Alpha-3 (three letters) to specify the origin of the shipment (e.g. USA for United
   * States).
   */
  private String originCountryIso3;

  /**
   * Enter ISO Alpha-3 (three letters) to specify the destination of the shipment (e.g. USA for
   * United States). If you use postal service to send international shipments, AfterShip will
   * automatically get tracking results at destination courier as well.
   */
  private String destinationCountryIso3;

  /**
   * ext field for the note
   */
  private String note;

  /**
   * Slug group is a group of slugs which belong to same courier. For example, when you inpit "fedex-group" as slug_group, AfterShip will detect the tracking with "fedex-uk", "fedex-fims", and other slugs which belong to "fedex". It cannot be used with slug at the same time.
   */
  private String slugGroup;

  /**
   * Date and time of the order created
   *
   * <p>Date
   */
  private String orderDate;

  /**
   * Text field for order number
   *
   * <p>String
   */
  private String orderNumber;

  /**
   * The carrier’s shipment type. When you input this field, AfterShip will not get updates from the carrier.
   */
  private String shipmentType;

  /**
   * Used to add tags to your shipments to help categorize and filter them easily.
   */
  private String[] shipmentTags;
}
