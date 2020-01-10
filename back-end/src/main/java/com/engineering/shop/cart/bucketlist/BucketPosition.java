package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.products.Product;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="bucket_position")
@AllArgsConstructor
public class BucketPosition extends RepresentationModel<BucketPosition> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @ManyToOne
    private Product product;
   // @NotNull
    private String productName;
    @NotNull
    private BigDecimal productPrice;
    @NotNull
    private int productQuantity;

    @ManyToOne
    @JoinColumn(name="bucket_id", nullable = false)
    private Bucket bucket;

    public BucketPosition(){

    }

    public BucketPosition( Product product, int productQuantity, Bucket bucket) {

        this.product = product;
        this.productName = product.getName();
        this.productQuantity = productQuantity;
        this.productPrice = product.getPrice();
        this.bucket = bucket;

    }


}
