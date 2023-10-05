package com.music_shop.TechUI.menu;

public interface Menu {
    default void establish() {
        System.out.println("0 - Выйти из приложения.");
    }
    int performAction(int actionNumber);
}
