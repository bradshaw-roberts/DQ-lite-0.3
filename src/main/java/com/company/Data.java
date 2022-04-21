/**
 * DQ-lite
 * Data class
 *
 * This class will not make objects of its own. This class will use text files to create
 * Enemy, Spells, and Items objects. Each of the three methods in this class works the
 * same way, but one creates the Enemy object, one creates the Spells object, and the
 * other creates the Items object. I will explain how the get_item_data() method works,
 * but the get_enemy_data() and get_spell_data() methods work the same way.
 *
 * The method will get a String that is the name of an item as a parameter. using that
 * String the method will search the Items.txt file for the line that contains the
 * String parameter. Once is has the correct line from the file the method will take
 * each value in the line that is separated by a | symbol and place that value in a
 * variable. With all the information needed located and put into variables the method
 * will create an Items object using those variables and then return that object.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Data {

    public static Enemy enemy = null;
    public static Spells spell = null;
    public static Items item = null;

    /**
     * Creates an Enemy object that is needed by the program. The information
     * for the object is found in a text file in a process explained at the top
     * of this file.
     *
     * @param enemy_name the name of the enemy that we want an object for
     * @return Enemy the object created based on the parameter
     * @throws FileNotFoundException
     */
    public static Enemy get_enemy_data(String enemy_name) throws FileNotFoundException {
        Scanner input = new Scanner(new File ("src/main/resources/Enemies.txt"));

        String line = "", name = "", magic_type = "", temp = "", spell_list = "";
        int level = 0, HP = 0, MP = 0, strength = 0, critical = 0, resilience = 0, agility = 0, victory_experience = 0, victory_gold = 0;

        do {
            line = input.nextLine();
            name = line.substring(0, line.indexOf("|"));

            if (name.equals(enemy_name)) {
                line = line.substring(line.indexOf("|") + 1);
                magic_type = line.substring(0, line.indexOf("|"));

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                level = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                HP = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                MP = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                strength = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                critical = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                resilience = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                agility = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                victory_experience = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                victory_gold = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                spell_list = line.substring(0, line.indexOf("|"));

                Enemy temp_enemy = new Enemy(name, magic_type, level, HP, MP, strength, critical, resilience, agility, victory_experience, victory_gold, spell_list);

                return temp_enemy;
            }
        } while(input.hasNext());

        Enemy backup_enemy = new Enemy("Slime", "water", 1, 10, 0, 2, 1, 0, 25, 2, 2, "None");

        return backup_enemy;
    }


    /**
     * Creates a Spells object that is needed by the program. The information
     * for the object is found in a text file in a process explained at the top
     * of this file.
     *
     * @param spell_name the name of the spell that we want an object for
     * @return Spells the object created based on the parameter
     * @throws FileNotFoundException
     */
    public static Spells get_spell_data(String spell_name) throws FileNotFoundException {
        Scanner input = new Scanner(new File ("src/main/resources/Spells.txt"));

        String line = "", name = "", spell_type = "", spell_effect = "", temp = "";
        int spell_strength = 0, spell_critical = 0, MP_cost = 0, level_available = 0, gold_cost = 0;

        do {
            line = input.nextLine();
            name = line.substring(0, line.indexOf("|"));

            if (name.equals(spell_name)) {
                line = line.substring(line.indexOf("|") + 1);
                spell_type = line.substring(0, line.indexOf("|"));

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                spell_strength = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                spell_critical = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                MP_cost = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                spell_effect = line.substring(0, line.indexOf("|"));

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                level_available = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                gold_cost = Integer.parseInt(temp);

                Spells temp_spell = new Spells(name, spell_type, spell_strength, spell_critical, MP_cost, spell_effect, level_available, gold_cost);

                return temp_spell;
            }
        } while(input.hasNext());

        Spells backup_spell = new Spells("default", "All", 1, 1, 1, "attack", 11, 10);

        return backup_spell;
    }


    /**
     * Creates an Items object that is need by the program. The information
     * for the object is found in a text file in a process explained at the top
     * of this file.
     *
     * @param item_name the name of the enemy that we want an object for
     * @return Items the object created based on the parameter
     * @throws FileNotFoundException
     */
    public static Items get_item_data(String item_name) throws FileNotFoundException {
        Scanner input = new Scanner(new File ("src/main/resources/Items.txt"));

        String line = "", name = "", effect = "", type = "", temp = "";
        int strength = 0, level = 0, cost = 0;

        do {
            line = input.nextLine();
            name = line.substring(0, line.indexOf("|"));

            if (name.equals(item_name)) {
                line = line.substring(line.indexOf("|") + 1);
                effect = line.substring(0, line.indexOf("|"));

                line = line.substring(line.indexOf("|") + 1);
                type = line.substring(0, line.indexOf("|"));

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                strength = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                level = Integer.parseInt(temp);

                line = line.substring(line.indexOf("|") + 1);
                temp = line.substring(0, line.indexOf("|"));
                cost = Integer.parseInt(temp);

                Items temp_item = new Items(name, effect, type, strength, level, cost);

                return temp_item;
            }
        } while(input.hasNext());

        Items backup_item = new Items("default", "health", "consumable", 10, 1, 10);

        return backup_item;
    }
}

