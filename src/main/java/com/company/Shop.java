/**
 * DQ-lite
 * Shop class
 *
 * This class will not create objects. This class is a collection of methods that are
 * related in use and purpose. The methods were put in a separate class to help make
 * the code easier to read and easier to find what you are looking for. These methods
 * are called by the command() method in the GUI class. This class also extends the GUI
 * class because the methods were originally in that class and use the variables and
 * objects that are in the class.
 *
 * All the methods in this class are actions that the playable character or hero can
 * perform while in the shop like buying and selling items.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

import java.io.FileNotFoundException;

public class Shop extends GUI {
    /**
     * Moves the hero or playable character to the shop cannot do if in battle.
     *
     * @throws FileNotFoundException
     */
    public static void go_to_shop() throws FileNotFoundException {
        output_all += ("You have entered the supply shop just out side the arena.\n" +
                "When Buying and Selling items adjust the spinner to indicate how \nmany. " +
                "Only 1 armor or weapon and be bought or sold at one time.\n");

        hero.set_location_at("shop");

        create_shop_inventory();
    }

    /**
     * Based on the hero's level items and spells will be added to the shop inventory.
     * The items in the shop will be available for purchase. Items and spells that are
     * the same as the hero's level and the items that are 2 levels below the hero's
     * level will be available in the shop.
     */
    public static void create_shop_inventory() {
        for (int i = 0; i < item_list.size(); i++) {
            if (item_list.get(i).get_level() <= hero.get_level() && item_list.get(i).get_level() >= (hero.get_level() - 2)) {
                shop_inventory_items.add(item_list.get(i));
            }
        }

        for (int i = 0; i < spell_list.size(); i++) {
            if (spell_list.get(i).get_level_available() <= hero.get_level() && spell_list.get(i).get_level_available() >= (hero.get_level() - 2)) {
                shop_inventory_spells.add(spell_list.get(i));
            }
        }
    }

    /**
     * Based on the parameter and the amount variable that is input from the user by
     * adjusting the spinner on the screen the hero buy's a given number of an item.
     * the player must have enough gold to purchase the specified amount of whatever
     * item they are trying to buy. I f they do not then the user would be told how
     * much the items cost and how much gold they have explaining that they do not
     * have enough.
     *
     * @param item the item that is being purchased
     */
    public static void buy(Items item) {
        boolean inStore = false;

        if (amount > 0) {
            for(int i = 0; i < shop_inventory_items.size(); i++) {
                if (shop_inventory_items.get(i).get_name().equals(item.get_name())) {
                    if (hero.get_gold() >= (item.get_cost() * amount)) {
                        if (item.get_type().equals("consumable")) {

                            hero.add_consumable(item, amount);
                            hero.edit_gold(-1 * (amount * item.get_cost()));
                            if (amount == 1) {
                                output_all += (hero.get_name() + " has added a " + item.get_name() + " to their inventory.\n");
                            } else {
                                output_all += (hero.get_name() + " has added " + amount + " " + item.get_name() + "'s to their inventory.\n");
                            }
                        } else if (item.get_type().equals("Weapon")) {
                            if (!(hero.get_weapon_equipped().get_name().equals("None"))) {
                                sell(hero.get_weapon_equipped());
                            }

                            hero.set_weapon(item);
                            output_all += (hero.get_name() + " has added and equipped a " + item.get_name() + " as a weapon.\n");

                        } else if (item.get_type().equals("Armor")) {
                            if (!(hero.get_armor_equipped().get_name().equals("None"))) {
                                sell(hero.get_armor_equipped());
                            }

                            hero.set_armor(item);
                            output_all += (hero.get_name() + " has added and equipped " + item.get_name() + " as armor.\n");
                        }
                    } else {
                        if (amount == 1) {
                            output_all += ("Not enough gold for a " + item.get_name() + ", you need " + item.get_cost() + " and you have " + hero.get_gold() + ".");
                        } else {
                            output_all += ("Not enough gold for " + amount + " " + item.get_name() + "'s, you need " + (item.get_cost() * amount) + " and you have " + hero.get_gold() + ".");
                        }
                    }
                    inStore = true;
                }
            }
            if (!inStore) {
                output_all += (item.get_name() + " is not currently for sale.\n");
            }
        } else {
            output_all += ("Not a valid amount to purchase, please enter a whole number greater than 0.\n");
        }
    }

    /**
     * If the player has enough gold and the magic type is the same as theirs or type All
     * they buy the spell. It is added to their spell list and they can use it in battle.
     *
     * @param spell the that is being purchases
     */
    public static void buy_spell(Spells spell) {
        boolean inStore = false;

        if (amount > 0) {
            for(int i = 0; i < shop_inventory_spells.size(); i++) {
                if (shop_inventory_spells.get(i).get_spell_name().equals(spell.get_spell_name())) {
                    if (hero.get_gold() >= (spell.get_gold_cost())) {
                        if (!(hero.check_if_have_spell(spell.get_spell_name()))) {
                            if (spell.get_spell_type().equals(hero.get_magic_type()) || spell.get_spell_type().equals("All")) {
                                hero.add_spell(spell.get_spell_name());
                                output_all += (hero.get_name() + " has added " + spell.get_spell_name() + " to their list of spells.\n");
                            } else {
                                output_all += ("You do not have the same magic type as this spell.\n");
                            }
                        } else {
                            output_all += ("You already have this spell.\n");
                        }
                    } else {
                        output_all += ("Not enough gold for a " + item.get_name() + ", you need " + item.get_cost() + " and you have " + hero.get_gold() + ".");
                    }
                    inStore = true;
                }
            }
            if (!inStore) {
                output_all += (spell.get_spell_name() + " is not currently for sale.\n");
            }
        } else {
            output_all += ("Not a valid amount to purchase, please enter a whole number greater than 0.\n");
        }
    }

    /**
     * If the player has the items in their inventory the can sell them for 60% of
     * purchased price. the user can specify how many they want to sell by adjusting
     * the spinner.
     *
     * @param item the item being sold
     */
    public static void sell(Items item) {
        int amount_in_inventory = hero.get_number_of_item(item.get_name());

        if (amount > 0) {
            if (amount_in_inventory >= amount) {
                if (item.get_type().equals("consumable")) {
                    hero.remove_consumable(item, amount);
                    hero.edit_gold(amount * item.get_selling_price());
                    if (amount == 1) {
                        output_all += (hero.get_name() + " has sold a " + item.get_name() + " for " + item.get_selling_price() + " gold.\n");
                    } else {
                        output_all += (hero.get_name() + " has sold " + amount + " " +  item.get_name() + "'s for " + (item.get_selling_price() * amount) + " gold.\n");
                    }
                } else if (item.get_type().equals("weapon")) {
                    hero.edit_gold(amount * item.get_selling_price());
                    output_all += (hero.get_name() + " has sold their " + item.get_name() + " for " + item.get_selling_price() + " gold.\n");
                } else if (item.get_type().equals("armor")) {
                    hero.edit_gold(amount * item.get_selling_price());
                    output_all += (hero.get_name() + " has sold their " + item.get_name() + " for " + item.get_selling_price() + " gold.\n");
                }
            } else {
                output_all += ("You do not have enough " + item.get_name() + "'s to sell that many.\n");
            }
        } else {
            output_all += ("Not a valid amount to sell, please enter a whole number greater than 0.\n");
        }
    }
}
