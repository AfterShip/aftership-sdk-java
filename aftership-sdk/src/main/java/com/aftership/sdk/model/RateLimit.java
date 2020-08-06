package com.aftership.sdk.model;

import lombok.*;

/** Status of Rate Limit */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateLimit {
  /** The unix timestamp when the rate limit will be reset. */
  private Long reset;
  /** The rate limit ceiling for your account per sec. */
  private Integer limit;
  /** The number of requests left for the 1 second window. */
  private Integer remaining;
}
