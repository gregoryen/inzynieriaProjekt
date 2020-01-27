package com.engineering.shop.advImageUpload;

import com.engineering.shop.imageAdv.*;
import com.engineering.shop.advertisement.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/advImages")
public class FileAdvController {

    private static final Logger logger = LoggerFactory.getLogger(FileAdvController.class);

    private FileAdvStorageService fileAdvStorageService;
    private AdvertisementRepo advertisementRepo;
    private  ImageAdvRepo imageAdvRepo;

    @Autowired
    public FileAdvController(FileAdvStorageService fileAdvStorageService, AdvertisementRepo advertisementRepo, ImageAdvRepo imageAdvRepo) {
        this.fileAdvStorageService = fileAdvStorageService;
        this.advertisementRepo = advertisementRepo;
        this.imageAdvRepo = imageAdvRepo;
    }

    @PostMapping("/uploadImage")
    public ImageAdv uploadFile(@RequestParam("file") MultipartFile file) {
        return fileAdvStorageService.storeFile(file);
    }



    @GetMapping("/downloadMainImage")
    public ResponseEntity<Resource> downloadMainImage(@RequestParam Integer idAdv, HttpServletRequest request) {
        Optional<Advertisement> adv = advertisementRepo.findById(idAdv);
        if(adv.isEmpty()) {
            throw new MyFileAdvNotFoundException("Product with id:  " + idAdv + "not found");
        }

        Optional<ImageAdv>  mainImage = imageAdvRepo.findById(adv.get().getMainImage());
        if(mainImage.isEmpty()) {
            throw new MyFileAdvNotFoundException("Product with id:  " + idAdv + "dont have main image");
        }
        return fetchImage(mainImage.get(), request);
    }

    private ResponseEntity<Resource> fetchImage(ImageAdv imageProduct, HttpServletRequest request) {
        Resource resource = fileAdvStorageService.loadFileAsResource(imageProduct.getImageName());

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
