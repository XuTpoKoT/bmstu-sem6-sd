package com.music_shop.TechUI;

import com.music_shop.TechUI.menu.MenuController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.music_shop", exclude={DataSourceAutoConfiguration.class})
public class Main {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ApplicationContext applicationContext = app.run();
        MenuController menuController = applicationContext.getBean(MenuController.class);
        try {
            menuController.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
