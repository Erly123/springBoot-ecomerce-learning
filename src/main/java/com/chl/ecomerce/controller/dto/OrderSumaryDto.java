package com.chl.ecomerce.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSumaryDto(Long orderId,
                                LocalDateTime orderData,
                                UUID userId,
                                BigDecimal total ) {
}
