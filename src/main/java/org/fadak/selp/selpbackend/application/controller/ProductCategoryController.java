package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.service.ProductCategoryService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.entity.ProductCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/productCategories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getProductCategories(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {

        long loginMemberId = userPrincipal.getId();
        List<ProductCategory> productCategories = productCategoryService.findAll();
        return ResponseEntity.ok(productCategories);
    }
}
