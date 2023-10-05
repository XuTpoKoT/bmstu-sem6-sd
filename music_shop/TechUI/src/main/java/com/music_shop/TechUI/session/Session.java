package com.music_shop.TechUI.session;

import com.music_shop.BL.API.ProductService;
import com.music_shop.BL.model.Product;
import com.music_shop.BL.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Session {
    @Getter
    @Setter
    private User.Role role = User.Role.UNREGISTERED;
    @Getter
    @Setter
    private State state = State.MAIN_MENU;
    @Getter
    @Setter
    private String login;
    private final List<UUID> validProductIds = new ArrayList<>();
    @Getter
    private Map<UUID, Integer> bucket = new HashMap<>();
    public void updateProductIds(List<Product> products) {
        for (Product p : products) {
            validProductIds.add(p.getId());
        }
    }
    public void clearBucket() {
        bucket.clear();
    }
    public void addProductToBucket(UUID id) {
        if (validProductIds.contains(id)) {
            if (bucket.containsKey(id)) {
                bucket.replace(id, bucket.get(id) + 1);
            } else {
                bucket.put(id, 1);
            }

        } else {
            throw new RuntimeException("Некорректный id");
        }
    }

    public void removeProductFromBucket(UUID id) {
        if (bucket.containsKey(id)) {
            if (bucket.get(id) == 1) {
                bucket.remove(id);
            } else {
                bucket.replace(id, bucket.get(id) - 1);
            }
        } else {
            throw new RuntimeException("Некорректный id");
        }
    }

    public String getMenuName() {
        if (state == State.PRODUCT_MENU) {
            return "productMenu";
        }
        return role.name().toLowerCase() + state.toString();
    }
}
