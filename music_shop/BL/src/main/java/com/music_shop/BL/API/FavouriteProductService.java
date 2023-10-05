package com.music_shop.BL.API;

import com.music_shop.BL.model.Product;

import java.util.List;
import java.util.UUID;

public interface FavouriteProductService {
    void addProductToFavourites(String name, UUID uuid);

    int getCountFavouriteProducts(String login);

    List<Product> getFavouriteProducts(String login, int skip, int limit);

    void deleteProductFromFavourites(String login, UUID uuid);

    void deleteAllProductsFromFavourites(String login);
}
