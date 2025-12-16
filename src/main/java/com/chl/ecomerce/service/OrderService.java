package com.chl.ecomerce.service;

import com.chl.ecomerce.controller.dto.CreateOrderDto;
import com.chl.ecomerce.controller.dto.OrderItemDto;
import com.chl.ecomerce.controller.dto.OrderSumaryDto;
import com.chl.ecomerce.entities.*;
import com.chl.ecomerce.exception.CreateOrderException;
import com.chl.ecomerce.repository.OrderItemRepository;
import com.chl.ecomerce.repository.OrderRepository;
import com.chl.ecomerce.repository.ProductRepository;
import com.chl.ecomerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(UserRepository userRepository,
                        OrderRepository orderRepository,
                        ProductRepository productRepository,
                        OrderItemRepository orderItemRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public OrderEntity createOrder(CreateOrderDto dto) {

        var order = new OrderEntity();

        // 1. Validate User
        var user = validateUser(dto);

        // 2. Validate Order Items
        var orderItems = validateOrderItems(order, dto);

        // 3. Calculate order total
        var total =  calculateOrderTotal(orderItems);

        // 4. Map to Entity
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setItems(orderItems);
        order.setTotal(total);

        // 5. Repository save
        return orderRepository.save(order);
    }

    public UserEntity validateUser(CreateOrderDto dto) {
        return userRepository.findById(dto.userId())
                .orElseThrow(() ->  new CreateOrderException("User not found"));
    }

    private List<OrderItemEntity> validateOrderItems(OrderEntity order,
                                                 CreateOrderDto dto) {

        if(dto.items().isEmpty()) {
            throw new CreateOrderException("order items is empty");
        }

        return dto.items()
                .stream()
                .map(ordemItemDto -> getOrderItem(order, ordemItemDto))
                .toList();
    }

    private OrderItemEntity getOrderItem(OrderEntity order,
                                         OrderItemDto ordemItemDto) {
        var orderItemEntity = new OrderItemEntity();
        var id = new OrderItemId();
        var product = getProduct(ordemItemDto.productId());

        id.setOrder(order);
        id.setProduct(product);

        orderItemEntity.setId(id);
        orderItemEntity.setQuantity(ordemItemDto.quantity());
        orderItemEntity.setSalePrice(product.getPrice());

        return orderItemEntity;
    }

    private ProductEntity getProduct(Long productId) {
        return  productRepository.findById(productId)
                .orElseThrow(() -> new CreateOrderException("product not found"));
    }
    private BigDecimal calculateOrderTotal(List<OrderItemEntity> items) {
        return items.stream()
                .map(i -> i.getSalePrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public Page<OrderSumaryDto> findAll(Integer page, Integer pageSize) {

        return orderRepository.findAll(PageRequest.of(page, pageSize))
                .map(entity -> {
                    return new OrderSumaryDto(
                            entity.getOrderId(),
                            entity.getOrderDate(),
                            entity.getUser().getUserId(),
                            entity.getTotal()
                    );
                });
    }

    public Optional<OrderEntity> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
