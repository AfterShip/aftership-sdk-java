package com.aftership.sdk.model.courier;

import lombok.Data;

import java.util.List;

/**
 * The response of courier list
 */
@Data
public class CourierList {
    /**
     * Total number of couriers supported by AfterShip.
     */
    private Integer total;

    /**
     * Array of Hash describes the couriers information.
     */
    private List<Courier> couriers;
}
