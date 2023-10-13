package engine;

//Enemy
class Enemy {
    private String name;
    private int health;
    private int coinsDropped;

    public Enemy(String name, int health, int coinsDropped) {
        this.name = name;
        this.health = health;
        this.coinsDropped = coinsDropped;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        if (isAlive()) {
            health -= damage;
        }
    }

    public int dropCoins() {
        return coinsDropped;
    }
}