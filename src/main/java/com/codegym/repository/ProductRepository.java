package com.codegym.repository;

import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByNameContainingOrCodeContaining(String name, String code, Pageable pageable);
}
