package com.engineering.shop.cart.bucket;


import com.engineering.shop.cart.bucketlist.BucketPosition;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;


@Entity
//@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name="Bucket")
//@RequiredArgsConstructor Sprawdzic czy POJO nadal bedzise sie z tym gryzlo
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="bucket_id")
    private Integer bucketId;
    private String token;
    private BigDecimal totalValue;
    @OneToMany(mappedBy ="bucket", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    private List<BucketPosition> positions;


    public Bucket(){

    }

    public Bucket(String token) {
        this.token = token;
        this.totalValue = new BigDecimal(0);
        this.positions = new ArrayList<>(); //new HashSet<>();
    }

    public int getUniqueItemsNumber () {
        if (this.positions != null)
            return this.positions.size();
        return 0;
    }

    public void addToTotalValue (BigDecimal value) {
//        System.out.println(this.totalValue);
        this.totalValue = this.totalValue.add(value);
//        System.out.println(this.totalValue);

    }

    public boolean substructFromTotalValue (BigDecimal value) {
        //System.out.println(value);
        if (this.totalValue.subtract(value).compareTo(new BigDecimal(0)) >= 0) {
            this.totalValue = totalValue.subtract(value);
            //System.out.println("Odejmowanie" + this.totalValue);
            return true;
        } else {
           // System.out.println("Nie da rady odjac");
            return false;
        }
    }

    public void addToPositions(BucketPosition pos) {
        positions.add(pos);
    }

    public boolean removeFromPositions(BucketPosition pos){

        int deletedId = pos.getProduct().getId();
        int counter = 0;
        for (BucketPosition it : positions) {
            if(it.getProduct().getId() == deletedId) {
                positions.remove(counter);
                return true;
            }
            counter ++;
        }

        return false;
    }
}
