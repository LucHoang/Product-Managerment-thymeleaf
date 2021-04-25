package com.controller;

import com.model.Product;
import com.model.ProductForm;
import com.service.IProductService;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import java.io.File;
import java.io.IOException;
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
        model.addAttribute("productForm", new ProductForm());
        return "/create";
    }

    @Value("${file-upload}")
    private String fileUpload;
    @PostMapping("/save")
    public String save(@ModelAttribute ProductForm productForm,  RedirectAttributes redirect, Model model) {
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (productForm.getName().trim().isEmpty()) {
            model.addAttribute("productForm", productForm);
            model.addAttribute("success", "Cac truong khong duoc de trong!");
            return "/create";
        }
        productForm.setId((int) (Math.random() * 10000));
        Product product = new Product(productForm.getId(), productForm.getName(), productForm.getPrice(), productForm.getQuantity(), productForm.getColor(),
                productForm.getDescription(), fileName);
        productService.insertUser(product);
        redirect.addFlashAttribute("success", "Them moi thanh cong!");
        return "redirect:/product";

//        if (product.getName().trim().isEmpty()) {
//            model.addAttribute("product", product);
//            model.addAttribute("success", "Cac truong khong duoc de trong!");
//            return "/create";
//        }
//        product.setId((int) (Math.random() * 10000));
//        productService.insertUser(product);
//        redirect.addFlashAttribute("success", "Them moi thanh cong!");
//        return "redirect:/product";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
//        Product product = productService.selectProduct(id);
//        ProductForm productForm = new ProductForm(product.getId(), product.getName(), product.getPrice(), product. getQuantity(), product.getColor(),
//                product.getDescription(), product.getCategory(), product.getImage());
        model.addAttribute("product", productService.selectProduct(id));
//        model.addAttribute("productForm", productForm);
        return "/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ProductForm productForm) {
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        productForm.setId((int) (Math.random() * 10000));
        Product product = new Product(productForm.getId(), productForm.getName(), productForm.getPrice(), productForm.getQuantity(), productForm.getColor(),
                productForm.getDescription(), fileName);

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
