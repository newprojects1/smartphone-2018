package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Producer;
import com.codegym.model.Product;
import com.codegym.service.CategoryService;
import com.codegym.service.ProducerService;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public Iterable<Producer> producers() {
        return producerService.findAll();
    }

    @ModelAttribute
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
}