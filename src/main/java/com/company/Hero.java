/**
 * DQ-lite
 * Hero class
 *
 * This class creates the object that is the playable character. It extends the Entity
 * superclass. The Entity class hold all variables and methods that Hero and Enemy have
 * in common.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Hero extends Entity {
    private String location_at;
    private int current_experience, gold;
    private ArrayList<String> item_inventory = new ArrayList<String>(Arrays.asList("Potion", "Super Potion", "Either", "Super Either", "Full Either"));
    private ArrayList<Integer> item_amount = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0));
    private Items weapon_equipped = new Items("None", "attack", "Weapon", 0, 1, 0);
    private Items armor_equipped = new Items("None", "defence", "Armor", 0, 1, 0);

    /**
     * Default class constructor
     */
    public Hero() {}

    /**
     * Class constructor
     *
     * @param name
     * @param magic_type
     */
    public Hero(String name, String magic_type) {
        this.name = name;
        this.magic_type = magic_type;
        this.level = 1;
        this.HP = 15;
        this.MP = 1;
        this.maxHP = 15;
        this.maxMP = 1;
        this.strength = 4;
        this.critical = 1;
        this.resilience = 0;
        this.location_at = "inn";
        this.current_experience = 0;
        this.gold = 30;

        if (magic_type.equals("None")) {
            this.maxHP += 2;
            this.HP += 2;
            this.maxMP += 2;
            this.MP += 2;
            this.resilience += 1;
        }

        spells.add("Discharge");
    }

    /**
     * Class constructor
     *
     * @param name
     * @param magic_type
     * @param level
     * @param HP
     * @param MP
     * @param maxHP
     * @param maxMP
     * @param strength
     * @param critical
     * @param resilience
     * @param location_at
     */
    public Hero(String name, String magic_type, int level, int HP, int MP, int maxHP, int maxMP,
                int strength, int critical, int resilience, String location_at) {
        this.name = name;
        this.magic_type = magic_type;
        this.level = level;
        this.HP = HP;
        this.MP = MP;
        this.maxHP = maxHP;
        this.maxMP = maxMP;
        this.strength = strength;
        this.critical = critical;
        this.resilience = resilience;
        this.location_at = location_at;
        this.current_experience = 0;
        this.gold = 30;

        spells.add("Discharge");
    }


    /**
     * Will calculate the hero's attack power by adding the hero's strength,
     * and the strength of the weapon equipped together. Then add a random number
     * between the critical variable and the negative value of the critical variable.
     *
     * @return attack_power the power of the attack that was just calculated
     */
    public int hero_attack() {
        int attack_power = ((strength + weapon_equipped.get_strength()) / 2); //subject to change
        Random random = new Random();
        if (critical > 0) {
            attack_power += random.nextInt((-1 * critical), critical);
        }
        return attack_power;
    }


    /**
     * Given an attack spell's name and how much Mana Points (MP) the spell uses
     * return true is the spell can be cast or false if it cannot. If the spell
     * is cast subtract the spell's MP for the hero's MP.
     *
     * @param spell_name the name of the spell being used
     * @param MP_cost the Mana Points (MP) the spell requires for use
     * @return boolean if the spell was cast
     */
    public boolean hero_attack_spell(String spell_name, int MP_cost) {
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
     * is cast subtract the spell's MP for the hero's MP and add the strength of
     * the spell or the healing power to the hero's HP.
     *
     * @param spell_name the name of the spell being used
     * @param healing_power the strength of the spell
     * @param MP_cost the Mana Points (MP) the spell requires for use
     * @return boolean if the spell was cast
     */
    public boolean hero_recovery_spell(String spell_name, int healing_power, int MP_cost) {
        if (spells.contains(spell_name.toString()) && (MP >= MP_cost)) {
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
     * When the enemy attacks the hero the enemy's attack power is used here to find
     * out how much damage is done to the hero. If the magic type of the attack is
     * the same as the hero's magic type the attack does less damage. The armor that is
     * equipped strength and the hero's resilience is subtracted for the attack power
     * to find the damage. The damage is then subtracted from the hero's Hit Point (HP).
     *
     * @param enemy_attack_power the power of the enemy's attack
     * @param attack_magic_type the magic type of the attack, none if not a spell attack
     * @return damage the damage done to the hero, so it can be output to the user
     */
    public int hero_defence(int enemy_attack_power, String attack_magic_type) {
        if (attack_magic_type.equals(magic_type)) {
            enemy_attack_power = enemy_attack_power / 2;
        }

        int damage = enemy_attack_power;

        if ((armor_equipped.get_strength() >= 0) && (resilience >= 0)) {
            damage -= ((armor_equipped.get_strength() + resilience) / 4);
        }

        if (damage <= 0) {
            damage = 1;
        }

        HP -= damage;

        return damage;
    }


    /**
     * When a monster is defeated you add the gold and experience from that monster
     * to the hero's gold and experience.
     *
     * @param victory_gold the amount of gold received from the monster
     * @param victory_experience the amount of experience received from the monster
     */
    public void add_spoils(int victory_gold, int victory_experience) {
        this.gold += victory_gold;
        this.current_experience += victory_experience;
    }


    /**
     * Find out if the hero's experience has reached the next amount need to level up.
     * if the hero is currently level 1 it will only check if the hero has enough to
     * make it to level 2. If the hero levels up their base values will be changed and
     * increased.
     *
     * @return boolean weather or not the hero leveled up
     */
    public boolean level_up() {
        boolean level_up = false;

        switch (level) {
            //currently level 1, see if level up to level 2
            case 1:
                if(current_experience >= 7) {
                    level = 2;
                    level_up = true;
                    //change stats
                    maxHP = 15;
                    maxMP = 5;
                    strength = 5;
                    critical = 1;
                    if (magic_type.toString().equals("None")) {
                        maxHP += 4;
                        maxMP = 0;
                        strength += 1;
                        resilience = 1;
                    }
                }
                break;

            case 2:
                if(current_experience >= 23) {
                    level = 3;
                    level_up = true;
                    //change stats
                    maxHP = 24;
                    maxMP = 10;
                    strength = 7;
                    critical = 2;
                    if (magic_type.toString().equals("None")) {
                        maxHP += 6;
                        maxMP = 0;
                        strength += 2;
                        resilience = 2;
                    }
                }
                break;

            case 3:
                if(current_experience >= 47) {
                    level = 4;
                    level_up = true;
                    //change stats
                    maxHP = 31;
                    maxMP = 16;
                    strength = 7;
                    critical = 2;
                    if (magic_type.toString().equals("None")) {
                        maxHP += 7;
                        maxMP = 0;
                        strength += 3;
                        resilience = 3;
                    }
                }
                break;

            case 4:
                if(current_experience >= 110) {
                    level = 5;
                    level_up = true;
                    //change stats
                    maxHP = 35;
                    maxMP = 20;
                    strength = 12;
                    critical = 3;
                    if (magic_type.toString().equals("None")) {
                        maxHP += 9;
                        maxMP = 0;
                        strength += 3;
                        resilience = 3;
                    }
                }
                break;

            case 5:
                if(current_experience >= 220) {
                    level = 6;
                    level_up = true;
                    //change stats
                    maxHP = 38;
                    maxMP = 24;
                    strength = 16;
                    critical = 4;
                    if (magic_type.toString().equals("None")) {
                        maxHP += 11;
                        maxMP = 0;
                        strength += 4;
                        resilience = 4;
                    }
                }
                break;

            case 6:
                if(current_experience >= 450) {
                    level = 7;
                    level_up = true;
                    //change stats
                    maxHP = 40;
                    maxMP = 26;
                    strength = 18;
                    critical = 4;
                    if (magic_type.toString().equals("None")) {
                        maxHP += 13;
                        maxMP = 0;
                        strength += 4;
                        resilience = 4;
                    }
                }
                break;

            case 7:
                if(current_experience >= 800) {
                    level = 8;
                    level_up = true;
                    //change stats
                    maxHP = 46;
                    maxMP = 29;
                    strength = 22;
                    critical = 5;
                    if (magic_type.toString().equals("None")) {
                        maxHP += 15;
                        maxMP = 0;
                        strength += 5;
                        resilience = 5;
                    }
                }
                break;

            case 8:
                if(current_experience >= 1300) {
                    level = 9;
                    level_up = true;
                    //change stats
                    maxHP = 50;
                    maxMP = 36;
                    strength = 30;
                    critical = 5;
                    if (magic_type.toString().equals("None")) {
                        maxHP += 16;
                        maxMP = 0;
                        strength += 6;
                        resilience = 6;
                    }
                }
                break;

            case 9:
                if(current_experience >= 2000) {
                    level = 10;
                    level_up = true;
                    //change stats
                    maxHP = 54;
                    maxMP = 40;
                    strength = 35;
                    critical = 6;
                    if (magic_type.toString().equals("None")) {
                        maxHP += 18;
                        maxMP = 0;
                        strength += 7;
                        resilience = 7;
                    }
                }
                break;

            default:
                level_up = false;
                break;
        }

        return level_up;
    }


    /**
     * Check the hero's inventory to see how many of a certain item it contains,
     * could be 0 or none.
     *
     * @param item_name name of the item we are looking for
     * @return int number of the item in the hero's inventory
     */
    public int get_number_of_item(String item_name) {
        int index = item_inventory.indexOf(item_name.toString());
        return item_amount.get(index);
    }

    /**
     * The hero used a healing item that restores HP to the hero. Add the items' strength
     * to the hero's Hit Points (HP). Then call remove_consumable() to remove on of the
     * items used for the hero' inventory.
     *
     * @param item the item that was used
     * @param strength the strength of the item being used
     */
    public void used_a_health_item(Items item, int strength) {
        HP += strength;
        if (HP > maxHP) {
            HP = maxHP;
        }
        remove_consumable(item, 1);
    }

    /**
     * The hero used a mana item that restores MP to the hero. Add the items' strength
     * to the hero's Mana Points (MP). Then call remove_consumable() to remove on of the
     * items used for the hero' inventory.
     *
     * @param item the item that was used
     * @param strength the strength of the item being used
     */
    public void used_a_mana_item(Items item, int strength) {
        MP += strength;
        if (MP > maxMP) {
            MP = maxMP;
        }
        remove_consumable(item, 1);
    }

    /**
     * Adding items to the hero's inventory. Used when the hero buys items in the shop that are
     * consumable items like potion and eithers and not weapons, armor, or spells.
     *
     * @param item the item that is being added
     * @param amount the number of that item being added
     */
    public void add_consumable(Items item, int amount) {
        int index = item_inventory.indexOf(item.get_name());
        int current_amount = item_amount.get(index);
        current_amount += amount;
        item_amount.set(index, amount);
    }

    /**
     * Removing items to the hero's inventory. Used when the hero sells items in the
     * shop that are consumable items like potions and eithers and not weapons, armor,
     * or spells. Also called when the hero uses a consumable item.
     *
     * @param item the item that is being added
     * @param amount the number of that item being added
     */
    public void remove_consumable(Items item, int amount) {
        int index = item_inventory.indexOf(item.get_name());
        int current_amount = item_amount.get(index);
        current_amount -= amount;
        item_amount.set(index, current_amount);
    }

    /**
     * Adds a spell to the hero's ArrayList of spells so the spell can be later used
     * in combat.
     *
     * @param name the name of the spell being added
     */
    public void add_spell(String name) {
        if (!(spells.contains(name))) {
            spells.add(name);
        }
    }

    /**
     * Checks the ArrayList of spells to see if the spell is there or not. If it is
     * the hero can cast that spell.
     *
     * @param name the name of the spell
     * @return boolean whether or ont the spell name is there
     */
    public boolean check_if_have_spell(String name) {
        if (spells.contains(name)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adding or subtracting gold from the hero's supply of gold. The parameter is
     * negative if it is being subtracted. Used if buying or selling items.
     *
     * @param gold the amount of gold being added or subtracted
     */
    public void edit_gold(int gold) {this.gold += gold;}

    //getters for all but the two arrays in this class
    public Items get_weapon_equipped() {return weapon_equipped;}
    public Items get_armor_equipped() {return armor_equipped;}
    public int get_weapon_attack() {return weapon_equipped.get_strength();}
    public int get_armor_defence() {return armor_equipped.get_strength();}
    public int get_current_experience() {return current_experience;}
    public int get_gold() {return gold;}
    public String get_location_at() {return location_at;}

    /**
     * Puts all the names of spells inthe list of spells into one string. Used to see what
     * spells the hero has and display them to the user.
     *
     * @return spell_list
     */
    public String get_spells() {
        String spell_list = "";
        for (String spell : spells) {
            spell_list += spell + "\n";
        }
        return spell_list;
    }


    //setters
    public void set_weapon(Items item) {this.weapon_equipped = item;}
    public void set_armor(Items item) {this.armor_equipped = item;}
    public void set_location_at(String location_at) {this.location_at = location_at;}
}

