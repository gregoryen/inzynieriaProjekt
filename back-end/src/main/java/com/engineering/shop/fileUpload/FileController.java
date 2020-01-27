package com.engineering.shop.fileUpload;

import com.engineering.shop.imageProducts.ImageProduct;
import com.engineering.shop.imageProducts.ImageProductRepo;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private FileStorageService fileStorageService;
    private ProductsRepo productsRepo;
    private  ImageProductRepo imageProductRepo;

    @Autowired
    public FileController(FileStorageService fileStorageService, ProductsRepo productsRepo, ImageProductRepo imageProductRepo) {
        this.fileStorageService = fileStorageService;
        this.productsRepo = productsRepo;
        this.imageProductRepo = imageProductRepo;
    }

    @PostMapping("/uploadImage")
    public ImageProduct uploadFile(@RequestParam("file") MultipartFile file) {
        return fileStorageService.storeFile(file);
    }

    //NIEZALECANA (rozwaz uzycie /uploadImage)
    //nie wiem dlaczego ale jak sie nie da praamtru files to w prawdzie nie dodoa zadnych plikow
    //ale zwroci 200 jakby wszystko bylo dobrze
    @PostMapping("/uploadMultipleImages")
    public List<ImageProduct> uploadMultipleFiles(@RequestParam(name="files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }


    @GetMapping("/downloadAdditionalImage")
    public ResponseEntity<Resource> downloadAdditionalImage(@RequestParam Integer idImage, HttpServletRequest request) {

        Optional<ImageProduct>  image = imageProductRepo.findById(idImage);
        if(image.isEmpty()) {
            throw new MyFileNotFoundException("Image with id:  " + idImage + "dont exists");
        }
        return fetchImage(image.get(), request);
    }

    @GetMapping("/downloadMainImage")
    public ResponseEntity<Resource> downloadMainImage(@RequestParam Integer idProduct, HttpServletRequest request) {
        Optional<Product> product = productsRepo.findById(idProduct);
        if(product.isEmpty()) {
            throw new MyFileNotFoundException("Product with id:  " + idProduct + "not found");
        }

        Optional<ImageProduct>  mainImage = imageProductRepo.findById(product.get().getMainImage());
        if(mainImage.isEmpty()) {
            throw new MyFileNotFoundException("Product with id:  " + idProduct + "dont have main image");
        }
        return fetchImage(mainImage.get(), request);
    }

    private ResponseEntity<Resource> fetchImage(ImageProduct imageProduct, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(imageProduct.getImageName());

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
