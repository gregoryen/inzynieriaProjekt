package com.engineering.shop.categories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;


@Projection(name = "withId", types = {Category.class})
public interface CategoryWithIdProjection {
    @Value("#{target.id}")
    Integer getId();

    String getName();

    String getDescription();

    Integer getParentId();

    Integer getPreviousCategoryId();
}