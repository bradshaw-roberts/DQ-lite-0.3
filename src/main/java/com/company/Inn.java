/**
 * DQ-lite
 * Inn class
 *
 * This class will not create objects. This class is a collection of methods that are
 * related in use and purpose. The methods were put in a separate class to help make
 * the code easier to read and easier to find what you are looking for. These methods
 * are called by the command() method in the GUI class. This class also extends the GUI
 * class because the methods were originally in that class and use the variables and
 * objects that are in the class.
 *
 * All the methods in this class are actions that the playable character can do while
 * in the inn.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

import java.io.FileNotFoundException;

public class Inn extends GUI {
    /**
     * Move the hero or playable character to the inn, cannot do if in battle.
     *
     * @throws FileNotFoundException
     */
    public static void go_to_inn() throws FileNotFoundException {
        output_all += ("You have entered the local inn, what will you do now?\n");
        output_all += ("If you would like to rest and recover HP and MP use command \"R\"\n");

        hero.set_location_at("inn");
    }

    /**
     * The hero's Hit Points (HP) and Mana Point (MP) will be set to the max of each
     * respectfully. While in the inn the player can rest to restore HP and MP to max.
     */
    public static void rest() {
        hero.set_HP(hero.get_maxHP());
        hero.set_MP(hero.get_maxMP());
        output_all += (hero.get_name() + " has rested to full health.\nHP is now " + hero.get_HP() + " and MP is now "  + hero.get_MP() + ".\n");
    }
}
