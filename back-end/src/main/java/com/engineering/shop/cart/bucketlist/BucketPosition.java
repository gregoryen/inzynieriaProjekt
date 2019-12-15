package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.product.Product;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="BucketPosition")
public class BucketPosition extends RepresentationModel<BucketPosition> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @NotNull
    private Integer productId;
    @NotNull
    private Integer bucketId;
    @NotNull
    private Integer productQuantity;
    @NotNull
    private float productPrice;

    public BucketPosition(){

    }

    public BucketPosition( Integer productId, Integer productQuantity, Float price) {

        this.productId = productId;
        this.productQuantity = productQuantity;
        this.productPrice = price;
    }


}
