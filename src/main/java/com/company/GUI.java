/**
 * DQ-lite
 * GUI class
 *
 * This class creates and manages the GUI. It also holds the objects and
 * variables shared with the entire program.
 *
 * @author Bradshaw Roberts
 * @version 0.2
 * Started 3/16/2022
 */

package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;

public class GUI extends Application {

    public static boolean end_program = false; //has the program ended or not
    public static boolean intro_name_finished = false; //flag for the intro
    public static boolean intro_magic_finished = false; //flag for the intro
    public static String output_all = ""; //main output to the user
    public static int amount = 0; // this amount changes depending on the spinner

    //Hero object
    public static Hero hero = new Hero("No Name", "Fire", 0, 1, 1, 1, 1, 1, 0, 0, "inn");
    public static String hero_name = ""; //for the intro
    public static String hero_magic = ""; //for the intro

    //enemy object use by other classes
    public static Enemy enemy = new Enemy("Slime", "water", 1, 10, 0, 2, 1, 0, 25, 2, 2, "None,");
    public static Spells spell = null; //spell object use by other classes
    public static Items item = null; //item object use by other classes

    public static ArrayList<String> enemy_list = new ArrayList<String>(); //list if possible enemies to encounter for the current battle
    public static int monsters_left = 0; //number of monster left for the current battle

    //shop inventory both items and spell currently available for purchase in the shop
    public static ArrayList<Items> shop_inventory_items = new ArrayList<Items>();
    public static ArrayList<Spells> shop_inventory_spells = new ArrayList<Spells>();

    //Items list for quick reference
    public static ArrayList<Items> item_list;
    static {
        try {
            item_list = new ArrayList<Items>(Arrays.asList(Data.get_item_data("Potion"), Data.get_item_data("Super Potion"),
                    Data.get_item_data("Either"), Data.get_item_data("Super Either"), Data.get_item_data("Full Either"),
                    Data.get_item_data("Stick"), Data.get_item_data("Club"), Data.get_item_data("Copper Sword"), Data.get_item_data("Hand Axe"), Data.get_item_data("Broad Sword"), Data.get_item_data("Flame Sword"),
                    Data.get_item_data("Plain Clothes"), Data.get_item_data("Leather Armor"), Data.get_item_data("Chain Mail"), Data.get_item_data("Half Plate"), Data.get_item_data("Full Plate")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Spells list for quick reference
    public static ArrayList<Spells> spell_list;
    static {
        try {
            spell_list = new ArrayList<Spells>(Arrays.asList(Data.get_spell_data("Heal"),
                    Data.get_spell_data("Cure Water"), Data.get_spell_data("Divine Blessing"), Data.get_spell_data("Fountain Of Youth"), Data.get_spell_data("Full Rest"),
                    Data.get_spell_data("Flame"), Data.get_spell_data("Fire Ball"), Data.get_spell_data("Hell Fire"), Data.get_spell_data("Bubble"),
                    Data.get_spell_data("Water Ball"), Data.get_spell_data("Splash Flow"), Data.get_spell_data("Gust"), Data.get_spell_data("Wind Shock"), Data.get_spell_data("Tornado")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //Everything for the GUI
    public static Label hero_name_label = new Label(hero.get_name());
    public static Label hero_level_label = new Label("Level " + hero.get_level());
    public static Label hero_HP_label = new Label("HP    %-2d / %-2d".formatted(hero.get_HP(), hero.get_maxHP()));
    public static Label hero_MP_label = new Label("MP    %-2d / %-2d".formatted(hero.get_MP(), hero.get_maxMP()));
    public static Label hero_location_label = new Label("Location: ");

    private Label current_enemy_label = new Label("Current Enemy");
    public static Label enemy_name_label = new Label(enemy.get_name());
    public static Label enemy_level_label = new Label("Level " + enemy.get_level());
    public static Label enemy_HP_label = new Label("HP    %-2d / %-2d".formatted(enemy.get_HP(), enemy.get_maxHP()));

    private Label spacer_label = new Label(" ");

    public static TextField command_line_text_field = new TextField();
    private Button submit_button = new Button("Submit");
    private Label command_line_label = new Label("Enter Name: ");
    public static TextArea screen_output_text_area = new TextArea();
    public static Spinner<Integer> amount_spinner = new Spinner(1, 10, 1);

    private Label reference_label = new Label("Command Reference Sheet");
    private TextArea reference_text_area = new TextArea(
            """
            Go Commands (G)
            - move to different location
            Go to Inn (GI)
            Go to Shop (GS)
            Go to Battle (GB) or
            Go to Fight (GF)
            
            Rest Command (R)
            - rest in the inn
            
            Display Commands (D)
            Player Stats (DPS)
            Inventory (DI)
            Spells (DS)
            Shop Inventory (DSI)
            
            Use Commands (U)
            U followed by the first letter for
            every word in the item name
            Example:
            Use Potion (UP)
            Use Full Either (UFE)
            
            Buy Commands (B)
            B followed by the first letter for
            every word in the item name
            Example:
            Buy Potion (BP)
            Buy Plain Clothes (BPC)
            
            Sell Commands (S)
            S followed by the first letter for
            every word in the item name
            Example:
            Sell Potion (SP)
            Sell Full Either (SFE)
            
            Attack Command (A)
            - physical attack
            
            Cast Commands (C)
            C followed by the first letter for
            every word in the spell name
            Example:
            Cast Heal (CH)
            Cast Flame (CF)
            
            
            Good Luck""");

    public static HBox in = new HBox();

    //left side button tree
    public static int command_button_level = 10;
    public static Label command_buttons_label = new Label("Go to");
    public static VBox right_Vbox = new VBox();

    public static Button back_command_button = new Button("Back");

    public static Button go_command_button = new Button("Go");
    public static Button go_to_inn_command_button = new Button("Inn");
    public static Button go_to_shop_command_button = new Button("Shop");
    public static Button go_to_battle_command_button = new Button("Battle");

    public static Button display_command_button = new Button("Display");
    public static Button display_player_stats_command_button = new Button("Player Stats");
    public static Button display_inventory_command_button = new Button("Inventory");
    public static Button display_spells_command_button = new Button("Spells");
    public static Button display_shop_inventory_command_button = new Button("Shop Inventory");

    public static Button buy_command_button = new Button("Buy");

    public static Button sell_command_button = new Button("Sell");

    public static Button use_command_button = new Button("Use");

    public static Button cast_command_button = new Button("Cast");

    public static Button rest_command_button = new Button("Rest");

    public static Button attack_command_button = new Button("Attack");


    @Override
    public void start(Stage applicationStage) {
        command_line_text_field.setPrefColumnCount(30);
        command_line_text_field.setEditable(true);
        amount_spinner.setEditable(true);
        screen_output_text_area.setEditable(false);
        reference_text_area.setEditable(false);

        amount_spinner.setPrefSize(75, 25);
        screen_output_text_area.setPrefSize(500, 250);
        reference_text_area.setPrefSize(200, 250);

        //right_Vbox.setAlignment(Pos.CENTER);

        VBox hero_Vbox = new VBox(hero_name_label, hero_level_label, hero_HP_label, hero_MP_label, spacer_label, hero_location_label);
        VBox enemy_Vbox = new VBox(current_enemy_label, enemy_name_label, enemy_level_label, enemy_HP_label);
        VBox left_Vbox = new VBox(hero_Vbox, spacer_label, enemy_Vbox);

        left_Vbox.setPrefWidth(148);

        in = new HBox(command_line_label,command_line_text_field, amount_spinner, submit_button);
        VBox inAndOut = new VBox(screen_output_text_area, in);

        //VBox right_Vbox = new VBox(reference_label, reference_text_area);

        HBox all = new HBox(left_Vbox, inAndOut, right_Vbox);

        hero_Vbox.setPadding(new Insets(10, 10, 10, 10));
        enemy_Vbox.setPadding(new Insets(10, 10, 10, 10));
        in.setPadding(new Insets(10, 10, 10, 0));
        inAndOut.setPadding(new Insets(10, 10, 10, 10));
        all.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(all);

        hero_name_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        hero_level_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        hero_HP_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        hero_MP_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        hero_location_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        current_enemy_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        enemy_name_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        enemy_level_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        enemy_HP_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        command_line_text_field.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        screen_output_text_area.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        command_line_label.setFont(Font.font(java.awt.Font.MONOSPACED, 13));
        submit_button.setFont(Font.font(java.awt.Font.MONOSPACED, 13));


        screen_output_text_area.setText("Enter your name where it says Enter a Command below\n(10 characters max).\n");

        //Submit button action event
        submit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (intro_name_finished && intro_magic_finished) {
                    //command_action(command_line_text_field.getText());
                    if (command_button_level == 23) {
                        command_action("B");
                    } else if (command_button_level == 24) {
                        command_action("S");
                    } else if (command_button_level == 35) {
                        command_action("U");
                    } else if (command_button_level == 36) {
                        command_action("C");
                    }
                } else if (intro_name_finished) {
                    boolean accepted = intro_scene_magic();
                    if (accepted) {
                        intro_magic_finished = true;
                        hero = new Hero(hero_name, hero_magic);
                        output_all += (hero.get_name() + " with magic type " + hero.get_magic_type() + " has started their trial.\n");
                        update_command_buttons();
                        update_screen();
                    } else {
                        screen_output_text_area.setText("Command not valid, capitalization matters, try again.\n\nEnter a command to select a magic type\nFire(F) Water(WT) Wind(WD) None(N):");
                    }
                } else {
                    boolean accepted = intro_scene_name();
                    if (accepted) {
                        intro_name_finished = true;
                        screen_output_text_area.setText("Enter a command to select a magic type:\nFire(F) Water(WT) Wind(WD) None(N)");
                    } else {
                        screen_output_text_area.setText("The name entered is not the proper length, try again.\n\nEnter your name where it says Enter a Command below\n(10 characters max).");
                    }
                }
            }
        });

        //Key pressed action event for enter key, up arrow, and down arrow
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if (key.getCode().equals(KeyCode.ENTER)) {
                    if (intro_name_finished && intro_magic_finished) {
                        //command_action(command_line_text_field.getText());
                        if (command_button_level == 23) {
                            command_action("B");
                        } else if (command_button_level == 24) {
                            command_action("S");
                        } else if (command_button_level == 35) {
                            command_action("U");
                        } else if (command_button_level == 36) {
                            command_action("C");
                        }
                    } else if (intro_name_finished) {
                        boolean accepted = intro_scene_magic();
                        if (accepted) {
                            intro_magic_finished = true;
                            hero = new Hero(hero_name, hero_magic);
                            output_all += (hero.get_name() + " with magic type " + hero.get_magic_type() + " has started their trial.\n");
                            update_command_buttons();
                            update_screen();
                        } else {
                            screen_output_text_area.setText("Command not valid, capitalization matters, try again.\n\nEnter a command to select a magic type\nFire(F) Water(WT) Wind(WD) None(N):");
                        }
                    } else {
                        boolean accepted = intro_scene_name();
                        if (accepted) {
                            intro_name_finished = true;
                            screen_output_text_area.setText("Enter a command to select a magic type:\nFire(F) Water(WT) Wind(WD) None(N)");
                        } else {
                            screen_output_text_area.setText("The name entered is not the proper length, try again.\n\nEnter your name where it says 'Enter Command' below\n(10 characters max).");
                        }
                    }
                } else if (key.getCode().equals(KeyCode.UP)) {
                    if (amount_spinner.getValue() < 10) {
                        amount_spinner.getValueFactory().setValue(amount_spinner.getValue() + 1);
                    }
                } else if (key.getCode().equals(KeyCode.DOWN)) {
                    if (amount_spinner.getValue() > 1) {
                        amount_spinner.getValueFactory().setValue(amount_spinner.getValue() - 1);
                    }
                }
            }
        });

        go_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (hero.get_location_at().equals("inn")) {
                    command_button_level = 11;
                } else if (hero.get_location_at().equals("shop")) {
                    command_button_level = 21;
                }
                update_command_buttons();
            }
        });
        go_to_inn_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_button_level = 10;
                update_command_buttons();
                command_action("GI");
            }
        });
        go_to_shop_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_button_level = 20;
                update_command_buttons();
                command_action("GS");
            }
        });
        go_to_battle_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_button_level = 30;
                update_command_buttons();
                command_action("GB");
            }
        });

        display_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (hero.get_location_at().equals("inn")) {
                    command_button_level = 12;
                } else if (hero.get_location_at().equals("shop")) {
                    command_button_level = 22;
                } else if (hero.get_location_at().equals("battle")) {
                    command_button_level = 32;
                }
                update_command_buttons();

            }
        });
        display_player_stats_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_action("DPS");
            }
        });
        display_inventory_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_action("DI");
            }
        });
        display_spells_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_action("DS");
            }
        });
        display_shop_inventory_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_action("DSI");
            }
        });

        buy_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_button_level = 23;
                update_command_buttons();
            }
        });

        sell_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_button_level = 24;
                update_command_buttons();
            }
        });

        use_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_button_level = 35;
                update_command_buttons();
            }
        });

        cast_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_button_level = 36;
                update_command_buttons();
            }
        });

        rest_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_action("R");
            }
        });

        attack_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                command_action("A");
            }
        });

        back_command_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int subtract = command_button_level%10;
                if (subtract > 0) {
                    command_button_level -= subtract;
                }
                update_command_buttons();
            }
        });


        applicationStage.setScene(scene);
        applicationStage.setTitle("DQ-lite");
        applicationStage.setWidth(925);
        applicationStage.setHeight(350);
        applicationStage.show();
    }

    /**
     * This method is called everytime the enter key or the submit button is pressed.
     * That signal that the user has entered and new command and this method will call
     * command that will execute the command and the call update_screen.
     */
    public static void command_action(String commandString) {
        boolean executed = false;
        amount = amount_spinner.getValue();

        //if (!(command_line_text_field.getText().equals(""))) {
            try {
                executed = command(commandString);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        //}

        if (executed == false) {
            output_all += ("Command not valid or command not permitted in current \nlocation/situation, try again.\n");
        }

        if (!(hero.check_if_alive())) {
            death_scene();
        }

        update_screen();
        command_line_text_field.clear();
    }

    /**
     * When command is called a series of actions are performed and each
     * action output something to the user or changes something about the
     * current state of the program. this method will update the main output in
     * the center of the screen along with the value on the left side of the
     * screen in case that changed.
     */
    public static void update_screen() {
        if (!(end_program)) {
            output_all += "\nEnter a command below.";
        }
        screen_output_text_area.setText(output_all);

        hero_name_label.setText(hero.get_name());
        hero_level_label.setText("Level " + hero.get_level());
        hero_HP_label.setText("HP    %-2d / %-2d".formatted(hero.get_HP(), hero.get_maxHP()));
        hero_MP_label.setText("MP    %-2d / %-2d".formatted(hero.get_MP(), hero.get_maxMP()));

        enemy_name_label.setText(enemy.get_name());
        enemy_level_label.setText("Level " + enemy.get_level());
        enemy_HP_label.setText("HP    %-2d / %-2d".formatted(enemy.get_HP(), enemy.get_maxHP()));

        if (hero.get_location_at().equals("inn")) {
            hero_location_label.setText("Location: Inn");
        } else if (hero.get_location_at().equals("shop")) {
            hero_location_label.setText("Location: Shop");
        } else if (hero.get_location_at().equals("battle")) {
            hero_location_label.setText("Location: Battle");
        }


        output_all = "";
    }

    public static void update_command_buttons() {
        in.setVisible(false);
        if (command_button_level%10 == 1) {
            go_command_buttons();
        } else if (command_button_level%10 == 2) {
            display_command_buttons();
        } else if (command_button_level%10 == 3) {
            buy_command_button();
        } else if (command_button_level%10 == 4) {
            sell_command_buttons();
        } else if (command_button_level%10 == 5) {
            use_command_buttons();
        } else if (command_button_level%10 == 6) {
            cast_command_buttons();
        } else if (command_button_level == 10) {
            inn_command_buttons();
        } else if (command_button_level == 20) {
            shop_command_buttons();
        } else if (command_button_level == 30) {
            battle_command_buttons();
        } else if (command_button_level == 23) {
            buy_command_button();
        } else if (command_button_level == 24) {
            sell_command_buttons();
        } else {
            command_buttons_label.setText("Error");
            right_Vbox.getChildren().setAll(command_buttons_label, back_command_button);
        }
    }

    public static void inn_command_buttons() {
        command_buttons_label.setText("Inn");
        right_Vbox.getChildren().setAll(command_buttons_label, rest_command_button, go_command_button, display_command_button);
    }

    public static void shop_command_buttons() {
        command_buttons_label.setText("Shop");
        right_Vbox.getChildren().setAll(command_buttons_label, buy_command_button, sell_command_button, go_command_button, display_command_button);
    }

    public static void battle_command_buttons() {
        command_buttons_label.setText("Battle");
        right_Vbox.getChildren().setAll(command_buttons_label, attack_command_button, cast_command_button, use_command_button, display_command_button);
    }

    public static void go_command_buttons() {
        command_buttons_label.setText("Go to");

        if (hero.get_location_at().equals("inn")) {
            right_Vbox.getChildren().setAll(command_buttons_label, go_to_shop_command_button, go_to_battle_command_button, back_command_button);
        } else if (hero.get_location_at().equals("shop")) {
            right_Vbox.getChildren().setAll(command_buttons_label, go_to_inn_command_button, go_to_battle_command_button, back_command_button);
        }
    }

    public static void display_command_buttons() {
        command_buttons_label.setText("Display");

        if (hero.get_location_at().equals("shop")) {
            right_Vbox.getChildren().setAll(command_buttons_label, display_player_stats_command_button, display_inventory_command_button, display_spells_command_button, display_shop_inventory_command_button, back_command_button);
        } else {
            right_Vbox.getChildren().setAll(command_buttons_label, display_player_stats_command_button, display_inventory_command_button, display_spells_command_button, back_command_button);
        }
    }

    public static void buy_command_button() {
        command_buttons_label.setText("Buy\nEnter the exact name of the item at \nthe bottom of the page.");
        right_Vbox.getChildren().setAll(command_buttons_label, back_command_button);
        in.setVisible(true);
    }

    public static void sell_command_buttons() {
        command_buttons_label.setText("Sell\nEnter the exact name of the item at \nthe bottom of the page.");
        right_Vbox.getChildren().setAll(command_buttons_label, back_command_button);
        in.setVisible(true);
    }

    public static void use_command_buttons() {
        command_buttons_label.setText("Use Item\nEnter the exact name of the item at \nthe bottom of the page.");
        right_Vbox.getChildren().setAll(command_buttons_label, back_command_button);
        in.setVisible(true);
    }

    public static void cast_command_buttons() {
        command_buttons_label.setText("Cast Spell\nEnter the exact name of the spell at \nthe bottom of the page.");
        right_Vbox.getChildren().setAll(command_buttons_label, back_command_button);
        in.setVisible(true);
    }

    /**
     * Simple calls the GUI to start
     *
     * @param args
     */
    public static void main(String [] args) {launch(args);}

    /**
     * This method will get the users name at the beginning of the game.
     * It will return true once the user has entered an acceptable input,
     * and it will then go onto the next step.
     *
     * @return boolean whether the user as inputted an acceptable name ot not
     */
    public static boolean intro_scene_name() {
        String name = command_line_text_field.getText();
        command_line_text_field.clear();

        if (name.equals("")) {
            name = "default to long";
        }

        if (name.length() > 10) {
            return false;
        } else {
            hero_name = name;
            return true;
        }
    }

    /**
     * This will allow the user to choose between 4 magic types at the
     * beginning onf the game.
     *
     * @return boolean whether the user as inputted an acceptable input or not
     */
    public static boolean intro_scene_magic() {
        String command = command_line_text_field.getText();
        command_line_text_field.clear();

        if (command.equals("F")) {
            hero_magic = "Fire";
            return true;
        } else if (command.equals("WT")) {
            hero_magic = "Water";
            return true;
        } else if (command.equals("WD")) {
            hero_magic = "Wind";
            return true;
        } else if (command.equals("N")) {
            hero_magic = "None";
            return true;
        } else {
            return false;
        }
    }

    /**
     * If the hero dies in battle this will tell the user the game is over.
     * It will also set end_program to true preventing any other command to work.
     */
    public static void death_scene() {
        output_all += ("Game Over.\n\nEnd of Program.\n");
        end_program = true;
    }

    /**
     * If the hero reaches level 10 and wins the game.
     * It will also set end_program to true preventing any other command to work.
     */
    public static void victory_scene() {
        output_all += ("You Win.\n\nEnd of Program.\n");
        end_program = true;
    }

    /**
     * Based on the hero's current level this method will make a list of possible
     * enemies to fight. When an enemy is chosen it will be randomly selected form
     * this list. The player will have a chance to encounter an enemy from one level
     * below their current level, one level above, and at the same level they
     * currently are. you have a higher chance to encounter an enemy the same level as
     * you, a lower chance to run into an enemy one level below, and the rarest is
     * an enemy one level above. To get this to work a same level enemy will be
     * entered in to the list 4 times, an enemy one below 2 times, and an enemy one
     * above just once. This will create a higher chance of the enemy at the same
     * level getting selected randomly from the list.
     *
     * @return ArrayList String the list of possible enemies
     * @throws FileNotFoundException
     */
    public static ArrayList<String> create_enemy_list() throws FileNotFoundException {
        ArrayList<String> enemy_list = new ArrayList<String>();
        Scanner input = new Scanner(new File("src/main/resources/Enemies.txt"));

        String line = "", name = "", temp = "";
        int level = 0;

        do {
            line = input.nextLine();
            name = line.substring(0, line.indexOf("|"));

            line = line.substring(line.indexOf("|") + 1);
            line = line.substring(line.indexOf("|") + 1);
            temp = line.substring(0, line.indexOf("|"));
            level = Integer.parseInt(temp);

            if (level == hero.get_level()) {
                enemy_list.add(name);
                enemy_list.add(name);
                enemy_list.add(name);
                enemy_list.add(name);
            } else if ((hero.get_level() > 1) && (level == (hero.get_level() - 1))) {
                enemy_list.add(name);
                enemy_list.add(name);
            } else if ((hero.get_level() < 10) && (level == (hero.get_level() + 1))) {
                enemy_list.add(name);
            }
        } while(input.hasNext());

        return enemy_list;
    }


    /**
     * This method basically calls all the necessary methods and makes thing happen.
     * Based on what the user has entered in the input text field on the main screen
     * this method will do different things. The user enters a command and the
     * program dose something in response to that command.
     *
     * @return boolean whether the command was valid/executed
     * @throws FileNotFoundException
     */
    public static boolean command(String command) throws FileNotFoundException {
        //String command = command_line_text_field.getText();
        boolean executed = false;
        char base_command = command.charAt(0);

        //Display command
        if (hero.get_level() == 10 && hero.get_location_at().equals("inn")) {
            executed = true;
            victory_scene();
        } else if (hero.get_HP() <= 0) {
            executed = true;
        } else if (base_command == 'D') {
            if (command.equals("DPS")) {
                Display.player_stats();
                executed = true;
            } else if (command.equals("DI")) {
                Display.inventory();
                executed = true;
            } else if (command.equals("DS")) {
                Display.spells();
                executed = true;
            } else if (command.equals("DSI") && (hero.get_location_at().equals("shop"))) {
                Display.shop_inventory();
                executed = true;
            }

            //End Program
        } else if (base_command == 'E') {
//            System.out.println("Type \"End Program\" exactly to end the program: ");
//            String end = input.nextLine();
//
//            if (end.equals("End Program")) {
//                end_program = true;
//                System.out.println("Program Ended");
//                executed = true;
//            }

            //Rest command
        } else if (base_command == 'R' && (hero.get_location_at().equals("inn"))) {
            Inn.rest();
            executed = true;

            //Go/travel commands
        } else if (base_command == 'G' && !(hero.get_location_at().equals("battle"))) {
            if(command.equals("GI")) { // go to the inn
                Inn.go_to_inn();
                executed = true;
            } else if(command.equals("GS")){
                Shop.go_to_shop();
                executed = true;
            } else if(command.equals("GF") || command.equals("GB")){
                Battle.start_battle();
                executed = true;
            }

            //Attack command
        } else if (base_command == 'A' && (hero.get_location_at().equals("battle"))) {
            Action.physical_attack("hero");
            Battle.hero_turn();
            executed = true;

            //Cast spell command
        } else if (base_command == 'C' && (hero.get_location_at().equals("battle"))) {
            String spellName = command_line_text_field.getText();

            for (Spells spell : spell_list) {
                if (spell.get_spell_name().equals(spellName)) {
                    executed = Action.cast_spell(spell, "hero");
                    Battle.hero_turn();
                }
            }

            //Use an item command
        } else if (base_command == 'U') {
            String itemName = command_line_text_field.getText();

            for (Items item : item_list) {
                if (item.get_name().equals(itemName)) {
                    Action.use_item(item);
                    if (hero.get_location_at().equals("battle")) {
                        Battle.hero_turn();
                    }
                }
            }
            //Buy commands
        } else if (base_command == 'B' && (hero.get_location_at().equals("shop"))) {
            String itemName = command_line_text_field.getText();
            executed = true;

            for (Items item : item_list) {
                if (item.get_name().equals(itemName)) {
                    Shop.buy(item);
                } else {
                    executed = false;
                }
            }

            //Sell commands
        } else if (base_command == 'S' && (hero.get_location_at().equals("shop"))) {
            String itemName = command_line_text_field.getText();
            executed = true;

            for (Items item : item_list) {
                if (item.get_name().equals(itemName)) {
                    Shop.sell(item);
                } else {
                    executed = false;
                }
            }
        }
        return executed;
    }
}
