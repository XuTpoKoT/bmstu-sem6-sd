package com.music_shop.TechUI.action.auth;

import com.music_shop.BL.API.AuthService;
import com.music_shop.BL.model.User;
import com.music_shop.TechUI.action.Action;
import com.music_shop.TechUI.session.Session;
import com.music_shop.TechUI.session.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LoginAction implements Action {
    private final Session session;
    private final AuthService authService;
    private final Scanner in = new Scanner(System.in);

    @Autowired
    public LoginAction(Session session, AuthService authService) {
        this.session = session;
        this.authService = authService;
    }

    @Override
    public int perform() {
        System.out.println("Введите логин:");
        String login = in.nextLine();
        System.out.println("Введите пароль:");
        String password = in.nextLine();
        try {
            User.Role role = authService.login(login, password);
            session.setRole(role);
            session.setLogin(login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
