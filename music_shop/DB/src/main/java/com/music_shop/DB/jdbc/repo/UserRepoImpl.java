package com.music_shop.DB.jdbc.repo;

import com.music_shop.BL.exception.DBException;
import com.music_shop.BL.model.User;
import com.music_shop.DB.API.UserRepo;
import com.music_shop.DB.jdbc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl implements UserRepo {
    private static final String SQL_ADD_USER = """
        INSERT INTO public.user (login, password, role_) 
        VALUES (:login, :password, :role) 
    """;
    private static final String SQL_GET_USER_BY_LOGIN = """
        SELECT login, convert_from(password, 'UTF-8') as password, role_, email
        FROM public.user
        WHERE login = :login 
    """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Autowired
    public UserRepoImpl(NamedParameterJdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public boolean addUser(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", user.getLogin());
        params.addValue("password", user.getPassword());
        params.addValue("role", user.getRole().toString());

        try {
            jdbcTemplate.update(SQL_ADD_USER, params);
        } catch (DataAccessException e) {
            throw new DBException(e.getMessage());
        }

        return true;
    }

    @Override
    public User getUserByLogin(String login) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);

        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_GET_USER_BY_LOGIN, params, userMapper);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DBException(e.getMessage());
        }

        return user;
    }
}
