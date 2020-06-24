package com.example.ericwojcik.game;

import java.util.List;

/***
 * These are not the droids you are looking for
 */

public class BenKenobi extends Equipment {

    public BenKenobi(){
        super();
    }

    /***
     * Create a new Ben Kenobi object by specifying id
     * @param item_id identifier for individual food item
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     * @param inMass how much Kenobi weighs
     */
    public BenKenobi(int item_id, int inValue, String inDesc, double inMass){
        super(item_id, inValue,inDesc,inMass);
    }

    /***
     * Creates a new Ben Kenobi object, without specifying id
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     * @param inMass how much Kenobi weighs
     */
    public BenKenobi(int inValue, String inDesc, double inMass){
        super(inValue,inDesc,inMass);
    }

    public Player useKenobi(List<Item> listItem, Player player){
        for(int i=0 ;i<listItem.size(); i++){
            if(listItem.get(i) instanceof Food){
                player.setHealth(((Food) listItem.get(i)).getHealth());
            }
            else if(listItem.get(i) instanceof Equipment){
                player.add((Equipment)listItem.get(i));
            }
        }
        return player;
    }
}
