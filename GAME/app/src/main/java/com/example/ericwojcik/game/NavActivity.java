package com.example.ericwojcik.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import java.util.*;


public class NavActivity extends AppCompatActivity {


    //String Arrays//
    private String[] str_equipList;
    private String[] str_specialList;
    private String[] str_foodList;

    //Array Lengths//
    private int equipListSize;
    private int specialListSize;
    private int foodListSize;

    private GameData theGame;
    private Area[][] map;
    private Player player;
    private StatusBar statBar;
    private AreaInfo areaInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        //Initialising Fragments//
        FragmentManager fm = getSupportFragmentManager();
        statBar = (StatusBar) fm.findFragmentById(R.id.fragment_place);
        areaInfo = (AreaInfo) fm.findFragmentById(R.id.fragment_place2);
        if(statBar == null){
            statBar = new StatusBar();
            fm.beginTransaction().add(R.id.fragment_place,statBar).commit();
        }
        if(areaInfo == null){
            areaInfo = new AreaInfo();
            fm.beginTransaction().add(R.id.fragment_place2,areaInfo).commit();
        }


        //Widgets//
        Button b_overview, b_option, b_up, b_down, b_left, b_right;

        //Constants for Equipment names//
        String e_dagger = "Dagger", e_dagger2 = "Old Dagger", e_dagger3 = "Rubber Dagger",
                e_dagger4 = "Pathetic Dagger", e_pencil = "AB Pencil",
                e_trink = "Trinket of the Gods", e_ajax = "Ajax Spray and Wipe",
                e_flex = "Flex Tape", e_bronze = "Bronze Trumpet", e_silver = "Silver Trumpet",
                e_gold = "Gold Trumpet", e_titanium = "Titanium Trumpet",
                e_deceased = "Deceased Trumpet", e_vbucks = "V-bucks", e_oball = "Oracle Ball",
                e_mRuby = "Mystery Ruby", e_mJade = "Mystery Jade", e_mRock = "Mystery Rock",
                e_tseries = "T-series", e_wide = "Wide Sword", e_extra = "Extra Wide Sword",
                e_rmap = "Road Map", e_jmonkey = "Jade Monkey", e_iscraper = "Ice Scraper";

        //Constants for Special Names//
        String s_improb = "Improbability Device", s_ben = "Ben Kenobi", s_smell = "Smell O Vision";

        //Constants for Food//
        String f_chicken = "Chicken Spine", f_berry = "Wild Berry", f_rberry = "Rotten Berry",
                f_foot = "Foot Fungi", f_gsnail = "Giant Snail", f_horse = "Horse",
                f_season = "Seasoned Elephant", f_chad = "Chad's Thigh", f_human = "Human",
                f_shuman = "Subhuman", f_prehuman = "Prehuman", f_ehuman = "Evolved Human aka Hipsters",
                f_bomb = "Bomb Curry", f_milk = "Old milk", f_indig = "Indigestion";

        //Initialising String Arrays//
        str_equipList = new String[] { e_dagger, e_dagger2, e_dagger3, e_dagger4, e_pencil, e_trink,
                e_ajax, e_flex, e_bronze, e_silver, e_gold, e_titanium, e_deceased, e_vbucks,
                e_oball, e_mRuby, e_mJade, e_mRock, e_tseries, e_wide, e_extra, e_rmap, e_jmonkey,
                e_iscraper };
        str_specialList = new String [] { s_improb, s_ben, s_smell };
        str_foodList = new String [] { f_chicken, f_berry, f_rberry, f_foot, f_gsnail, f_horse,
                f_season, f_chad, f_human, f_shuman, f_prehuman, f_ehuman, f_bomb, f_milk, f_indig };

        //Initialising Array Lengths//
        equipListSize = str_equipList.length;
        specialListSize = str_specialList.length;
        foodListSize = str_foodList.length;

        //Initialising Game World//
        theGame = new GameData();
        player = new Player();
        player.initDBHelper(this);
        theGame.initDBHelper(this);
        /*Database loading disabled*/
        if(true){
            map = new Area[8][8];
            randomiseGameWorld();
            theGame.setMap(map);
            player.setHealth(100.0);
            player.setCash(400);
            player.setTotalEqMass(1.0);
            theGame.setGame_condition(1);
            theGame.setGameData(theGame);
        }
        else{
            theGame.loadMap();
            player = theGame.loadPlayer();
            player.loadAll();
        }
        theGame.setPlayer(player);
        theGame.makeInstance(theGame);


        //Initialising widgets//
        b_overview = (Button) findViewById(R.id.overview_button_id);
        b_option = (Button) findViewById(R.id.option_button_id);
        b_up = (Button) findViewById(R.id.up_button_id);
        b_down = (Button) findViewById(R.id.down_button_id);
        b_left = (Button) findViewById(R.id.left_button_id);
        b_right = (Button) findViewById(R.id.right_button_id);

        //Button Overview//
        b_overview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent iIntent = new Intent(NavActivity.this, OverviewActivity.class);
                startActivity(iIntent);
            }
        });

        //Button Option//
        b_option.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(theGame.getMap()[player.getColLocation()][player.getRowLocation()].getTown()){
                    Intent iIntent = new Intent(NavActivity.this, TownActivity.class);
                    startActivity(iIntent);
                }
                else{
                    Intent iIntent = new Intent(NavActivity.this, WildActivity.class);
                    startActivity(iIntent);
                }
            }
        });

        //Button Up//
        b_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int location = player.getColLocation();
                if(location-1>=0){
                    player.setColLocation(location-1);
                    buttonModifyValues();
                }
            }
        });

        //Button Down//
        b_down.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int location = player.getColLocation();
                if(location+1<8){
                    player.setColLocation(location+1);
                    buttonModifyValues();
                }
            }
        });

        //Button Left//
        b_left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int location = player.getRowLocation();
                if(location-1>=0){
                    player.setRowLocation(location-1);
                    buttonModifyValues();
                }
            }
        });

        //Button Right//
        b_right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int location = player.getRowLocation();
                if(location+1<8){
                    player.setRowLocation(location+1);
                    buttonModifyValues();
                }
            }
        });
    }

    public void buttonModifyValues(){
        int col = player.getColLocation();
        int row = player.getRowLocation();
        theGame.returnArea(col,row).setExplored(true);
        player.setHealth(Math.max(0.0, player.getHealth() - 5.0 - (player.getTotalEqMass()/15.0)));
        if(player.getHealth()==0){
            reset();
            Toast.makeText(NavActivity.this, "GAME OVER",
                    Toast.LENGTH_SHORT).show();
        }
        else if(player.checkWin()){
            reset();
            Toast.makeText(NavActivity.this, "YOU WON THE GAME!!!",
                    Toast.LENGTH_SHORT).show();

        }
        statBar.updateText("health", player.getHealth());
        statBar.updateText("cash", player.getCash());
        statBar.updateText("mass", player.getTotalEqMass());
        areaInfo.updateLocation(col,row);
        areaInfo.updateDesc(col,row);
        areaInfo.updateToggle();
    }

    private void reset(){
        randomiseGameWorld();
        player.setHealth(100.0);
        player.setCash(400);
        player.removeAll();
        player.setRowLocation(4);
        player.setColLocation(4);
    }




    /*------------------------------------RANDOMISATION METHODS------------------------------------*/

    /***
     * This method is responsible for randomising game world elements such as determining what portion
     * of the map is covered in wilderness. It also calls a method for generating a random list of
     * items to place in a given map.
     */
    public void randomiseGameWorld(){
        Random randomiser = new Random();
        int numWilderness = 10+randomiser.nextInt(40); //There cannot be more than roughly 60% wilderness//
        int row = 0, col = 0, index =0;


        for (int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                Area aTemp = new Area();
                aTemp.initDBHelper(this);
                aTemp = randomiseItemInterface(aTemp);
                map[i][j] = aTemp;
                index++;
            }
        }

        //Randomise wilderness coverage of map//
        for (int i=0; i<numWilderness; i++)
        {
            row = randomiser.nextInt(8);
            col = randomiser.nextInt(8);
            map[row][col].setTown(false);
        }
    }

    /***
     * Interface used to interact with randomise item method. Iterates through names of the items,
     * uses the randomiser method to add these items to the list, then scrambles the list so the items
     * appear in a random order.
     */
    public Area randomiseItemInterface(Area a){
        List<Item> listItem = new ArrayList<>();
        for (int i = 0; i < equipListSize; i++)
        {
            randomiseItem(str_equipList[i], "e", listItem);
        }

        for (int i = 0; i < specialListSize; i++)
        {
            randomiseItem(str_specialList[i], "s", listItem);
        }

        for (int i = 0; i < foodListSize; i++)
        {
            randomiseItem(str_foodList[i], "f", listItem);
        }

        Collections.shuffle(listItem);
        a.setListItem(listItem);
        return a;
    }

    /***
     * Method responsible for generating a random quantity of any item. However many items are then
     * instantiated and added to the array list.
     * @param description this is the name of the item passed in e.g. dagger
     * @param itemType this is the type of item i.e. equipment, special
     * @param listItem this is the ArrayList used to store the instantiated items
     */
    public void randomiseItem(String description, String itemType, List<Item> listItem)
    {
        Random randomiser = new Random();

        int quantity = 0;
        int value = 0;
        double varDouble = 0.0;

        quantity = randomiser.nextInt(3);
        for (int i =0; i<quantity; i++)
        {
            value = 1+randomiser.nextInt(1000);
            switch (itemType) {
                case "e": //If item is type equipment//
                    varDouble = 1.0 + randomiser.nextInt(250);
                    if (description.equals("Road Map") || description.equals("Jade Monkey")
                            || description.equals("Ice Scraper")) {
                        value = 2130080;
                    }
                    Equipment equip = new Equipment(value, description, varDouble);
                    listItem.add(equip);
                    break;
                case "f": //If item is type food//
                    varDouble = 1.0 + randomiser.nextInt(100);
                    Food food = new Food(value, description, varDouble);
                    listItem.add(food);
                    break;
                case "s": //If item is type special//
                    switch (description) {
                        case "Ben Kenobi":
                            BenKenobi ben = new BenKenobi(value, description, 0.0);
                            listItem.add(ben);
                            break;
                        case "Improbability Device":
                            ImprobDrive imp = new ImprobDrive(value, description, -3.14);
                            listItem.add(imp);

                            break;
                        case "Smell O Vision":
                            SmellScope scope = new SmellScope(value, description, 5.0);
                            listItem.add(scope);
                            break;
                    }
                    break;
            }
        }

    }

}
