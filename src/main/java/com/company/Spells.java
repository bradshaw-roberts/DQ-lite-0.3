/**
 * DQ-lite
 * Spells class
 *
 * This class creates objects for all the spells in the game. In this game the player
 * can cast different spells to either heal themselves or inflict damage on the monster
 * they are fighting. This class holds all the information for the spells and has getters
 * for the methods in this program to get that information.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

public class Spells {
    private String spell_name, spell_type, spell_effect;
    private int spell_strength,spell_critical, MP_cost, level_available, gold_cost;

    /**
     * Default class constructor
     */
    public Spells() {}

    /**
     * Class constructor
     *
     * @param spell_name name of the spell
     * @param spell_type the magic type, Fire, Water, Wind, or All
     * @param spell_strength the strength or power of the spell
     * @param spell_critical a range that has chance to get higher or lower that the strength when using the spell
     * @param MP_cost how much Mana Points (MP) the spell requires
     * @param spell_effect recovery (healing) or attack
     * @param level_available the level in which the spell is available for purchase in the shop
     * @param gold_cost the amount of gold needed to buy the spell in the shop
     */
    public Spells(String spell_name, String spell_type, int spell_strength, int spell_critical,
                  int MP_cost, String spell_effect, int level_available, int gold_cost) {
        this.spell_name = spell_name;
        this.spell_type = spell_type;
        this.spell_strength = spell_strength;
        this.spell_critical = spell_critical;
        this.MP_cost = MP_cost;
        this.spell_effect = spell_effect;
        this.level_available = level_available;
        this.gold_cost = gold_cost;
    }

    //getters for all variables in this class
    public String get_spell_name() {return spell_name;}
    public String get_spell_type() {return spell_type;}
    public int get_spell_strength() {return spell_strength;}
    public int get_spell_critical() {return spell_critical;}
    public int get_MP_cost() {return MP_cost;}
    public int get_level_available() {return level_available;}
    public int get_gold_cost() {return gold_cost;}
    public String get_spell_effet() {return spell_effect;}
}

