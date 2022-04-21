/**
 * DQ-lite
 * Battle class
 *
 * This class will not create objects. This class is a collection of methods that are
 * related in use and purpose. The methods were put in a separate class to help make
 * the code easier to read and easier to find what you are looking for. These methods
 * are called by the command() method in the GUI class. This class also extends the GUI
 * class because the methods were originally in that class and use the variables and
 * objects that are in the class.
 *
 * All the methods in this class control a battle in the game.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

import java.io.FileNotFoundException;
import java.util.Random;

public class Battle extends GUI {

    /**
     * At the start of a battle the list of possible monsters is created, a number
     * between 4 and 7 is randomly selected. This number is the number of monsters
     * you must defeat before the battle is over.
     *
     * @throws FileNotFoundException
     */
    public static void start_battle() throws FileNotFoundException {
        Random random = new Random();
        hero.set_location_at("battle");
        enemy_list = create_enemy_list();
        monsters_left = random.nextInt(4, 7);

        output_all += ("The battle has started.\n");

        set_next_enemy();
    }

    /**
     * At the end of the battle after the player defeats all the monsters they need to
     * and if they are still alive the are moved to the inn.
     */
    public static void end_battle() {
        output_all += ("\nThe battle is over.\n");
        level_up();
        output_all += ("You are now in the inn.\n");
        hero.set_location_at("inn");
    }

    /**
     * this will reset the enemy object with a new monster randomly selected from the
     * list of monster for this battle and the number of monster left to battle will
     * have one subtracted from it.
     *
     * @throws FileNotFoundException
     */
    public static void set_next_enemy() throws FileNotFoundException {
        Random random = new Random();

        String next_enemy = enemy_list.get(random.nextInt(0, enemy_list.size()));
        enemy = Data.get_enemy_data(next_enemy);

        monsters_left -= 1;

        output_all += ("\nA " + enemy.get_name() + " has appeared before you.\n");
    }

    /**
     * Check to see if the hero has leveled after that battle.
     */
    public static void level_up() {
        boolean level_up = hero.level_up();

        if (level_up) {
            output_all += ("LEVEL UP!!\n");
            Display.player_stats();
        }
    }

    /**
     * If the player performs an action that uses a turn this method will be called and
     * that will also it means the enemy gets a turn if the player's move did not kill
     * the enemy.
     *
     * @throws FileNotFoundException
     */
    public static void hero_turn() throws FileNotFoundException {
        if (enemy.check_if_alive()) {
            enemy_turn();
        } else {
            output_all += (enemy.get_name() + " has been defeated.\n\n"
                + hero.get_name() + " gains " + enemy.get_victory_gold() + " gold and " + enemy.get_victory_experience() + " experience points.\n");
            hero.add_spoils(enemy.get_victory_gold(), enemy.get_victory_experience());
            if (monsters_left == 0) {
                end_battle();
            } else {
                set_next_enemy();
            }
        }
    }

    /**
     * This method will decide what the enemy will do for its turn.
     *
     * @throws FileNotFoundException
     */
    public static void enemy_turn() throws FileNotFoundException {
        Random random = new Random();

        int number_of_spells = enemy.get_number_of_spells();
        if (number_of_spells > 0) {
            String has_recovery = enemy.check_if_have_recovery_spell();
            if (!(has_recovery.equals("none"))) {
                int chance = random.nextInt(0, 2);
                if (chance == 0) {
                    Spells spell = Data.get_spell_data(has_recovery);
                    Action.cast_spell(spell, "enemy");
                }
            }

            if (number_of_spells == 1) {
                int chance = random.nextInt(0, 2);
                if (chance == 0) {
                    String spell_name = enemy.get_spell_at(0);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else {
                    Action.physical_attack("enemy");
                }
            } else if (number_of_spells == 2) {
                int chance = random.nextInt(0, 3);
                if (chance == 0) {
                    String spell_name = enemy.get_spell_at(0);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else if (chance == 1) {
                    String spell_name = enemy.get_spell_at(1);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else {
                    Action.physical_attack("enemy");
                }
            } else if (number_of_spells == 3) {
                int chance = random.nextInt(0, 4);
                if (chance == 0) {
                    String spell_name = enemy.get_spell_at(0);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else if (chance == 1) {
                    String spell_name = enemy.get_spell_at(1);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else if (chance == 2) {
                    String spell_name = enemy.get_spell_at(2);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else {
                    Action.physical_attack("enemy");
                }
            } else if (number_of_spells == 4) {
                int chance = random.nextInt(0, 5);
                if (chance == 0) {
                    String spell_name = enemy.get_spell_at(0);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else if (chance == 1) {
                    String spell_name = enemy.get_spell_at(1);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else if (chance == 2) {
                    String spell_name = enemy.get_spell_at(2);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else if (chance == 3) {
                    String spell_name = enemy.get_spell_at(3);
                    Spells spell = Data.get_spell_data(spell_name);
                    Action.cast_spell(spell, "enemy");
                } else {
                    Action.physical_attack("enemy");
                }
            }
        } else {
            Action.physical_attack("enemy");
        }

        if (!(hero.check_if_alive())) {
            output_all += (hero.get_name() + " has fallen in battle.\n");
        }
    }
}
