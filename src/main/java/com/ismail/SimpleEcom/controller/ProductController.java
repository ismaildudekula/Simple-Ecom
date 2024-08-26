package com.ismail.SimpleEcom.controller;

import com.ismail.SimpleEcom.model.Product;
import com.ismail.SimpleEcom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public String greet(){
        return "Bismillah";
    }

    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @RequestMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId){
        Product product = productService.getProductById(productId);
        if(product != null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        else{
            return new  ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){

        try {
            Product product1 = productService.addProduct(product,imageFile);
            return new ResponseEntity<Product>(product1,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int productId){

        Product product = productService.getProductById(productId);
        byte[] imageFile = product.getImageData();
         return ResponseEntity.ok()
                 .contentType(MediaType.valueOf(product.getImageType()))
                 .body(imageFile);

    }

    @PutMapping("product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestPart Product product, @RequestPart MultipartFile imageFile){

        Product product1 = null;
        try {
            product1 = productService.updateProduct(productId,product,imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }
        if(product1 != null){
            return new ResponseEntity("Updated", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId){
        Product product = productService.getProductById(productId);
        if(product != null){
            productService.deleteProduct(productId);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Failed to Delete",HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products = productService.searchProducts(keyword);
        return  new ResponseEntity<>(products,HttpStatus.OK);
    }






}
