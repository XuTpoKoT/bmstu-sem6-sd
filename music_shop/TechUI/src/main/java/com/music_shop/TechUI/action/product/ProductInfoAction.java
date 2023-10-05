package com.music_shop.TechUI.action.product;

import com.music_shop.BL.API.ProductService;
import com.music_shop.BL.model.Product;
import com.music_shop.TechUI.action.Action;
import com.music_shop.TechUI.session.Session;
import com.music_shop.TechUI.session.State;
import com.music_shop.TechUI.util.ProductPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class ProductInfoAction implements Action {
    private final ProductService productService;
    private final Session session;
    private final Scanner in = new Scanner(System.in);

    @Autowired
    public ProductInfoAction(ProductService productService, Session session) {
        this.productService = productService;
        this.session = session;
    }

    @Override
    public int perform() {
        try {
            System.out.println("Введите id товара:");
            UUID id = UUID.fromString(in.nextLine());
            Product p = productService.getProductById(id);
            ProductPrinter.printFull(p);
            session.setState(State.PRODUCT_MENU);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
