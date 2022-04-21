# DQ-lite-2 UML
## Bradshaw Roberts

```mermaid
classDiagram
    GUI <|--Action
    GUI <|--Battle
    GUI <|--Display
    GUI <|--Inn
    GUI <|--Shop
    Entity <|--Hero
    Entity <|--Enemy
    class GUI {
        +boolean end_program
        +boolean intro_name_finished
        +boolean intro_magic_finished
        +String output_all
        +int amount
        +Hero hero
        +String hero_name
        +String hero_magic
        +Enemy enemy
        +Spells spell
        +Items item
        +ArrayList<Items> shop_inventory_items
        +ArrayList<Spells> shop_inventory_spells
        +ArrayList<Items> item_list
        +ArrayList<Spells> spell_list
        
        +start(Stage applicationStage)
        +command() boolean
        +command_action()
        +update_screen()
        +intro_scene_name() boolean
        +intro_scene_magic() boolean
        +death_scene()
        +victory_scene()
        +create_enemy_list() ArrayList<String>
    }
    class Action {
        +cast_spell(Spells spell, String who_cast_spell) boolean
        +recovery_spell_hero(Spells spell) boolean
        +recovery_spell_enemy(Spells spell) boolean
        +attack_spell_hero(Spells spell) boolean
        +attack_spell_enemy(Spells spell) boolena
        +physical_attack(String who)
        use_item(Items item) boolean
    }
    class Battle {
        +start_battle()
        +end_battle()
        +set_next_enemy()
        +level_up()
        +hero_turn()
        +enemy_turn()
    }
    class Display {
        +shop_inventory()
        +player_stats()
        +inventory()
        +spells()
    }
    class Inn {
        +go_to_inn()
        +rest()
    }
    class Shop {
        +go_to_shop()
        +create_shop_inventory()
        +buy(Items item)
        +buy_spell(Spells spell)
        +sell(Items item)
    }
    class Entity {
        -String name
        -String magic_type
        -int level
        -int HP
        -int MP
        -int maxHP
        -int maxMP
        -int strength
        -int critical
        -int resilience
        -ArrayList<String> spells
        
        +check_if_alive() boolean
    }
    class Hero {
        -String weapon_equipped
        -String armor_equipped
        -String location_at
        -int current_experience
        -int gold
        -int weapon_attack
        -int armor_defence
        
        -ArrayList<String> item_inventory
        -ArrayList<String> item_amount
        
        +hero_attack() int
        +hero_attack_spell(String, int) boolean
        +hero_recovery_spell(String, int) boolean
        +hero_defence(int, String) int
        +add_spoils(int, int)
        +level_up() boolean
        +get_number_of_item(String) int
        +use_a_health_item(Items, int)
        +use_a_mana_item(Items, int) 
        +add_consumable(Items, int)
        +remove_consumable(Items, int)
        +add_spell(String name)
        +check_if_have_spell(String name)
        +edit_gold(int)
        +get_sells()
        +set_weapon(String, int)
        +set_armor(String, int)
        +set_location_at(String)
    }
    class Enemy {
        -int agility
        -int victory_experience
        -int victory_gold
        
        +add_to_spells(String spell_list)
        +enemy_attack() int
        +enemy_attack_spell(String, int) boolean
        +enemy_recovery_spell(String, int) boolean
        +enemy_defence(int, String) int
        +get_number_of_spells() int
        +get_spell_at(int) String
        +check_if_have_recovery_spell() String
    }
    class Spells {
        -String spell_name
        -String spell_type
        -String spell_effect
        -int spell_strength
        -int spell_critical
        -int MP_cost
        -int level_avaliable
        -int gold_cost
    }
    class Items {
        -String name
        -String effect
        -String cost
        -int strength
        -int level
        -int cost
        
        +get_selling_price() int
    }
     