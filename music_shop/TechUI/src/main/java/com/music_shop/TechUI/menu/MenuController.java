package com.music_shop.TechUI.menu;

import com.music_shop.TechUI.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Scanner;

@Component
public class MenuController {
    private final Scanner in = new Scanner(System.in);
    private final Session session;
    private final Map<String, Menu> menuMap;
    private final PagePrinter pagePrinter;

    @Autowired
    public MenuController(Session session, Map<String, Menu> menuMap, PagePrinter pagePrinter) {
        this.session = session;
        this.menuMap = menuMap;
        this.pagePrinter = pagePrinter;
    }
    public void run() throws InterruptedException {

        int rc = 0;
        Thread.sleep(2 * 1000);
        do {
            pagePrinter.printPage();
            Menu menu = menuMap.get(session.getMenuName());
            menu.establish();
            try {
                int actionNumber = Integer.parseInt(in.nextLine());
                rc = menu.performAction(actionNumber);
            } catch (NumberFormatException e) {
                System.out.println("Неизвестное действие!");
            }

        } while (rc != -1);
    }
}
