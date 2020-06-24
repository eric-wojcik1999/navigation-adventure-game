package com.example.ericwojcik.game;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import android.content.Context;


/****
 * Represents the player
 */

public class Player implements Serializable{

    private static final double MAX_HEALTH = 100;
    private int player_id;
    private int rowLocation;
    private int colLocation;
    private int cash;
    private double health;
    private double totalEqMass;
    private int eqCount;
    private int smellCount;
    private DBHelper dbHelper;

    private List<Equipment> inventory = new ArrayList<>();


    public Player(){
        player_id = 0;
        rowLocation = 0;
        colLocation = 0;
        cash = 0;
        health = 0.0;
        totalEqMass = 0.0;
        eqCount = 0;
    }

    public void initDBHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    /***
     * Creates a new player
     * @param inRow row location for player
     * @param inCol column location for player
     * @param inCash how much cash a play has
     * @param inHealth hpw much health a player has
     * @param inTotalEqMass how much a player weighs
     */
    public Player(int inPlayer_id, int inRow, int inCol, int inCash, double inHealth, double inTotalEqMass){
        this.player_id = inPlayer_id;
        this.rowLocation = inRow;
        this.colLocation = inCol;
        this.cash = inCash;
        this.health = inHealth;
        this.totalEqMass = inTotalEqMass;
    }

    public void loadAll(){
        this.inventory = dbHelper.getAllEquipment();
    }


    /***
     * Adds an equipment object into the player's inventory listArray
     * @param newE is the new equipment object to add
     * @return returns the size/index of array at insertion point
     */
    public int add(Equipment newE){
        inventory.add(newE);
        dbHelper.insertEquip(newE);
        return inventory.size()-1; /*Return insertion point*/
    }

    public void update(Equipment upEquip){
        dbHelper.updateEquip(upEquip);
    }

    /***
     * Allows you to grab a certain piece of equipment in the player's inventory based on a given
     * index
     * @param i is the index of the equipment item you wish to return
     * @return  the equipment object itself
     */
    public Equipment get(int i){
        return inventory.get(i);
    }

    public void remove(Equipment rmEquip){
        dbHelper.deleteEquipment(rmEquip);
        inventory.remove(rmEquip);
    }

    public void removeAll(){
        inventory.clear();
    }


    /***
     * A function that iterates through the players inventory and calculates the total weight
     */
    private void calcMass(){
        double tempTotalMass=0.0;
        for(int i = 0; i<inventory.size(); i++){
            tempTotalMass += inventory.get(i).getMass();
        }
        setTotalEqMass(tempTotalMass);
    }

    public SmellScope findSmellScope(){
        SmellScope ss = new SmellScope();
        Equipment e = new Equipment();
        for(int i=0; i<inventory.size(); i++){
            e = inventory.get(i);
            if (e instanceof SmellScope){
                ss = (SmellScope)e;
            }
        }
        smellCount++;
        return ss;
    }

    public boolean checkWin(){
        boolean winState = false;
        int jadeCount = 0;
        int iceCount = 0;
        int roadCount = 0;
        for(int i=0; i<inventory.size(); i++){
            if(inventory.get(i).getDesc().equals("Jade Monkey")){
                jadeCount++;
            }
            else if(inventory.get(i).getDesc().equals("Ice Scraper")){
                iceCount++;
            }
            else if(inventory.get(i).getDesc().equals("Road Map")){
                roadCount++;
            }
        }
        if(jadeCount>0 && roadCount>0 && iceCount>0){
            winState = true;
        }
        return winState;
    }

    public int getSmellCount(){
        return smellCount;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }

    public int getCash() {
        return cash;
    }

    public double getHealth() {
        return health;
    }

    public double getTotalEqMass() {
        calcMass();
        return totalEqMass;
    }

    public int getEqCount(){
        eqCount = inventory.size();
        return eqCount;
    }

    public int getSize(){
        return inventory.size();
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public void setRowLocation(int rowLocation) {
        this.rowLocation = rowLocation;
    }

    public void setColLocation(int colLocation) {
        this.colLocation = colLocation;
    }

    public void setCash(int inCash) {
        this.cash = inCash;
        if(this.cash < 0){
            this.cash = 0;
        }
    }

    public void setHealth(double inHealth) {
        this.health = inHealth;
        if(this.health > MAX_HEALTH){
            this.health = MAX_HEALTH;
        }
        if(this.health < 0){
            this.health = 0;
        }
    }


    public void setTotalEqMass(double totalEqMass) {
        this.totalEqMass = totalEqMass;
    }

}

