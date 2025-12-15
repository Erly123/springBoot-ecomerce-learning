package com.chl.ecomerce.controller;

import com.chl.ecomerce.controller.dto.ApiResponse;
import com.chl.ecomerce.controller.dto.CreateOrderDto;
import com.chl.ecomerce.controller.dto.OrderSumaryDto;
import com.chl.ecomerce.controller.dto.PaginationResponseDto;
import com.chl.ecomerce.repository.OrderRepository;
import com.chl.ecomerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDto dto) {

        var order = orderService.createOrder(dto);

        return ResponseEntity.created(URI.create("/orders/" + order.getOrderId())).build();

    }
    @GetMapping
    public ResponseEntity<ApiResponse<OrderSumaryDto>> listOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        var resp = orderService.findAll(page, pageSize);

        return ResponseEntity.ok(new ApiResponse<>(
                resp.getContent(),
                new PaginationResponseDto(resp.getNumber(), resp.getSize(), resp.getTotalElements(), resp.getTotalPages())
        ));
    }
}

