package com.engineering.shop.cart.order;



import com.engineering.shop.cart.bucket.Bucket;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    private Integer orderId;
    @NotNull
    private String userId;
// Zakladalismy przekazanie klasy CustomerDetails
//    @NotNull
//    private Customer customerDetails;
    @NotNull
 //   @Size(min=2, max=40)
    private String firstName;
    @NotNull
 //   @Size(min=2, max=40)
    private String lastName;
    @NotNull
 //   @Size(min=5, max=90)
    private  String adress;
    @NotNull
    private String email;
    private String phoneNumber;
    @NotNull
    //private Integer orderBucketId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;
    private BigDecimal orderValue;
    private Boolean isPaid;
    //private PaymentType paymentType;

    public Order(){

    }


}
