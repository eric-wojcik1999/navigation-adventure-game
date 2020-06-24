package com.example.ericwojcik.game;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Area implements Serializable {
    private int area_id;
    private boolean town;
    private String desc;
    private boolean starred;
    private boolean explored;
    private List<Item> listItem = new ArrayList<>();
    private int itemCnt;
    private static int next_id;
    private DBHelper dbHelper;

    public Area(){
        area_id = 0;
        next_id = 0;
        town = true;
        desc = "";
        starred = false;
        explored = false;
        itemCnt = 0;
    }

    public void initDBHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    public void loadItemList(){
        List<Equipment> temp = dbHelper.getAllEquipment();
        List<Food> temp1 = dbHelper.getAllFood();
        List<Item> temp2 = new ArrayList<>();
        for(int i=0; i<temp.size(); i++){
            temp2.add((Item)temp.get(i));
        }
        for(int i=0; i<temp1.size(); i++){
            temp2.add((Item)temp1.get(i));
        }
        listItem = temp2;
    }


    /***
     * Create a new Area with specified id
     * @param area_id identifier for individual item
     * @param inTown whether town or wilderness
     * @param inDesc user description of location
     * @param inStarred favourite status that user invokes
     * @param inExplored whether a user has been to location or not
     */
    public Area(int area_id, boolean inTown, String inDesc, boolean inStarred, boolean inExplored){
        this.area_id = area_id;
        this.town = inTown;
        this.desc = inDesc;
        this.starred = inStarred;
        this.explored = inExplored;
        next_id = area_id + 1;
    }

    public Area(boolean inTown, String inDesc, boolean inStarred, boolean inExplored){
        this.town = inTown;
        this.desc = inDesc;
        this.starred = inStarred;
        this.explored = inExplored;
        next_id++;
    }

    public int addItem(Item itm){
        listItem.add(itm);
        return listItem.size()-1;
    }

    public void removeItem(Item itm){
        listItem.remove(itm);
    }

    public void update(){
    }

    public int returnItemCnt(){
        itemCnt = listItem.size();
        return itemCnt;
    }

    public int getArea_id(){
        return area_id;
    }

    public boolean getTown() {
        return town;
    }

    public String getDesc() {
        return desc;
    }

    public boolean getExplored() {
        return explored;
    }

    public boolean getStarred() {
        return starred;
    }

    public Item getItem(int i){
        return listItem.get(i);
    }

    public List<Item> getListItem()
    {
        return listItem;
    }

    public void setArea_id(int id){
        this.area_id = id;
    }

    public void setTown(boolean town) {
        this.town = town;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setStarred(boolean starred){
        this.starred = starred;
    }

    public void setExplored(boolean explored){
        this.explored = explored;
    }

    public void setList(List<Item> listItem){
        this.listItem = listItem;
    }

    public void setListItem(List<Item> listItem)
    {
        this.listItem = listItem;
        for(int i =0; i<this.listItem.size(); i++){
            if(this.listItem.get(i) instanceof Equipment){
                dbHelper.insertEquip((Equipment)this.listItem.get(i));
            }
            else if(this.listItem.get(i) instanceof Food){
                dbHelper.insertFood((Food)this.listItem.get(i));
            }
        }
    }


}
