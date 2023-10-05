package com.music_shop.TechUI.session;

import lombok.Getter;

public enum State {
    UNKNOWN("Unknown"),
    MAIN_MENU("MainMenu"),
    PRODUCT_MENU("ProductMenu"),
    BUCKET_MENU("BucketMenu");

    @Getter
    private final String state;

    State(String state) {
        this.state = state;
    }
    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1, this.name().indexOf('_')).toLowerCase() +
                "Menu";
    }
}
