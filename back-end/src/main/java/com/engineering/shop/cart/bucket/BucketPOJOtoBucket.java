//package com.engineering.shop.cart.bucket;
//
//import com.engineering.shop.cart.bucketlist.BucketPosition;
//import com.engineering.shop.cart.bucketlist.BucketPositionRepo;
//import com.engineering.shop.common.Transformer;
//import org.apache.commons.collections4.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class BucketPOJOtoBucket implements Transformer<BucketPOJO, Bucket> {
//
//    private BucketPositionRepo bucketPositionRepo;
//
//    @Autowired
//    public BucketPOJOtoBucket(BucketPositionRepo bucketPositionRepo) {
//        this.bucketPositionRepo = bucketPositionRepo;
//    }
//
//    @Override
//    public Bucket transform(BucketPOJO pojo) {
//        return Bucket.builder()
//                .bucketId(pojo.getBucketId())
//                .positions(getPositions(pojo.getBucketPositions()))
//                .totalValue(pojo.getTotalValue())
//                .build();
//    }
//
//    private Set<BucketPosition> getPositions(Set<Integer> ids) {
//        return CollectionUtils.isNotEmpty(ids) ? ids.stream()
//                .map(id -> bucketPositionRepo
//                        .findById(id)).filter(Optional::isPresent)
//                        .map(Optional::get).collect(Collectors.toSet()) : new HashSet<>();
//    }
//
//    private BigDecimal getTotalValue(Set<BucketPosition> bucketList){
//        BigDecimal value = new BigDecimal(0);
//        BigDecimal total = new BigDecimal(0);
//
////        Iterator<BucketPosition> itr = bucketList.iterator();
////
//////        while(itr.hasNext()){
//////            value.multiply(itr.getClass().getMethod().getProductPrice())
//////        }
//
//        return total;
//    }
//}
