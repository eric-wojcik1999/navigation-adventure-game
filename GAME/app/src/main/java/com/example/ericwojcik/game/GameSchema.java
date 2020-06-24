package com.example.ericwojcik.game;

public class GameSchema {

    public static class GameData_Table{
        public static final String t_name = "GameData_Table";
        public static class Cols{
            public static final String ID = "game_id";
            public static final String GC = "game_condition_id";
        }
    }

    public static class Player_Table{
        public static final String t_name = "Player_Table";
        public static class Cols{
            public static final String ID = "player_id";
            public static final String COL = "col_id";
            public static final String ROW = "row_id";
            public static final String CASH = "cash_id";
            public static final String HEALTH = "health_id";
            public static final String EQMASS = "equipment_mass_id";
        }
    }

    public static class Area_Table{
        public static final String t_name = "Area_Table";
        public static class Cols{
            public static final String ID = "area_id";
            public static final String TOWN = "town_id";
            public static final String DESC = "description_id";
            public static final String STARRED = "starred_id";
            public static final String EXPLORED = "explored_id";
        }
    }

    public static class Item_Table{
        public static final String t_name = "Item_Table";
        public static class Cols{
            public static final String ID = "item_id";
            public static final String DESC = "description_id";
            public static final String VALUE = "value_id";
        }
    }

    public static class Food_Table{
        public static final String t_name = "Food_Table";
        public static class Cols{
            public static final String ID = "food_id";
            public static final String HEALTH = "health_id";
            public static final String ITEM = "item_id";
        }
    }

    public static class Equipment_Table{
        public static final String t_name = "Equip_table";
        public static class Cols{
            public static final String ID = "equip_id";
            public static final String MASS = "mass_id";
            public static final String ITEM = "item_id";
        }
    }

}
