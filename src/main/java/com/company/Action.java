/**
 * DQ-lite
 * Action class
 *
 * This class will not create objects. This class is a collection of methods that are
 * related in use and purpose. The methods were put in a separate class to help make
 * the code easier to read and easier to find what you are looking for. These methods
 * are called by the command() method in the GUI class. This class also extends the GUI
 * class because the methods were originally in that class and use the variables and
 * objects that are in the class.
 *
 * All the methods in this class are actions that the playable character or the enemies
 * use in battle.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

import java.util.Random;

public class Action extends GUI {

    /**
     * Allows the hero or enemy to cast an attack spell in battle and tells the
     * user what happened.
     *
     * @param spell the spell that is being cast
     * @param who_cast_spell either the 'hero' or the 'enemy'
     * @return boolean whether the spell has been successfully cast or not
     */
    public static boolean cast_spell(Spells spell, String who_cast_spell) {
        boolean spell_cast = false;
        String spell_effect = spell.get_spell_effet();

        if (spell_effect.equals("Recovery")) {
            if (who_cast_spell.equals("hero")) {
                spell_cast = recovery_spell_hero(spell);
            } else if(who_cast_spell.equals("enemy")) {
                spell_cast = recovery_spell_enemy(spell);
            }
        } else if (spell_effect.equals("Attack")) {
            if (who_cast_spell.equals("hero")) {
                spell_cast = attack_spell_hero(spell);
            } else if(who_cast_spell.equals("enemy")) {
                spell_cast = attack_spell_enemy(spell);
            }
        }

        if (spell_cast == false) {
            if (who_cast_spell.equals("hero")) {
                output_all += (hero.get_name() + " failed to cast spell.\n");
                spell_cast = true;
            } else if(who_cast_spell.equals("enemy")) {
                output_all += (enemy.get_name() + " failed to cast spell.\n");
                spell_cast = true;
            }
        }

        return spell_cast;
    }

    /**
     * The hero or playable character casts a healing spell to recover Hit Points (HP)
     * in battle.
     *
     * @param spell the spell that is being cast
     * @return boolean whether the spell has been successfully cast or not
     */
    public static boolean recovery_spell_hero(Spells spell) {
        boolean spell_cast = false;

        int spell_power = spell.get_spell_strength();
        int spell_critical = spell.get_spell_critical();
        Random random = new Random();

        spell_power += (random.nextInt((-1 * (spell_critical)), (spell_critical + 1)));

        spell_cast = hero.hero_recovery_spell(spell.get_spell_name(), spell_power, spell.get_MP_cost());

        if(spell_cast) {
            output_all += (hero.get_name() + " has successfully cast " + spell.get_spell_name() + " and recovered " + spell_power + " HP, HP now at " + hero.get_HP() + ".\n");
        }

        return spell_cast;
    }

    /**
     * The enemy casts a healing spell to recover Hit Points (HP) in battle.
     *
     * @param spell the spell that is being cast
     * @return boolean whether the spell has been successfully cast or not
     */
    public static boolean recovery_spell_enemy(Spells spell) {
        boolean spell_cast = false;

        int spell_power = spell.get_spell_strength();
        int spell_critical = spell.get_spell_critical();
        Random random = new Random();

        spell_power += (random.nextInt((-1 * (spell_critical)), (spell_critical + 1)));

        spell_cast = enemy.enemy_recovery_spell(spell.get_spell_name(), spell_power, spell.get_MP_cost());

        if(spell_cast) {
            output_all += (enemy.get_name() + " has successfully cast " + spell.get_spell_name() + " and recovered " + spell_power + " HP, HP now at " + enemy.get_HP() + ".\n");
        }

        return spell_cast;
    }

    /**
     * The hero or playable character casts an attack spell to deal damage to the 
     * enemy they are currently fighting in battle.
     *
     * @param spell the spell that is being cast
     * @return boolean whether the spell has been successfully cast or not
     */
    public static boolean attack_spell_hero(Spells spell) {
        boolean spell_cast = false;

        int spell_power = spell.get_spell_strength();
        int spell_critical = spell.get_spell_critical();
        Random random = new Random();

        spell_power += (random.nextInt((-1 * (spell_critical)), (spell_critical + 1)));

        spell_cast = hero.hero_attack_spell(spell.get_spell_name(), spell.get_MP_cost());

        if (spell_cast) {
            int damage = enemy.enemy_defence(spell_power, spell.get_spell_type());
            output_all += (hero.get_name() + " has successfully cast " + spell.get_spell_name() + " and dealt " +  damage + " damage to " + enemy.get_name() + ".\n");
        }

        return spell_cast;
    }

    /**
     * The enemy casts an attack spell to deal damage to the hero or playable character
     * in battle.
     *
     * @param spell the spell that is being cast
     * @return boolean whether the spell has been successfully cast or not
     */
    public static boolean attack_spell_enemy(Spells spell) {
        boolean spell_cast = false;

        int spell_power = spell.get_spell_strength();
        int spell_critical = spell.get_spell_critical();
        Random random = new Random();

        spell_power += (random.nextInt((-1 * (spell_critical)), (spell_critical + 1)));

        spell_cast = enemy.enemy_attack_spell(spell.get_spell_name(), spell.get_MP_cost());

        if (spell_cast) {
            int damage = hero.hero_defence(spell_power, spell.get_spell_type());
            output_all += (enemy.get_name() + " dealt " + damage + " damage to " + hero.get_name() + ".\n");
        }

        return spell_cast;
    }

    /**
     * The hero or the enemy uses their weapon to do a physical attack and inflict 
     * damage on the other.
     * 
     * @param who either the 'hero' or the 'enemy'
     */
    public static void physical_attack(String who) {
        if (who.equals("hero")) {
            int attack_power = hero.hero_attack();
            int damage = enemy.enemy_defence(attack_power, "none");
            output_all += (hero.get_name() + " has dealt " + damage + " damage to " + enemy.get_name() + ".\n");
        } else if (who.equals("enemy")) {
            int attack_power = enemy.enemy_attack();
            int damage = hero.hero_defence(attack_power, "none");
            output_all += (enemy.get_name() + " has dealt " + damage + " damage to " + hero.get_name() + ".\n");
        }
    }

    /**
     * the hero or playable character uses a consumable item from their inventory
     * while in battle. The enemy or monsters cannot use items.
     * 
     * @param item the item that is being used.
     * @return boolean whether the item has been used or not
     */
    public static boolean use_item(Items item) {
        boolean used = false;

        if (item.get_type().equals("consumable")) {
            if (item.get_effect().equals("Health")) {
                int number_of_item = hero.get_number_of_item(item.get_name());
                if (number_of_item > 0) {
                    hero.used_a_health_item(item, item.get_strength());
                    output_all += (hero.get_name() + " used a " + item.get_name() + ", it restored " + item.get_strength() + " HP, HP now at " + hero.get_HP() + ".\n");
                    used = true;
                } else {
                    output_all += (hero.get_name() + " does not currently have any " + item.get_name() +  "'s.\n");
                }
            } else if (item.get_effect().equals("Mana")) {
                int number_of_item = hero.get_number_of_item(item.get_name());
                if (number_of_item > 0) {
                    hero.used_a_mana_item(item, item.get_strength());
                    output_all += (hero.get_name() + " used a " + item.get_name() + ", it restored " + item.get_strength() + " MP, MP now at " + hero.get_MP() + ".\n");
                    used = true;
                } else {
                    output_all += (hero.get_name() + " does not currently have any " + item.get_name() +  "'s.\n");
                }
            }
        }

        return used;
    }
}
