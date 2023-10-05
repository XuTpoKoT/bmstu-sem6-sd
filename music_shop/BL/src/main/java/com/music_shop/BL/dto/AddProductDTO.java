package com.music_shop.BL.dto;

import lombok.Builder;

import java.util.UUID;

public record AddProductDTO(String name, int price, String description, String color,
                            UUID manufacturerId) {
    @Builder
    public AddProductDTO {
    }
}
