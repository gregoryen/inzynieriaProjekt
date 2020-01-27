package com.engineering.shop.imageAdv;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ImageAdv")
public class ImageAdv {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private Integer idAdv;
    private String extension;

    public ImageAdv() {
        this.idAdv = -1;
    }

    public ImageAdv(String extension) {
        this();
        this.extension = extension;
    }

    public String getImageName() {
        return this.id.toString() + "." + this.extension;
    }
}
