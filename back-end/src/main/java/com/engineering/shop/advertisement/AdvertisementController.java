package com.engineering.shop.advertisement;

import com.engineering.shop.advertisement.AdvertisementRepo;
import com.engineering.shop.imageAdv.ImageAdv;
import com.engineering.shop.imageAdv.ImageAdvRepo;
import com.engineering.shop.imageProducts.ImageProduct;
import com.engineering.shop.imageProducts.ImageProductRepo;
import com.engineering.shop.products.exception.ProductCreateException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    private AdvertisementRepo advertisementRepo;
    private ImageAdvRepo imageAdvRepo;
    private AdvertisementPOJOToAdvertisement advertisementPOJOToAdvertisement;

    @Autowired
    public AdvertisementController(AdvertisementRepo advertisementRepo, ImageAdvRepo imageAdvRepo, AdvertisementPOJOToAdvertisement advertisementPOJOToAdvertisement) {
        this.advertisementRepo = advertisementRepo;
        this.imageAdvRepo = imageAdvRepo;
        this.advertisementPOJOToAdvertisement = advertisementPOJOToAdvertisement;
    }

    @CrossOrigin
    @PostMapping
    public Advertisement addProduct(@RequestBody AdvertisementPOJO advPOJO) {
       Advertisement adv = advertisementPOJOToAdvertisement.transform(advPOJO);
        Integer mainImage = adv.getMainImage();
        Advertisement savedAdv = advertisementRepo.save(adv);

        Optional<ImageAdv> temp = imageAdvRepo.findById(mainImage);
        if (temp.isPresent()) {
            temp.get().setIdAdv(savedAdv.getId());
            imageAdvRepo.save(temp.get());
        }

        return savedAdv;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getCode();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}

