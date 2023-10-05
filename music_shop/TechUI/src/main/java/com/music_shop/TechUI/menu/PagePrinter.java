package com.music_shop.TechUI.menu;

import com.music_shop.BL.API.ProductService;
import com.music_shop.BL.model.Product;
import com.music_shop.TechUI.session.Session;
import com.music_shop.TechUI.util.ProductPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class PagePrinter {
    private final Session session;
    private final ProductService productService;

    @Autowired
    public PagePrinter(Session session, ProductService productService) {
        this.session = session;
        this.productService = productService;
    }
    public void printPage() {
        try {
            switch (session.getState()) {
                case MAIN_MENU -> printMainPage();
                case BUCKET_MENU -> printBucketPage();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void printBucketPage() {
        Map<UUID, Integer> bucket = session.getBucket();
        if (bucket.isEmpty()) {
            System.out.println("Корзина пуста");
        } else {
            List<Product> products = productService.getProductsByIds(bucket.keySet().stream().toList());
            System.out.println("Корзина:");
            for (Product p : products) {
                System.out.println("Кол-во: " + bucket.get(p.getId()));
                ProductPrinter.printShort(p);
            }
        }
    }

    private void printMainPage() {
        List<Product> products = productService.getAllProducts();
        System.out.println("Доступтые товары:");
        for (Product p : products) {
            ProductPrinter.printShort(p);
        }
        session.updateProductIds(products);

    }
}
