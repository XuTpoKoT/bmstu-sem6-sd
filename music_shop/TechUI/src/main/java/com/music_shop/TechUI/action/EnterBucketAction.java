package com.music_shop.TechUI.action;

import com.music_shop.BL.API.ProductService;
import com.music_shop.BL.model.Product;
import com.music_shop.TechUI.session.Session;
import com.music_shop.TechUI.session.State;
import com.music_shop.TechUI.util.ProductPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

@Component
public class EnterBucketAction implements Action {
    private final Session session;

    @Autowired
    public EnterBucketAction(Session session) {
        this.session = session;
    }

    @Override
    public int perform() {
        try {
            session.setState(State.BUCKET_MENU);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
