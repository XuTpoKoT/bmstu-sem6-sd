package com.music_shop.BL.service;

import com.music_shop.BL.log.Logger;
import com.music_shop.BL.log.LoggerImpl;
import com.music_shop.BL.model.SecurityUser;
import com.music_shop.BL.model.User;
import com.music_shop.DB.API.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger log = new LoggerImpl(getClass().getName());

    private final UserRepo userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        log.info("trying to load user " + login);
        User user = userRepository.getUserByLogin(login);
        return SecurityUser.fromUser(user);
    }
}
