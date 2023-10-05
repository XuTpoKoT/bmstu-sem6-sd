package com.music_shop.TechUI.action.user;

import com.music_shop.BL.API.UserService;
import com.music_shop.BL.model.User;
import com.music_shop.TechUI.action.Action;
import com.music_shop.TechUI.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeInfoAction implements Action {
    private final Session session;
    private final UserService userService;

    @Autowired
    public EmployeeInfoAction(Session session, UserService userService) {
        this.session = session;
        this.userService = userService;
    }

    @Override
    public int perform() {
        try {
            User user = userService.getEmployeeByLogin(session.getLogin());
            System.out.println("Логин: " + user.getLogin());
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
