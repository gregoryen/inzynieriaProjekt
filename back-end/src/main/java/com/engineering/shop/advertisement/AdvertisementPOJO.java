package com.engineering.shop.advertisement;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class AdvertisementPOJO {
    private String name;
    private String description;
    private LocalDateTime date;
    private Integer mainImage;
}
