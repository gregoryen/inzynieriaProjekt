package com.engineering.shop.cart.bucket;

import com.engineering.shop.cart.Exceptions.BucketException;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="bucket_id")
    private Integer bucketId;
    private String token;
    private BigDecimal totalValue;
 //   @OneToMany(mappedBy = "bucket")
    @OneToMany(mappedBy ="bucket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BucketPosition> positions;


    public Bucket(){

    }

    public Bucket(String token) {
        this.token = token;
        this.totalValue = new BigDecimal(0);
        this.positions = new ArrayList<>(); //new HashSet<>();
    }

    public void addToTotalValue (BigDecimal value) {
        System.out.println(this.totalValue);
        this.totalValue = this.totalValue.add(value);
        System.out.println(this.totalValue);

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

    public void removeFromPositions(BucketPosition pos){
        for (BucketPosition it : positions){
            positions.remove(it);
        }
    }
}
