package com.music_shop.DB.jdbc.repo;

import com.music_shop.BL.exception.DBException;
import com.music_shop.BL.model.Product;
import com.music_shop.DB.API.FavouriteProductRepo;
import com.music_shop.DB.jdbc.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FavouriteProductRepoImpl implements FavouriteProductRepo {
    private static final String SQL_GET_FAVOURITE_PRODUCTS  = """
        SELECT p.id, p.name_ as pname, price, color, description, characteristics, m.name_ as mname
        FROM public.product p
            join public.manufacturer m on m.id = p.manufacturer_id
            join public.favourites f on f.product_id = p.id
        WHERE f.login = :login
        LIMIT :limit
        OFFSET :offset
    """;
    private static final String SQL_GET_COUNT_OF_FAVOURITE_PRODUCTS = """
        SELECT count (*)
        FROM public.favourites
        WHERE login = :login
    """;
    private static final String SQL_ADD_PRODUCT_TO_FAVOURITES = """
        INSERT INTO public.favourites (login, product_id)
        VALUES (:login, :id)
        ON CONFLICT (login, product_id) DO NOTHING;
    """;
    private static final String SQL_DELETE_PRODUCT_FROM_FAVOURITES = """
        DELETE FROM public.favourites
        WHERE login = :login and product_id = :id;
    """;
    private static final String SQL_DELETE_ALL_PRODUCTS_FROM_FAVOURITES = """
        DELETE FROM public.favourites
        WHERE login = :login;
    """;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProductMapper productMapper;

    @Autowired
    public FavouriteProductRepoImpl(NamedParameterJdbcTemplate jdbcTemplate, ProductMapper productMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.productMapper = productMapper;
    }

    @Override
    public int getCountFavouriteProducts(String login) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);
        Integer count;
        try {
            count = jdbcTemplate.queryForObject(
                    SQL_GET_COUNT_OF_FAVOURITE_PRODUCTS, params, Integer.class);
        } catch (DataAccessException e) {
            throw new DBException("getCountFavouriteProducts failed", e);
        }

        return count;
    }

    @Override
    public List<Product> getFavouriteProducts(String login, int skip, int limit) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);
        params.addValue("offset", skip);
        params.addValue("limit", limit);
        List<Product> products = new ArrayList<>();
        try {
            products = jdbcTemplate.query(SQL_GET_FAVOURITE_PRODUCTS, params, productMapper).stream().toList();
        } catch (IncorrectResultSizeDataAccessException e) {
            return products;
        } catch (DataAccessException e) {
            throw new DBException("getFavouriteProducts failed", e);
        }

        return products;
    }

    @Override
    public void deleteProductFromFavourites(String login, UUID id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);
        params.addValue("id", id);

        try {
            jdbcTemplate.update(SQL_DELETE_PRODUCT_FROM_FAVOURITES, params);
        } catch (DataAccessException e) {
            throw new DBException("deleteProductFromFavourites failed", e);
        }
    }

    @Override
    public void deleteAllProductsFromFavourites(String login) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);

        try {
            jdbcTemplate.update(SQL_DELETE_ALL_PRODUCTS_FROM_FAVOURITES, params);
        } catch (DataAccessException e) {
            throw new DBException("deleteAllProductsFromFavourites failed", e);
        }
    }

    @Override
    public void addProductToFavourites(String login, UUID id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);
        params.addValue("id", id);

        try {
            jdbcTemplate.update(SQL_ADD_PRODUCT_TO_FAVOURITES, params);
        } catch (DataAccessException e) {
            throw new DBException("addProductToFavourites failed", e);
        }
    }
}
