package com.engineering.shop.cart.bucket;

import com.engineering.shop.cart.bucketlist.BucketPosition;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private BigDecimal totalValue;
    @OneToMany(mappedBy = "bucket")
    private Set<BucketPosition> positions;

    public void addToPositions(BucketPosition pos) {
        positions.add(pos);
    }

    public void removeFromPositions(BucketPosition pos){
        for (BucketPosition it : positions){
            positions.remove(it);
        }
    }
}
