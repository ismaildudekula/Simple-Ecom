package com.ismail.SimpleEcom.service;


import com.ismail.SimpleEcom.model.Product;
import com.ismail.SimpleEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int productId) {
        return repo.findById(productId).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return repo.save(product);

    }


    public Product updateProduct(int productId, Product product, MultipartFile imageFile) throws IOException {

        product.setImageData(imageFile.getBytes());
        product.setImageType(imageFile.getContentType());
        product.setImageName(imageFile.getOriginalFilename());

        return repo.save(product);

    }

    public void deleteProduct(int productId) {
        repo.deleteById(productId);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
