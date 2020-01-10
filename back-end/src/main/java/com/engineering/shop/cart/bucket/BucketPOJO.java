package com.engineering.shop.cart.bucket;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class BucketPOJO {

    private Set<Integer> bucketPositions;
    private BigDecimal totalValue;

}
