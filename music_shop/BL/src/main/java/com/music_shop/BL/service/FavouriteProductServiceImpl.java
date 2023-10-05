package com.music_shop.BL.service;

import com.music_shop.BL.API.FavouriteProductService;
import com.music_shop.BL.exception.DBException;
import com.music_shop.BL.log.Logger;
import com.music_shop.BL.log.LoggerImpl;
import com.music_shop.BL.model.Product;
import com.music_shop.DB.API.FavouriteProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FavouriteProductServiceImpl implements FavouriteProductService {
    private final Logger log = new LoggerImpl(getClass().getName());
    private final FavouriteProductRepo favouriteProductRepo;

    @Autowired
    public FavouriteProductServiceImpl(FavouriteProductRepo favouriteProductRepo) {
        this.favouriteProductRepo = favouriteProductRepo;
    }

    @Override
    public void addProductToFavourites(String login, UUID id) {
        log.info("addProductToFavourites called for login " + login);
        try {
            favouriteProductRepo.addProductToFavourites(login, id);
        } catch (DBException e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    @Override
    public int getCountFavouriteProducts(String login) {
        log.info("getCountFavouriteProducts called for " + login);
        return favouriteProductRepo.getCountFavouriteProducts(login);
    }

    @Override
    public List<Product> getFavouriteProducts(String login, int skip, int limit) {
        log.info("getFavouriteProducts called for " + login);
        return favouriteProductRepo.getFavouriteProducts(login, skip, limit);
    }

    @Override
    public void deleteProductFromFavourites(String login, UUID id) {
        log.info("deleteProductFromFavourites called for login " + login);
        try {
            favouriteProductRepo.deleteProductFromFavourites(login, id);
        } catch (DBException e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteAllProductsFromFavourites(String login) {
        log.info("deleteAllProductsFromFavourites called for login " + login);
        try {
            favouriteProductRepo.deleteAllProductsFromFavourites(login);
        } catch (DBException e) {
            log.info(e.getMessage());
            throw e;
        }
    }
}
