package com.music_shop.TechUI.action.user;

import com.music_shop.BL.API.UserService;
import com.music_shop.BL.model.User;
import com.music_shop.TechUI.action.Action;
import com.music_shop.TechUI.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerInfoAction implements Action {
    private final Session session;
    private final UserService userService;

    @Autowired
    public CustomerInfoAction(Session session, UserService userService) {
        this.session = session;
        this.userService = userService;
    }

    @Override
    public int perform() {
        try {
            User user = userService.getCustomerByLogin(session.getLogin());
            System.out.println("Логин: " + user.getLogin());
            System.out.println("Имя: " + user.getFirstName());
            System.out.println("Почта: " + user.getEmail());
            System.out.println("Бонусов на карте: " + user.getCard().getBonuses());
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
