package com.ismail.SimpleEcom.repo;


import com.ismail.SimpleEcom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM product WHERE " +
            "LOWER(name) LIKE LOWER(CONCAT('%',?1,'%')) OR " +
            "LOWER(description) LIKE LOWER(CONCAT('%',?1,'%')) OR " +
            "LOWER(brand) LIKE LOWER(CONCAT('%',?1,'%')) OR " +
            "LOWER(category) LIKE LOWER(CONCAT('%',?1,'%'))",

            nativeQuery = true)
    List<Product> searchProducts(String keyword);

}
