package com.chl.ecomerce.controller.dto;

import com.chl.ecomerce.entities.ProductEntity;
import com.chl.ecomerce.entities.TagEntity;

import java.util.List;

public record ProductResponseDto(Long productId,
                                 String productName,
                                 List<TagResponseDto> tags) {
    public static ProductResponseDto fromEntity(ProductEntity product) {
        return new ProductResponseDto(
                product.getProductId(),
                product.getProductName(),
                getTags(product.getTags())
        );
    }

    private static List<TagResponseDto> getTags(List<TagEntity> tags) {
        return tags.stream()
                .map(TagResponseDto::fromEntity)
                .toList();
    }
}
