package com.music_shop.TechUI.action.order;

import com.music_shop.BL.API.OrderService;
import com.music_shop.BL.API.ProductService;
import com.music_shop.BL.model.Order;
import com.music_shop.BL.model.Product;
import com.music_shop.TechUI.action.Action;
import com.music_shop.TechUI.session.Session;
import com.music_shop.TechUI.session.State;
import com.music_shop.TechUI.util.ProductPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class OrderHistoryAction implements Action {
    private final OrderService orderService;
    private final Session session;
    private final Scanner in = new Scanner(System.in);

    @Autowired
    public OrderHistoryAction(OrderService orderService, Session session) {
        this.orderService = orderService;
        this.session = session;
    }

    @Override
    public int perform() {
        try {
            List<Order> orders = new ArrayList<>();
            switch (session.getRole()) {
                case CUSTOMER -> orders = orderService.getOrdersByCustomerLogin(session.getLogin(), 0, 10);
                case EMPLOYEE -> orders = orderService.getOrdersByEmployeeLogin(session.getLogin(), 0, 10);
            }
            System.out.println("История заказов:");
            for (Order o : orders) {
                System.out.println("ID " + o.getId());
                System.out.println("Дата " + o.getDate());
                System.out.println("Стоимость " + o.getInitialCost());
                System.out.println("Оплачено бонусами " + o.getPaidByBonuses());
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
