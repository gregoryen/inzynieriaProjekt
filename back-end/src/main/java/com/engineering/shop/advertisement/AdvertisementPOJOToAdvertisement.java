package com.engineering.shop.advertisement;

import com.engineering.shop.advertisement.Advertisement;
import com.engineering.shop.advertisement.AdvertisementPOJO;

import com.engineering.shop.common.Transformer;

import org.springframework.stereotype.Service;


@Service
public class AdvertisementPOJOToAdvertisement implements Transformer<AdvertisementPOJO, Advertisement> {

    @Override
    public Advertisement transform(AdvertisementPOJO pojo) {
        return Advertisement.builder()
                .name(pojo.getName())
                .description(pojo.getDescription())
                .mainImage(pojo.getMainImage())
                .date(pojo.getDate())
                .build();
    }


}
