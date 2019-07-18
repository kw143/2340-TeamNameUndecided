package edu.gatech.cs2340.team.imperialtrader.entity;

public class PlayerShip extends Ship {

    /**
     * Constructor for PlayerShip
     * @param name ship's name
     * @param cost ship's cost
     * @param speed ship's speed
     * @param health ship's health
     * @param armor ship's armor
     * @param currentFuel ship's currentFuel
     * @param maxFuel ship's maxFuel
     * @param weapons ship's weapon
     * @param cargoCapacity ship's cargoCapacity
     */
    public PlayerShip(String name, int cost, int speed, int health, int armor, int currentFuel,
                      int maxFuel, int weapons, int cargoCapacity) {
        super(name, cost, speed, health, armor, currentFuel, maxFuel, weapons, cargoCapacity);
    }

    @Override
    /**
     * Fly method
     */
    public void fly() {
    }

    @Override
    /**
     * Shoot method
     */
    public void shoot() {
    }

    @Override
    /**
     * Repair method
     */
    public void repair() { this.setHealth(100); }

    @Override
    /**
     * Upgrade method
     */
    public void upgrade() {
        this.setArmor(getArmor() + 5);
        this.setWeapons(getWeapons() + 1);
        this.setCargoCapacity(getCargoCapacity() + 2);
    }
}
