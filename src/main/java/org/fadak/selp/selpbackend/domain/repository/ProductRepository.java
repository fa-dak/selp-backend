package org.fadak.selp.selpbackend.domain.repository;

import java.util.List;
import org.fadak.selp.selpbackend.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Product> findRandomProducts(@Param("count") int count);

}