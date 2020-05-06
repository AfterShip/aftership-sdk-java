package com.aftership.sdk.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateLimit {
    private Long reset;
    private Integer limit;
    private Integer remaining;
}
