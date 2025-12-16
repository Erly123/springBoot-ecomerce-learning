package com.chl.ecomerce.controller;

import com.chl.ecomerce.controller.dto.*;
import com.chl.ecomerce.repository.OrderRepository;
import com.chl.ecomerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/orders")
@Tag(
        name = "Pedidos",
        description = "Operações relacionadas aos pedidos e seus itens"
)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(
            summary = "Criar um novo pedido",
            description = "Realiza o cadastro de um novo pedido com seus respectivos itens"
    )
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDto dto) {

        var order = orderService.createOrder(dto);

        return ResponseEntity.created(URI.create("/orders/" + order.getOrderId())).build();

    }
    @GetMapping
    @Operation(
            summary = "Listar pedidos",
            description = "Retorna uma lista paginada de pedidos cadastrados no sistema"
    )
    public ResponseEntity<ApiResponse<OrderSumaryDto>> listOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        var resp = orderService.findAll(page, pageSize);

        return ResponseEntity.ok(new ApiResponse<>(
                resp.getContent(),
                new PaginationResponseDto(resp.getNumber(), resp.getSize(), resp.getTotalElements(), resp.getTotalPages())
        ));
    }
    @GetMapping("/{orderId}")
    @Operation(
            summary = "Buscar pedido por ID",
            description = "Consulta os detalhes de um pedido a partir do seu identificador"
    )
    public ResponseEntity<OrderResponseDto> findById(@PathVariable("orderId") Long orderId) {

        var order = orderService.findById(orderId);

        return order.isPresent() ?
                ResponseEntity.ok(OrderResponseDto.fromEntity(order.get())) :
                ResponseEntity.notFound().build();
    }
}

