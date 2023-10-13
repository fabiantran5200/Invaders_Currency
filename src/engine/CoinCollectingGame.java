package engine;

import java.util.Random;
public class CoinCollectingGame {

    public static void main(String[] args) {
        Player player = new Player("Player1", 0);
        Random random = new Random();

        Enemy enemy1 = new Enemy("Goblin", 50, 1, 10);
        Enemy enemy2 = new Enemy("Skeleton", 70, 5, 15);
        Enemy enemy3 = new Enemy("Orc", 100, 10, 20);

        while (enemy1.isAlive() || enemy2.isAlive() || enemy3.isAlive()) {
            if (enemy1.isAlive()) {
                int damage = random.nextInt(10) + 1;
                enemy1.takeDamage(damage);

                if (!enemy1.isAlive()) {
                    int coins = enemy1.dropCoins();
                    player.setCurrency(player.getCurrency() + coins);
                    System.out.println("You killed " + enemy1.getName() + ", obtained " + coins + " gold coins!");
                }
            }

            if (enemy2.isAlive()) {
                int damage = random.nextInt(15) + 1;
                enemy2.takeDamage(damage);

                if (!enemy2.isAlive()) {
                    int coins = enemy2.dropCoins();
                    player.setCurrency(player.getCurrency() + coins);
                    System.out.println("You killed " + enemy2.getName() + ", obtained " + coins + " gold coins!");
                }
            }

            if (enemy3.isAlive()) {
                int damage = random.nextInt(20) + 1;
                enemy3.takeDamage(damage);

                if (!enemy3.isAlive()) {
                    int coins = enemy3.dropCoins();
                    player.setCurrency(player.getCurrency() + coins);
                    System.out.println("You killed " + enemy3.getName() + ", obtained " + coins + " gold coins!");
                }
            }
        }

        System.out.println("Game Over. Total gold coins collected: " + player.getCurrency());
    }
}
