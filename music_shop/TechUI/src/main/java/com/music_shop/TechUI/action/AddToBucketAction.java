package com.music_shop.TechUI.action;

import com.music_shop.TechUI.action.Action;
import com.music_shop.TechUI.session.Session;
import com.music_shop.TechUI.session.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class AddToBucketAction implements Action {
    private final Session session;
    private final Scanner in = new Scanner(System.in);

    @Autowired
    public AddToBucketAction(Session session) {
        this.session = session;
    }

    @Override
    public int perform() {
        try {
            System.out.println("Введите id товара:");
            UUID id = UUID.fromString(in.nextLine());
            session.addProductToBucket(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
