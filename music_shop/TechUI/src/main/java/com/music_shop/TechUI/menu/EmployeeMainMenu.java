package com.music_shop.TechUI.menu;

import com.music_shop.TechUI.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmployeeMainMenu implements Menu {
    private final Map<String, Action> actionMap;

    @Autowired
    public EmployeeMainMenu(Map<String, Action> actionMap) {
        this.actionMap = actionMap;
    }

    @Override
    public void establish() {
        Menu.super.establish();
        System.out.println("1 - Выйти из профиля");
        System.out.println("2 - Посмотреть информацию о товаре");
        System.out.println("3 - Добавить в корзину");
        System.out.println("4 - Посмотреть профиль");
        System.out.println("5 - Корзина");
        System.out.println("6 - История заказов");
    }

    @Override
    public int performAction(int actionNumber) {
        switch (actionNumber) {
            case 0 -> {
                System.out.println("Выход из приложения");
                return -1;
            }
            case 1 -> actionMap.get("exitProfileAction").perform();
            case 2 -> actionMap.get("productInfoAction").perform();
            case 3 -> actionMap.get("addToBucketAction").perform();
            case 4 -> actionMap.get("employeeInfoAction").perform();
            case 5 -> actionMap.get("enterBucketAction").perform();
            case 6 -> actionMap.get("orderHistoryAction").perform();
            default -> System.out.println("Неизвестное действие!");
        }
        return 0;
    }
}
