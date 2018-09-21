package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Producer;
import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.service.CategoryService;
import com.codegym.service.ProducerService;
import com.codegym.service.ProductService;
import com.codegym.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("producers")
    public Iterable<Producer> producers() {
        return producerService.findAll();
    }

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    //    show list
    @GetMapping("/products")
    public ModelAndView listProducts(@RequestParam("s") Optional<String> s, Pageable pageable) {
        Page<Product> products;
        if (s.isPresent()) {
            products = productService.findAllByNameContainingOrCodeContainingOrProducer(s.get(), s.get(), s.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }
        ModelAndView modelAndView1 = new ModelAndView("/product/list");
        modelAndView1.addObject("products", products);
        return modelAndView1;
    }

    //    create
    @GetMapping("/create-product")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView1 = new ModelAndView("/product/create");
        modelAndView1.addObject("productForm", new ProductForm());
        return modelAndView1;
    }

    @PostMapping("/create-product")
    public ModelAndView saveProduct(@ModelAttribute("productForm") ProductForm productForm) {
        ModelAndView modelAndView1 = new ModelAndView("/product/create");

        try {
            String randomCode = UUID.randomUUID().toString();
            String originFileName = productForm.getImage().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
            productForm.getImage().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));

            Product product = new Product();
            product.setCode(productForm.getCode());
            product.setName(productForm.getName());
            product.setCategory(productForm.getCategory());
            product.setProducer(productForm.getProducer());
            product.setPrice(productForm.getPrice());
            product.setMemory(productForm.getMemory());
            product.setStorage(productForm.getStorage());
            product.setQuantity(productForm.getQuantity());
            product.setDetail(productForm.getDetail());
            product.setImage(randomName);

            productService.save(product);
            product.setCode("SS-" + product.getId());
            productService.save(product);
        } catch (IOException e) {
            e.printStackTrace();
        }

        modelAndView1.addObject("productForm", new ProductForm());
        modelAndView1.addObject("message", "New customer created successfully");
        return modelAndView1;
    }
}