package com.aftership.sdk.model.courier;

import java.util.List;
import lombok.Data;

/** Courier Object */
@Data
public class Courier {
  /** Unique code of courier */
  private String slug;

  /** Name of courier */
  private String name;

  /** Contact phone number of courier */
  private String phone;

  /** Other name of courier */
  private String otherName;

  /** Website link of courier */
  private String webUrl;

  /**
   * The extra fields need for tracking, such as `tracking_account_number`, `tracking_postal_code`,
   * `tracking_ship_date`, `tracking_key`, `tracking_destination_country`
   */
  private List<String> requiredFields;

  /**
   * the extra fields which are optional for tracking. Basically it's the same as required_fields,
   * but the difference is that only some of the tracking numbers require these fields.
   */
  private List<String> optionalFields;

  /** Default language of tracking results */
  private String defaultLanguage;

  /** Other supported languages */
  private List<String> supportLanguages;

  /** Country code (ISO Alpha-3) where the courier provides service */
  private List<String> serviceFromCountryIso3;
}
