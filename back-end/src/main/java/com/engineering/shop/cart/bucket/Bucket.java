package com.engineering.shop.cart.bucket;

import com.engineering.shop.cart.Exceptions.BucketException;
import com.engineering.shop.cart.bucketlist.BucketPosition;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Console;
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
 //   @OneToMany(mappedBy = "bucket")
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
    //public boolean removeFromPositions(Integer id){
//        for (BucketPosition it : positions){
////            if (it.getId() == id) {
////                System.out.println(it.getId() == id);
////                positions.remove(it);
////                return true;
////            }
////        }
////        return false;

//        Iterator itr = positions.iterator();
//        while(itr.hasNext()){
//            if(itr.next().equals(pos))
//                itr.remove();
//                return true;
//        }
        if (positions.size()>1) {
            for (int i = 0 ; i < positions.size(); i++) {
                if (Objects.equals(pos, positions.get(i))){

                    System.out.println(positions.get(i+1).getId());
                    positions.remove(i+1);
                }
            }
        } else {
            for (int i = 0 ; i < positions.size(); i++) {
                if (Objects.equals(pos, positions.get(i))){

                    System.out.println(positions.get(i).getId());
                    positions.remove(i);
                }
            }
        }


        return true;
    }
}
