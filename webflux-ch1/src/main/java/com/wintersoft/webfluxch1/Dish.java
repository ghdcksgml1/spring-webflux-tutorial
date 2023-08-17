package com.wintersoft.webfluxch1;

public class Dish {

    private String name;
    private boolean delivered = false;

    public static Dish deliver(Dish dish) {
        Dish deliveredDish = new Dish(dish.name);
        deliveredDish.delivered = true;

        return deliveredDish;
    }

    public Dish(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isDelivered() {
        return delivered;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", delivered=" + delivered +
                '}';
    }
}
