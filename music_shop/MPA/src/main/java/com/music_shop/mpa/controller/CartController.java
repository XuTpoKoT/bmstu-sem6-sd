package com.music_shop.mpa.controller;

import com.music_shop.BL.API.CartService;
import com.music_shop.BL.API.DeliveryPointService;
import com.music_shop.BL.log.Logger;
import com.music_shop.BL.log.LoggerImpl;
import com.music_shop.BL.model.DeliveryPoint;
import com.music_shop.BL.model.Product;
import com.music_shop.BL.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final Logger log = new LoggerImpl(getClass().getName());
    private final CartService cartService;
    private final DeliveryPointService deliveryPointService;

    @Autowired
    public CartController(CartService cartService, DeliveryPointService deliveryPointService) {
        this.cartService = cartService;
        this.deliveryPointService = deliveryPointService;
    }

    @GetMapping()
    public String getProductsInCart(Model model) {
        try {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            Map<Product, Integer> productsInCart = cartService.getProductsInCart(login);
            model.addAttribute("productsInCart", productsInCart);

            List<DeliveryPoint> deliveryPoints = deliveryPointService.getAllDeliveryPoints();
            model.addAttribute("deliveryPoints", deliveryPoints);
        } catch (Exception e) {
            log.error("getProductsInCart failed ", e);
        }

        return "cart";
    }

    @PostMapping()
    public String addProductToCart(@RequestHeader String referer,
                                   @RequestParam String productId,
                                   @RequestParam(defaultValue = "1") int count) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.addProductToCart(login, UUID.fromString(productId), count);

        return "redirect:"+ referer;
    }

    @DeleteMapping()
    public String deleteProductFromCart(@RequestParam String productId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.removeProductFromCart(login, UUID.fromString(productId));

        return "redirect:cart";
    }

    @PutMapping()
    public String updateProductInCart(@RequestParam String productId,
                                      @RequestParam(name = "cnt") Integer count) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.updateProductInCart(login, UUID.fromString(productId), count);

        return "redirect:cart";
    }
}
