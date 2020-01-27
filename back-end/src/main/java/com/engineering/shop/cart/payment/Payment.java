package com.engineering.shop.cart.payment;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="payment")
@AllArgsConstructor
public class Payment extends RepresentationModel<Payment> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private Integer paymentId;
    private Integer orderId;
    private String paymentType;

    public Payment(){

    }

    public Payment(Integer orderId, String paymentType) {
        this.orderId = orderId;
        this.paymentType = paymentType;
    }
}
