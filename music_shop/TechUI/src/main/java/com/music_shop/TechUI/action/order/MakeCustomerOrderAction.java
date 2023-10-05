package com.music_shop.TechUI.action.order;

import com.music_shop.BL.API.DeliveryPointService;
import com.music_shop.BL.API.OrderService;
import com.music_shop.BL.model.DeliveryPoint;
import com.music_shop.BL.dto.MakeOrderDTO;
import com.music_shop.BL.model.Order;
import com.music_shop.TechUI.action.Action;
import com.music_shop.TechUI.session.Session;
import com.music_shop.TechUI.session.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class MakeCustomerOrderAction implements Action {
    private final OrderService orderService;
    private final DeliveryPointService deliveryPointService;
    private final Session session;
    private final Scanner in = new Scanner(System.in);

    @Autowired
    public MakeCustomerOrderAction(OrderService orderService, DeliveryPointService deliveryPointService, Session session) {
        this.orderService = orderService;
        this.deliveryPointService = deliveryPointService;
        this.session = session;
    }

    @Override
    public int perform() {
        try {
            Map<UUID, Integer> bucket = session.getBucket();
            if (bucket.isEmpty()) {
                System.out.println("Корзина пуста!");
                return 0;
            }
            System.out.println("Точки доставки:");
            List<DeliveryPoint> deliveryPoints = deliveryPointService.getAllDeliveryPoints();
            for (DeliveryPoint dp : deliveryPoints) {
                System.out.println(dp.getId() + " " + dp.getAddress());
            }
            System.out.println("\nВведите id точки доставки:");
            UUID id = UUID.fromString(in.nextLine());
            boolean isIdValid = false;
            for (DeliveryPoint dp : deliveryPoints) {
                if (id.compareTo(dp.getId()) == 0) {
                    isIdValid = true;
                    break;
                }
            }
            if (!isIdValid) {
                System.out.println("Некорректный id");
                return 0;
            }
            System.out.println("Хотите списать бонусы? (yes/no, default no)");
            boolean needSpendBonuses = Objects.equals(in.nextLine(), "yes");

            MakeOrderDTO order = MakeOrderDTO.builder().customerLogin(session.getLogin()).date(ZonedDateTime.now()
                            .withZoneSameLocal(ZoneId.of("UTC")))
                    .deliveryPointID(id).needSpendBonuses(needSpendBonuses).productCountMap(bucket)
                    .status(Order.Status.formed).build();
            orderService.makeOrder(order);
            session.clearBucket();
            session.setState(State.MAIN_MENU);
            System.out.println("Заказ успешно оформлен!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
