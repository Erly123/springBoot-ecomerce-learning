package com.chl.ecomerce.controller.dto;

import com.chl.ecomerce.entities.TagEntity;

public record TagResponseDto(Long tagId,
                             String name) {
    public static TagResponseDto fromEntity(TagEntity entity) {
        return new TagResponseDto(
                entity.getTagId(),
                entity.getName()
        );
    }
}
