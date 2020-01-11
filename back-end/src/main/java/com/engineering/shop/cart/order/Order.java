package com.engineering.shop.cart.order;



import com.engineering.shop.cart.bucket.Bucket;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="User_Order")
@AllArgsConstructor
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long orderId;

    @NotNull
    private Integer userId;
// Zakladalismy przekazanie klasy CustomerDetails
//    @NotNull
//    private Customer customerDetails;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private  String adress;
    @NotNull
    private String phoneNumber;
    @NotNull
    //private Integer orderBucketId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;
    @NotNull
    private BigDecimal orderValue;
    private Boolean isPaid;
    //private PaymentType paymentType;

}
