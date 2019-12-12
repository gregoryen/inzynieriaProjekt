package com.engineering.shop.products;

import com.engineering.shop.imageProducts.ImageProduct;
import javafx.scene.paint.ImagePattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Product {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String name;
    private String description;
    private Integer categoryId;
    private BigDecimal price;
    private String reference;
    private String isbn;
    private String ean13;
    private boolean active;
    private Integer mainImageId;
}
