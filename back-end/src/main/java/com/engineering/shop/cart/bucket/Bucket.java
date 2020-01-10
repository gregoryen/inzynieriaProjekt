package com.engineering.shop.cart.bucket;



import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.sun.istack.NotNull;
import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Entity
@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="Bucket")

public class Bucket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer bucketId;
    @OneToMany (mappedBy = "bucket")
    private Set<BucketPosition> bucketList;
    @NotNull
    private BigDecimal totalValue;

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
    public void increaseTotalValue(BigDecimal value){
        this.totalValue.add(value);
    }

}
