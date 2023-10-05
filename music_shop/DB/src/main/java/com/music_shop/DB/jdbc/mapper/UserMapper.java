package com.music_shop.DB.jdbc.mapper;

import com.music_shop.BL.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    @Nullable
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        String login = rs.getString("login");
        byte[] password = rs.getBytes("password");
        String roleStr = rs.getString("role_");
        User.Role role = null;

        for (User.Role r : User.Role.values()) {
            if (roleStr.equals(r.name())) {
                role = r;
                break;
            }
        }

        if (role == null) { return null; }

        return User.builder().login(login).password(password).role(role).build();
    }

}
