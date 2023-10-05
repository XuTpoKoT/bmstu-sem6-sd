package com.music_shop.TechUI.menu;

import com.music_shop.TechUI.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmployeeBucketMenu implements Menu{
    private final Map<String, Action> actionMap;

    @Autowired
    public EmployeeBucketMenu(Map<String, Action> actionMap) {
        this.actionMap = actionMap;
    }

    @Override
    public void establish() {
        Menu.super.establish();
        System.out.println("1 - Назад");
        System.out.println("2 - Оформить заказ");
        System.out.println("3 - Удалить товар");
    }

    @Override
    public int performAction(int actionNumber) {
        switch (actionNumber) {
            case 0 -> {
                System.out.println("Выход из приложения");
                return -1;
            }
            case 1 -> actionMap.get("toMainMenuAction").perform();
            case 2 -> actionMap.get("makeEmployeeOrderAction").perform();
            case 3 -> actionMap.get("removeFromBucketAction").perform();
            default -> System.out.println("Неизвестное действие!");
        }
        return 0;
    }
}
