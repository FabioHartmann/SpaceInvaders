package src;

import java.util.function.Consumer;

public class Upgrade {
    private String name;
    private Consumer<Upgrade> onUse;
    private int usedTimes;
    private int maxUses;
    private int price;
    private int originalPrice;

    public Upgrade(String name, int uses, int price, Consumer onUse) {
        this.name = name;
        this.onUse = onUse;
        this.maxUses = uses;
        this.usedTimes = 0;
        this.price = price;
        this.originalPrice = price;

        if (name == null) name = "";
    }

    public void use() {
        if (isUsable()) {
            usedTimes++;
            Game.getInstance().removeScore(price);
            if (onUse != null) {
                this.onUse.accept(this);
            }
        }
    }

    public String getName() {
        return name;
    }

    public boolean isUsable() {
        return usedTimes < maxUses && Game.getInstance().getScore() - price >= 0;
    }

    public int getPrice() {
        return price;
    }

    public void reset() {
        usedTimes = 0;
        price = originalPrice;
    }

    public int getUsedTimes() {
        return usedTimes;
    }

    public int getMaxUses() {
        return maxUses;
    }
}
