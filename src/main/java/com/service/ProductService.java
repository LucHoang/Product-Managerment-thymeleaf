package com.service;

import com.model.Category;
import com.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService implements IProductService{
    private static final Map<Integer, Product> products;

    static {

        products = new HashMap<>();
        products.put(1, new Product(1, "Iphone 11", 799, 12, "Purple, Yellow, Green", "Dien thoai iphone"));
        products.put(2, new Product(2, "Iphone 11 Pro", 1100, 12, "Black, Yellow, Green", "Dien thoai iphone"));
        products.put(3, new Product(3, "Iphone X", 749, 13, "Coral, Blue, Green", "Dien thoai iphone"));
        products.put(4, new Product(4, "Smart TV man hinh cong 4K UHD inch RU7300", 1000, 5, "Black", "Tivi man hinh cong"));
        products.put(5, new Product(5, "Samsung galaxy S10", 420, 10, "Prism White, Yellow", "Dien thoai samsung"));
    }

    @Override
    public List<Product> selectAllUsers() {
        return new ArrayList<>(products.values());
    }

    @Override
    public void insertUser(Product product) {
        products.put(product.getId(),product);
    }

    @Override
    public Category selectCategoryByProductId(int id) {
        return null;
    }

    @Override
    public List<Category> getAllCategory() {
        return null;
    }

    @Override
    public Product selectProduct(int pid) {
        return products.get(pid);
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    @Override
    public void deleteProduct(int id) {
        products.remove(id);
    }

    @Override
    public List<Product> searchByName(String textSearch) {
        List<Product> productList = selectAllUsers();
        List<Product> products = new ArrayList<>();
        for (Product product: productList) {
            if (product.getName().toLowerCase().contains(textSearch.toLowerCase())) {
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public void update(int id, Product product) {
        products.put(id,product);
    }
}
