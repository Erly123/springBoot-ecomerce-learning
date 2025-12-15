package com.chl.ecomerce.controller.dto;

import java.util.List;
import java.util.UUID;

public record CreateOrderDto(UUID userId,
                             List<OrderItemDto> items
                             ) {

}
