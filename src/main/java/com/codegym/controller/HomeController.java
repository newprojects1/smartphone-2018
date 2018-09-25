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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProducerService producerService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute
    public Iterable<Producer> producers() {
        return producerService.findAll();
    }

    @GetMapping("/")
    public ModelAndView listHome(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/home");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/view-product/{id}")
    public ModelAndView showViewProductForm(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            ModelAndView modelAndView = new ModelAndView("/view-product");
            modelAndView.addObject("product", product);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/home");
            return modelAndView;
        }
    }
}
