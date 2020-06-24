package com.example.ericwojcik.game;

import android.content.Context;

import java.io.Serializable;

/***
 * This represents the entire game grid, which is an 8x8 space [64 total grid spaces]
 */

public class GameData implements Serializable {
    private int gameData_id;
    private Area [][] map;
    private Player thePlayer;
    private static GameData game;
    private int game_condition;
    private DBHelper dbHelper;


    public GameData(){
        thePlayer = new Player();
        map = new Area[8][8];
        game_condition = 0;
        gameData_id = 0;
    }

    public void initDBHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    public void loadMap(){
        this.map = dbHelper.getAreaList();
        for(int i=0; i<8; i++){
            for(int j =0; j<8; j++){
                this.map[i][j].loadItemList();
            }
        }
    }

    public Player loadPlayer(){
        return dbHelper.getPlayerObject();
    }

    /***
     * This is used to determine if game already exists. If does,
     * will cause randomisation methods to be skipped. If returns true,
     * then a game already exists
     * @return boolean determining if game already in db
     */
    public Boolean checkGameExists(){
        return dbHelper.CheckIsGameExist();
    }


   /*was static before so if anything breaks, its cos i made it not static*/
    public void makeInstance(GameData g){
        game = new GameData();
        game = g;
        dbHelper.updateGameData(g);
        dbHelper.updatePlayer(this.thePlayer);
    }


    public GameData getInstance(){
        return game;
    }

    public int getGame_condition() {
        return game_condition;
    }

    public int getGameData_id() {
        return gameData_id;
    }

    public GameData(Area[][] inMap){
        map = inMap;
    }

    public Area[][] getMap() {
        return map;
    }

    public Player getPlayer() {
        return thePlayer;
    }

    public void setGame_condition(int game_condition) {
        this.game_condition = game_condition;
    }

    public void setGameData_id(int gameData_id) {
        this.gameData_id = gameData_id;
    }

    public void setGameData(GameData g){
        dbHelper.insertGameData(g);
    }

    public void setMap(Area[][] map) {
        this.map = map;
        for(int i=0;i<8;i++){
            for(int j=0; j<8; j++){
                dbHelper.insertArea(this.map[i][j]);
            }
        }
    }

    public void setPlayer(Player p){
        this.thePlayer = p;
        dbHelper.insertPlayer(p);
    }

    public void insertArea(Area a, int x, int y){
        map[x][y]= a;
    }

    /***
     * Will return an area based on players current coordinates
     * @param x coordinate
     * @param y coordinate
     * @return specific area object at those coordinates
     */
    public Area returnArea(int x, int y){
        return map[x][y];
    }
}

