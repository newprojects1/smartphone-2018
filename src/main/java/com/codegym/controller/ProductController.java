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
            products = productService.findAllByNameContainingOrCodeContaining(s.get(), s.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    //    create
    @GetMapping("/create-product")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productForm", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/create-product")
    public ModelAndView saveProduct(@ModelAttribute("productForm") ProductForm productForm) {
        ModelAndView modelAndView = new ModelAndView("/product/create");

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

        modelAndView.addObject("productForm", new ProductForm());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;
    }

    //    edit
    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        ProductForm productForm = new ProductForm();

        productForm.setId(product.getId());
        productForm.setCode(product.getCode());
        productForm.setName(product.getName());
        productForm.setCategory(product.getCategory());
        productForm.setProducer(product.getProducer());
        productForm.setPrice(product.getPrice());
        productForm.setMemory(product.getMemory());
        productForm.setStorage(product.getStorage());
        productForm.setQuantity(product.getQuantity());
        productForm.setDetail(product.getDetail());
        productForm.setImageUrl(product.getImage());

        ModelAndView modelAndView;
        if (product != null) {
            modelAndView = new ModelAndView("/product/edit");
            modelAndView.addObject("productForm", productForm);
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("/product/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-product/{id}")
    public ModelAndView updateProduct(@ModelAttribute("productForm") ProductForm productForm, @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        Product product = productService.findById(id);

        if (!productForm.getImage().isEmpty()) {
            StorageUtils.removeFeature(product.getImage());
            String randomCode = UUID.randomUUID().toString();
            String originFilename = productForm.getImage().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFilename);
            try {
                productForm.getImage().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            product.setImage(randomName);
            productForm.setImageUrl(randomName);
        }
        product.setCode(productForm.getCode());
        product.setName(productForm.getName());
        product.setCategory(productForm.getCategory());
        product.setProducer(productForm.getProducer());
        product.setPrice(productForm.getPrice());
        product.setMemory(productForm.getMemory());
        product.setStorage(productForm.getStorage());
        product.setQuantity(productForm.getQuantity());
        product.setDetail(productForm.getDetail());

        productService.save(product);
        product.setCode("SS-" + product.getId());

        modelAndView.addObject("productForm", productForm);
        modelAndView.addObject("message", "This product has been up to date successfully");
        return modelAndView;
    }
}