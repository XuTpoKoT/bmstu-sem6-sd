package com.music_shop.mpa.controller;

import com.music_shop.BL.API.FavouriteProductService;
import com.music_shop.BL.API.ProductService;
import com.music_shop.BL.log.Logger;
import com.music_shop.BL.log.LoggerImpl;
import com.music_shop.BL.model.Product;
import com.music_shop.BL.model.User;
import com.music_shop.mpa.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ProductController {
    private final Logger log = new LoggerImpl(getClass().getName());
    private final ProductService productService;
    private final FavouriteProductService favouriteProductService;

    @Autowired
    public ProductController(ProductService productService, FavouriteProductService favouriteProductService) {
        this.productService = productService;
        this.favouriteProductService = favouriteProductService;
    }

    @GetMapping()
    public String getProductsByPageNumber(Model model,
                                          @RequestParam(name = "pageNumber", defaultValue = "1") int page) {
        final int pageSize = 6;
        try {
            int countProducts = productService.getCountProducts();
            PaginationHelper.addPaginationInfoToModel(countProducts, page, pageSize, model);
            int skip = (page - 1) * pageSize;
            List<Product> products = productService.getProductsBySkipAndLimit(skip, pageSize);
            model.addAttribute("products", products);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return "home";
    }

    @GetMapping("products/{id}")
    public String getProductInfo(@PathVariable String id, Model model) {
        try {
            Product product = productService.getProductById(UUID.fromString(id));
            model.addAttribute("product", product);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Set<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            if (roles.contains(User.Role.CUSTOMER.name())) {
                List<Product> favouriteProducts = favouriteProductService.getFavouriteProducts(authentication.getName(),
                        0, 9999);
                if (favouriteProducts.contains(product)) {
                    model.addAttribute("isFavourite", true);
                } else {
                    model.addAttribute("isFavourite", false);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return "product";
    }

    @PostMapping("favourites/{id}")
    public String addProductToFavorites(@PathVariable(name = "id") String productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        favouriteProductService.addProductToFavourites(authentication.getName(), UUID.fromString(productId));
        return "redirect:/";
    }

    @DeleteMapping("favourites/{id}")
    public String delProductFromFavorites(@PathVariable(name = "id") String productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        favouriteProductService.deleteProductFromFavourites(authentication.getName(), UUID.fromString(productId));
        return "redirect:/";
    }

    @DeleteMapping("favourites")
    public String delAllProductsFromFavorites() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        favouriteProductService.deleteAllProductsFromFavourites(authentication.getName());
        return "redirect:/";
    }

    @GetMapping("favourites")
    public String getFavouritesProducts(Model model,
                                        @RequestParam(name = "pageNumber", defaultValue = "1") int page) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        final int pageSize = 6;
        try {
            int countProducts = favouriteProductService.getCountFavouriteProducts(login);
            PaginationHelper.addPaginationInfoToModel(countProducts, page, pageSize, model);
            int skip = (page - 1) * pageSize;
            List<Product> products = favouriteProductService.getFavouriteProducts(login, skip, pageSize);
            model.addAttribute("products", products);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "favourites";
    }
}
