/**
 * DQ-lite
 * Items class
 *
 * This class creates objects for all the items, weapons, and armor in the game. In
 * this game the player can use different items to either restore Mana Points (MP) or
 * Hit Point (HP). The player can also buy new and better weapons and armor to get stronger.
 * This class holds all the information for the spells and has getters for the methods in
 * this program to get that information.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

public class Items {
    private String name, effect, type;
    private int strength, level, cost;

    /**
     * Default class constructor
     */
    public Items() {}

    /**
     * Class constructor
     *
     * @param name name of the item
     * @param effect Health for healing, Mana for MP restoration, attack for a weapon, and defence for armor
     * @param type consumable for healing and MP restoration, Weapon for weapon, and Armor for armor
     * @param strength the strength for the weapon or the armor, the amount of HP or MP that will be restored
     * @param level the level in which the spell is available for purchase in the shop
     * @param cost the amount of gold needed to buy the spell in the shop
     */
    public Items(String name, String effect, String type, int strength, int level, int cost) {
        this.name = name;
        this.effect = effect;
        this.type = type;
        this.strength = strength;
        this.level = level;
        this.cost = cost;
    }

    /**
     * When you sell an item to the shop it is worth 60% of the cost variable.
     *
     * @return int the amount of gold the item is worth when sold
     */
    public int get_selling_price () {
        return ((cost * 60) / 100);
    }

    //getters
    public String get_name() {return name;}
    public String get_effect() {return effect;}
    public String get_type() {return type;}
    public int get_strength() {return strength;}
    public int get_level() {return level;}
    public int get_cost() {return cost;}

    //no setters
}
