package com.engineering.shop.cart.bucket;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
//@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="Bucket")
//@RequiredArgsConstructor Sprawdzic czy POJO nadal bedzise sie z tym gryzlo
public class Bucket {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="bucket_id")
    private Integer bucketId;
    private BigDecimal totalValue;
 //   @OneToMany(mappedBy = "bucket")
    @OneToMany(mappedBy ="bucket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BucketPosition> positions;

    public Bucket(){

    }

    public Bucket(int bucketId) {
        this.bucketId = bucketId;
        this.totalValue = new BigDecimal(0);
        this.positions = new ArrayList<>(); //new HashSet<>();
    }

    public void addToTotalValue (BigDecimal value) {
        System.out.println(this.totalValue);
        this.totalValue = this.totalValue.add(value);
        System.out.println(this.totalValue);

    }

    public boolean substructFromTotalValue (BigDecimal value) {
        if (this.totalValue.subtract(value).equals(new BigDecimal(0))) {
            this.totalValue = totalValue.subtract(value);
            return true;
        } else {
            return false;
        }
    }

    public void addToPositions(BucketPosition pos) {
        positions.add(pos);
    }

    public void removeFromPositions(BucketPosition pos){
        for (BucketPosition it : positions){
            positions.remove(it);
        }
    }
}
