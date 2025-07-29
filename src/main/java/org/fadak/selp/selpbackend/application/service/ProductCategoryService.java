package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> findAll();
}
