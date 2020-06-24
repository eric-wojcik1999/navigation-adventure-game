package com.example.ericwojcik.game;

import java.io.Serializable;

/***
 * Represents an item
 */

public abstract class Item implements Serializable {

    private int item_id;
    private int value1;
    private String desc;
    private static int next_id;


    public Item(){
        item_id = 0;
        next_id = 0;
        value1 = 0;
        desc = "None";
    }


    /***
     * Creates a new item, with a specified id
     * @param item_id identifier for individual item
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     */
    public Item(int item_id, int inValue, String inDesc) {
        this.item_id = item_id;
        this.value1 = inValue;
        this.desc = inDesc;
        next_id = item_id + 1;
    }

    /***
     * Creates a new item, without specifying id
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     */
    public Item(int inValue, String inDesc){
        this.value1 = inValue;
        this.desc = inDesc;
        next_id++;
    }


    public int getItem_id(){
        return item_id;
    }


    public int getInValue() {
        return value1;
    }

    public String getDesc(){
        return desc;
    }

    /***
     * Generic method used to set a variable either specific to Equipment or Food i.e. mass/health
     * @return
     */
    public abstract double getVar();

    public void setItem_id(int inItem_id){
        this.item_id = inItem_id;
    }


    public void setInValue(int inValue){
        if (validateInt(inValue)){
            this.value1 = value1;
        }
        else{
            throw new IllegalArgumentException("Invalid import values");
        }
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean equals(Object inObject){
        boolean isEqual = false;
        if (inObject instanceof Item){
            Item inItem = (Item)inObject;
            if(value1 == inItem.getInValue()){
                if(desc.equals(inItem.getDesc())){
                    isEqual = true;
                }
            }
        }
        return isEqual;
    }

    public String toString(){
        String output = "Item: " + desc + ", Value:=  " +value1 +", ";
        return output;
    }

    //PRIVATE SUBMODULES
    private boolean validateInt(int inInt){
        return(inInt > 0);
    }
}
