/**
 * DQ-lite
 * Enemy class
 *
 * This class creates the objects for themonsters and enemies of the game.It extends
 * the Entity superclass. The Entity class hold all variables and methods that Hero
 * and Enemy have in common.
 * This class has methods that manipulate the objects variables, but not until a few
 * calculation using those variable. Rarely are the variable just set to something else.
 * Same with the methods that return values, they rarely just return a variable for the
 * object. They Usually use multiple variables to calculate and value to return.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

import java.util.Random;

public class Enemy extends Entity {
    private int agility, victory_experience, victory_gold;

    /**
     * Default class constructor
     */
    public Enemy() {}

    /**
     * Class constructor
     *
     * @param name
     * @param magic_type
     * @param level
     * @param HP
     * @param MP
     * @param strength
     * @param critical
     * @param resilience
     * @param agility
     * @param victory_experience
     * @param victory_gold
     * @param spell_list
     */
    public Enemy(String name, String magic_type, int level, int HP, int MP,
                 int strength, int critical, int resilience, int agility, int victory_experience,
                 int victory_gold, String spell_list) {
        this.name = name;
        this.magic_type = magic_type;
        this.level = level;
        this.HP = HP;
        this.MP = MP;
        this.maxHP = HP;
        this.maxMP = MP;
        this.strength = strength;
        this.critical = critical;
        this.resilience = resilience;
        this.agility = agility;
        this.victory_experience = victory_experience;
        this.victory_gold = victory_gold;

        add_to_spells(spell_list);
    }

    /**
     * Given a String of spells names it will separate them and add them to an ArrayList
     * of spells. In the String spell_list the individual spells are separated with a
     * comma (,). This method will take the words in between the commas, that is the
     * spells name, and add it to the ArrayList spells. That ArrayList will be used
     * later to find out what spells the monster can use in combat.
     *
     * @param spell_list a String off all the spells that the monster will be able to use
     */
    private void add_to_spells(String spell_list) {
        String spell = "";
        do {
            spell = spell_list.substring(0, spell_list.indexOf(","));
            if (!(spell.equals("None"))) {
                spells.add(spell);
            }
            spell_list = spell_list.substring((spell_list.indexOf(",") + 1));
        } while (spell_list.contains(","));
    }


    /**
     * Will calculate the enemy's attack power by adding the enemy's strength,
     * and add a random number between the critical variable and the negative
     * value of the critical variable.
     *
     * @return attack_power the power of the attack that was just calculated
     */
    public int enemy_attack() {
        Random random = new Random();
        int attack_power = (strength / 2);
        if (critical > 0) {
            attack_power += random.nextInt((-1 * critical), critical);
        }
        return attack_power;
    }


    /**
     * Given an attack spell's name and how much Mana Points (MP) the spell uses
     * return true is the spell can be cast or false if it cannot. If the spell
     * is cast subtract the spell's MP for the enemy's MP.
     *
     * @param spell_name the name of the spell being used
     * @param MP_cost the Mana Points (MP) the spell requires for use
     * @return boolean if the spell was cast
     */
    public boolean enemy_attack_spell(String spell_name, int MP_cost) {
        if (spells.contains(spell_name) && (MP >= MP_cost)) {
            this.MP -= MP_cost;
            return true;
        } else {
            return false;
        }
    }


    /**
     * Given a recovery spell's name and how much Mana Points (MP) the spell uses
     * return true is the spell can be cast or false if it cannot. If the spell
     * is cast subtract the spell's MP for the enemy's MP and add the strength of
     * the spell or the healing power to the enemy's HP.
     *
     * @param spell_name the name of the spell being used
     * @param healing_power the strength of the spell
     * @param MP_cost the Mana Points (MP) the spell requires for use
     * @return boolean if the spell was cast
     */
    public boolean enemy_recovery_spell(String spell_name, int healing_power, int MP_cost) {
        if (spells.contains(spell_name) && (MP >= MP_cost)) {
            this.MP -= MP_cost;
            this.HP += healing_power;
            if (HP > maxMP) {
                HP = maxHP;
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * When the hero attacks the monster the hero's attack power is used here to find
     * out how much damage is done to the monster. If the magic type of the attack is
     * the same as the monster's magic type the attack does less damage. The enemy's
     * resilience is subtracted for the attack power to find the damage. The damage is
     * then subtracted from the enemy's Hit Point (HP).
     *
     * @param hero_attack_power the power of the enemy's attack
     * @param attack_magic_type the magic type of the attack, none if not a spell attack
     * @return damage the damage done to the hero, so it can be output to the user
     */
    public int enemy_defence(int hero_attack_power, String attack_magic_type) {
        if (attack_magic_type.equals(magic_type.toString())) {
            hero_attack_power = hero_attack_power / 2;
        }

        int damage = hero_attack_power;

        if (resilience > 0) {
            damage -= ((resilience) / 4);
        }

        if (damage <= 0) {
            damage = 1;
        }

        HP -= damage;

        return damage;
    }


    /**
     * Count the number of spells the monster has. When the game is deciding what
     * the monster will do in battle we will need to know how many spells the monster
     * has to cast, if any.
     *
     * @return int number of spells
     */
    public int get_number_of_spells() {
        return spells.size();
    }

    /**
     * Based on the int parameter find the spell in the spell list that the monster
     * will use in battle. When we got the number of spells we selected a one of them
     * by selecting a number then using that number here to get the spell name. If we
     * know there are 3 attack spells and we select the second one we then find out what
     * spell is at index 1.
     *
     * @param index the index of the spell we are looking for in the list of spells
     * @return String the name of the spell
     */
    public String get_spell_at(int index) {
        return spells.get(index);
    }


    /**
     * Checks to see if the monster has any recovery spells in their list of spells.
     * When the game is deciding what the monster will do in battle we will need to
     * know if the monster has any recovery spells and which ones are they.
     *
     * @return String the name of the spell
     */
    public String check_if_have_recovery_spell() {
        if (spells.contains("Heal")) {
            return "Heal";
        } else if (spells.contains("Cure Water")) {
            return "Cure Water";
        } else if (spells.contains("Divine Blessing")) {
            return "Divine Blessing";
        } else if (spells.contains("Fountain of Youth")) {
            return "Fountain of Youth";
        } else if (spells.contains("Full Rest")) {
            return "Full Rest";
        } else {
            return "none";
        }
    }

    //getters
    public int get_agility() {return agility;}
    public int get_victory_experience() {return victory_experience;}
    public int get_victory_gold() {return victory_gold;}

    //no setters
}
