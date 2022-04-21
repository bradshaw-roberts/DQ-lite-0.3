/**
 * DQ-lite
 * Entity class
 *
 * This class is the superclass to Hero and Enemy. Hero and Enemy
 * share a number of variables and methods. All of the variables
 * and methods that they share have been added to the superclass
 * Entity.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

import java.util.ArrayList;

public class Entity {
    protected String name, magic_type;
    protected int level, HP, MP, maxHP, maxMP, strength, critical, resilience;
    protected ArrayList<String> spells = new ArrayList<String>();

    /**
     * Return true or false if the hero or monster is alive by checking if the
     * Hit Points (HP) is less than 0.
     *
     * @return boolean
     */
    public boolean check_if_alive() {
        boolean alive = true;
        if (this.HP <= 0) {
            alive = false;
        }

        return alive;
    }

    //getters for all variables in this class
    public String get_name() {return name;}
    public int get_level() {return level;}
    public int get_HP() {return HP;}
    public int get_MP() {return MP;}
    public int get_maxHP() {return maxHP;}
    public int get_maxMP() {return maxMP;}
    public int get_strength() {return strength;}
    public int get_critical() {return critical;}
    public int get_resilience() {return resilience;}
    public String get_magic_type() {return magic_type;}

    //setters for all variables in this class
    public void set_name (String name) {this.name = name;}
    public void set_HP (int HP) {this.HP = HP;}
    public void set_MP (int MP) {this.MP = MP;}
    public void set_maxHP (int maxHP) {this.maxHP = maxHP;}
    public void set_maxMP (int maxMP) {this.maxMP = maxMP;}
    public void set_strength (int strength) {this.strength = strength;}
    public void set_critical (int critical) {this.critical = critical;}
    public void set_resilience (int resilience) {this.resilience = resilience;}
    public void set_magic_type (String magic_type) {this.magic_type = magic_type;}
}
