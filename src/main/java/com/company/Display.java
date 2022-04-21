/**
 * DQ-lite
 * Display class
 *
 * This class will not create objects. This class is a collection of methods that are
 * related in use and purpose. The methods were put in a separate class to help make
 * the code easier to read and easier to find what you are looking for. These methods
 * are called by the command() method in the GUI class. This class also extends the GUI
 * class because the methods were originally in that class and use the variables and
 * objects that are in the class.
 *
 * All the methods in this class are actions that the player will use to get more
 * detailed information about their character. None of these methods will cost a
 * turn if used in battle.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

public class Display extends GUI {
    /**
     * This will display all the items and spells that are currently
     * available in the shop.
     */
    public static void shop_inventory() {
        output_all += ("%-15s%-15s%-10s%-10s\n".formatted("Name", "Effect/Type", "Strength", "Cost"));
        for (int i = 0; i < shop_inventory_items.size(); i++) {
            if (shop_inventory_items.get(i).get_type().equals("consumable")) {
                output_all += ("%-15s%-15s%-10d%-10d".formatted(shop_inventory_items.get(i).get_name(), shop_inventory_items.get(i).get_effect(), shop_inventory_items.get(i).get_strength(), shop_inventory_items.get(i).get_cost()));
            } else {

                output_all += ("%-15s%-15s%-10d%-10d".formatted(shop_inventory_items.get(i).get_name(), shop_inventory_items.get(i).get_type(), shop_inventory_items.get(i).get_strength(), shop_inventory_items.get(i).get_cost()));
            }
            output_all += (" \n");
        }

        output_all += ("\nSpells\n%-15s%-15s%-15s%-10s%-10s\n".formatted("Name", "Type", "Effect", "Strength", "Cost"));
        for (int i = 0; i < shop_inventory_spells.size(); i++) {
            output_all += ("%-15s%-15s%-15s%-10d%-10d\n".formatted(shop_inventory_spells.get(i).get_spell_name(), shop_inventory_spells.get(i).get_spell_type(), shop_inventory_spells.get(i).get_spell_effet(), shop_inventory_spells.get(i).get_spell_strength(), shop_inventory_spells.get(i).get_gold_cost()));
        }
    }

    /**
     * This will display all the playable character's stats to the user not
     * just the few that are show on the left side of the screen.
     */
    public static void player_stats() {
        output_all += ("%s level %d\n%-17s %s\n%-17s %d\n%-17s %d\n%-17s %d\n%-17s %d\n%-17s %d\n".formatted(
                hero.get_name(), hero.get_level(), "Magic Type:", hero.get_magic_type(), "Hit Points (HP):", hero.get_HP(), "Mana Points (MP):",
                hero.get_MP(), "Strength:", hero.get_strength(), "Gold:", hero.get_gold(), "Experience:", hero.get_current_experience()));
    }

    /**
     * This will show the user the amount of each item they have in their inventory.
     * If they do not have any it will not appear.
     */
    public static void inventory() {
        output_all += ("\n%s's Inventory\nWeapon Equipped: %s\nArmor Equipped: %s\n".formatted(hero.get_name(), hero.get_weapon_equipped().get_name(), hero.get_armor_equipped().get_name()));
        if (hero.get_number_of_item("Potion") > 0) {
            output_all += ("%s %d\n".formatted("Potion:", hero.get_number_of_item("Potion")));
        }
        if (hero.get_number_of_item("Super Potion") > 0) {
            output_all += ("%s %d\n".formatted("Super Potion:", hero.get_number_of_item("Super Potion")));
        }
        if (hero.get_number_of_item("Either") > 0) {
            output_all += ("%s %d\n".formatted("Either:", hero.get_number_of_item("Either")));
        }
        if (hero.get_number_of_item("Super Either") > 0) {
            output_all += ("%s: %d\n".formatted("Super Either:", hero.get_number_of_item("Super Either")));
        }
        if (hero.get_number_of_item("Full Either") > 0) {
            output_all += ("%s %d\n".formatted("Full Either:", hero.get_number_of_item("Full Either")));
        }
    }

    /**
     * This will show the user all the spell the currently have and can use in battle.
     */
    public static void spells() {
        String spell_list = hero.get_spells();
        output_all += (hero.get_name() + "'s Spells\n" + spell_list + "\n");
    }
}
