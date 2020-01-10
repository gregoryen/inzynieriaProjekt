package com.engineering.shop.categories;

import lombok.Data;

@Data
public class DeleteCategoryMessage {
    String message;

    public DeleteCategoryMessage(String message) {
        this.message = message;
    }
}
