package com.aftership.sdk.model.courier;

import lombok.Data;

import java.util.List;

@Data
public class CourierList {
    private Integer total;
    private List<Courier> couriers;
}
