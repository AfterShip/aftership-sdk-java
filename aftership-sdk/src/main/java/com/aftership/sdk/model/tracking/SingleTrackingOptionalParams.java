package com.aftership.sdk.model.tracking;

import java.util.HashMap;
import java.util.Map;
import com.aftership.sdk.endpoint.StringMap;
import lombok.Data;

/** SingleTrackingOptionalParams is the optional parameters in single tracking query */
@Data
public class SingleTrackingOptionalParams implements StringMap {
  /** The postal code of receiver's address. Required by some couriers, such asdeutsch-post */
  private String trackingPostalCode;

  /** Shipping date in YYYYMMDD format. Required by some couriers, such asdeutsch-post */
  private String trackingShipDate;

  /**
   * Destination Country of the shipment for a specific courier. Required by some couriers, such
   * aspostnl-3s
   */
  private String trackingDestinationCountry;

  /**
   * Account number of the shipper for a specific courier. Required by some couriers, such
   * asdynamic-logistics
   */
  private String trackingAccountNumber;

  /** Key of the shipment for a specific courier. Required by some couriers, such assic-teliway */
  private String trackingKey;

  /**
   * Origin Country of the shipment for a specific courier. Required by some couriers, such asdhl
   */
  private String trackingOriginCountry;

  /**
   * Located state of the shipment for a specific courier. Required by some couriers, such
   * asstar-track-courier
   */
  private String trackingState;

  /**
   * Generate a Map dictionary.
   *
   * @return StringStringMap
   */
  @Override
  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>(10);
    map.put("tracking_postal_code", this.getTrackingPostalCode());
    map.put("tracking_ship_date", this.getTrackingShipDate());
    map.put("tracking_destination_country", this.getTrackingDestinationCountry());
    map.put("tracking_account_number", this.getTrackingAccountNumber());
    map.put("tracking_key", this.getTrackingKey());
    map.put("tracking_origin_country", this.getTrackingOriginCountry());
    map.put("tracking_state", this.getTrackingState());
    return map;
  }
}
