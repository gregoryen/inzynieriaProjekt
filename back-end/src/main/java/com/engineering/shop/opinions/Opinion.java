package com.engineering.shop.opinions;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="Opinie")

public class Opinion {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotNull
    private Integer productId;

    @NotNull
    private String clientEmail;

    @NotNull
    private Integer starsNumber;

    @NotNull
    private String description;

    @ElementCollection
    private List<String> likesList;

    @ElementCollection
    private List<String> dislikesList;

    public Opinion(){

    }

    public Opinion(Integer id, Integer productId, String clientEmail, Integer starsNumber, String description, List<String> likesList, List<String> dislikesList) {
        this.id = id;
        this.productId = productId;
        this.clientEmail = clientEmail;
        this.starsNumber = starsNumber;
        this.description = description;
        this.likesList = likesList;
        this.dislikesList = dislikesList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Integer getStarsNumber() {
        return starsNumber;
    }

    public void setStarsNumber(Integer starsNumber) {
        this.starsNumber = starsNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<String> likesList) {
        this.likesList = likesList;
    }

    public List<String> getDislikesList() {
        return dislikesList;
    }

    public void setDislikesList(List<String> dislikesList) {
        this.dislikesList = dislikesList;
    }
}
