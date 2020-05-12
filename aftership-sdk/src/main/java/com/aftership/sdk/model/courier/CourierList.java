package com.aftership.sdk.model.courier;

import java.util.List;
import lombok.Data;

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
