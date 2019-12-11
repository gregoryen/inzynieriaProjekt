package com.engineering.shop;

import com.engineering.shop.fileUpload.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class RestApiApplication {

    public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
    }

}
