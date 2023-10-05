package com.music_shop.TechUI.action;

import com.music_shop.TechUI.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.UUID;

@Component
public class RemoveFromBucketAction implements Action {
    private final Session session;
    private final Scanner in = new Scanner(System.in);

    @Autowired
    public RemoveFromBucketAction(Session session) {
        this.session = session;
    }

    @Override
    public int perform() {
        try {
            System.out.println("Введите id товара:");
            UUID id = UUID.fromString(in.nextLine());
            session.removeProductFromBucket(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
