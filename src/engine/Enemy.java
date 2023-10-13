package engine;

import java.util.Random;

class Enemy {
    private String name;
    private int health;
    private int coinDropMin;
    private int coinDropMax;

    public Enemy(String name, int health, int coinDropMin, int coinDropMax) {
        this.name = name;
        this.health = health;
        this.coinDropMin = coinDropMin;
        this.coinDropMax = coinDropMax;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int dropCoins() {
        Random random = new Random();
        return random.nextInt(coinDropMax - coinDropMin + 1) + coinDropMin;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public String getName() {
        return name;
    }
}