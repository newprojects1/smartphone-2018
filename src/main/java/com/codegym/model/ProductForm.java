package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

public class ProductForm {
    private Long id;
    private String code;
    private String name;
    private Producer producer;
    private Category category;
    private String imageUrl;
    private MultipartFile image;
    private double price;
    private int memory;
    private int capacity;
    private int quantity;
    private String detail;

    public ProductForm() {
    }

    public ProductForm(Long id, String code, String name, Producer producer, Category category, String imageUrl, MultipartFile image, double price, int memory, int capacity, int quantity, String detail) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.producer = producer;
        this.category = category;
        this.imageUrl = imageUrl;
        this.image = image;
        this.price = price;
        this.memory = memory;
        this.capacity = capacity;
        this.quantity = quantity;
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
