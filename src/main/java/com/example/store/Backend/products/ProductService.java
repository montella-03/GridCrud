package com.example.store.Backend.products;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class ProductService implements CrudListener<Product> {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Collection<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Product productToUpdate = productRepository.findByName(product.getFarm())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
       return  productRepository.save(productToUpdate);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);

    }
}
