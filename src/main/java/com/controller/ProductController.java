package com.controller;

import com.model.Product;
import com.service.IProductService;
import com.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final IProductService productService = new ProductService();

    @GetMapping("")
    public String index(Model model) {

        List<Product> productList = productService.selectAllUsers();
        model.addAttribute("listProduct", productList);
        return "/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "/create";
    }

    @PostMapping("/save")
    public String save(Product product,  RedirectAttributes redirect, Model model) {
        if (product.getName().trim().isEmpty()) {
//            redirect.addFlashAttribute("success", "Cac truong khong duoc de trong!");
            model.addAttribute("product", product);
            model.addAttribute("success", "Cac truong khong duoc de trong!");
//            return "redirect:/product/create";
            return "/create";
        }
        product.setId((int) (Math.random() * 10000));
        productService.insertUser(product);
        redirect.addFlashAttribute("success", "Them moi thanh cong!");
        return "redirect:/product";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.selectProduct(id));
        return "/edit";
    }

    @PostMapping("/update")
    public String update(Product product) {
        productService.update(product.getId(), product);
        return "redirect:/product";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.selectProduct(id));
        return "/delete";
    }

    @PostMapping("/delete")
    public String delete(Product product, RedirectAttributes redirect) {
        productService.deleteProduct(product.getId());
        redirect.addFlashAttribute("success", "Xoa san pham thanh cong!");
        return "redirect:/product";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.selectProduct(id));
        return "/view";
    }

    @GetMapping("/search")
    public String delete(@RequestParam String text, Model model, RedirectAttributes redirect) {
        List<Product> products = productService.searchByName(text);

        model.addAttribute("listProduct", products);
        return "/index";
    }
}
