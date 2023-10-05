package com.music_shop.TechUI.action.auth;

import com.music_shop.BL.model.User;
import com.music_shop.TechUI.action.Action;
import com.music_shop.TechUI.session.Session;
import com.music_shop.TechUI.session.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExitProfileAction implements Action {
    private final Session session;

    @Autowired
    public ExitProfileAction(Session session) {
        this.session = session;
    }
    @Override
    public int perform() {
        session.setRole(User.Role.UNREGISTERED);
        return 0;
    }
}
