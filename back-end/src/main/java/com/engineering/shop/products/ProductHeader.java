package com.engineering.shop.products;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductHeader {
    private Integer id;
    private String name;
    private BigDecimal price;
}
