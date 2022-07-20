package com.aftership.sdk.model.estimateddeliverydate;

import lombok.Data;

@Data
public class Address {
  /**
   * The country/region of the origin location from where the package is
   * picked up by the carrier to be delivered to the final destination.
   * Use 3 letters of ISO 3166-1 country/region code.
   */
  private String country;

  /**
   * State, province, or the equivalent location of the origin address.
   * Either `origin_address.state` or `origin_address.postal_code` is required.
   */
  private String state;

  /**
   * City of the origin address.
   */
  private String postalCode;

  /**
   * Postal code of the origin address.
   * Either `origin_address.state` or `origin_address.postal_code` is required.
   */
  private String rawLocation;

  /**
   * Raw location of the origin address.
   */
  private String city;
}
