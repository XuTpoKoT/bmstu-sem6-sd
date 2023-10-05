package com.music_shop.TechUI.menu;

import com.music_shop.TechUI.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductMenu implements Menu {
    private final Map<String, Action> actionMap;

    @Autowired
    public ProductMenu(Map<String, Action> actionMap) {
        this.actionMap = actionMap;
    }

    @Override
    public void establish() {
        Menu.super.establish();
        System.out.println("1 - Назад");
    }

    @Override
    public int performAction(int actionNumber) {
        switch (actionNumber) {
            case 0 -> {
                System.out.println("Выход из приложения");
                return -1;
            }
            case 1 -> actionMap.get("toMainMenuAction").perform();
            default -> System.out.println("Неизвестное действие!");
        }
        return 0;
    }
}
