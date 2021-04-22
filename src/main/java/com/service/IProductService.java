package com.service;

import com.model.Category;
import com.model.Product;

import java.util.List;

public interface IProductService {
    public List<Product> selectAllUsers();
    public void insertUser(Product product);
    public Category selectCategoryByProductId(int id);
    public List<Category> getAllCategory();
    public Product selectProduct(int pid);
    public boolean updateProduct(Product product);
    public void deleteProduct(int id);
    public List<Product> searchByName(String textSearch);
    public void update(int id, Product product);
}
