package com.music_shop.TechUI.util;

import com.music_shop.BL.model.Product;

import java.util.Map;

public class ProductPrinter {
    public static void printShort(Product p) {
        System.out.println("ID: " + p.getId());
        System.out.println("Название: " + p.getName());
        System.out.println("Цена: " + p.getPrice());
        System.out.println();
    }

    public static void printFull(Product p) {
        System.out.println("ID: " + p.getId());
        System.out.println("Имя: " + p.getName());
        System.out.println("Производитель: " + p.getManufacturer());
        System.out.println("Цвет: " + p.getColor());
        System.out.println("Цена: " + p.getPrice());
        Map<String, String> characteristics = p.getCharacteristics();
        if (!characteristics.isEmpty()) {
            for (Map.Entry entry : characteristics.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
        System.out.println();
    }
}
