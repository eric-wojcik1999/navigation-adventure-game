package com.example.ericwojcik.game;

/***
 * Represents food, subclass of Item
 */

public class Food extends Item{
    private double health;

    public Food(){
        super();
        health = 0.0;
    }

    /***
     * Create a new food object, specifically specifying id
     * @param item_id identifier for individual food item
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     * @param inHealth how much health this item provides
     */
    public Food(int item_id, int inValue, String inDesc, double inHealth){
        super(item_id, inValue, inDesc);
        health = inHealth;
    }

    /***
     * Create a new food object without specifying id
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     * @param inHealth how much health this item provides
     */
    public Food(int inValue, String inDesc, double inHealth){
        super(inValue, inDesc);
        health = inHealth;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    /***
     * Generic method used to set a variable either specific to Equipment or Food i.e. mass/health
     * @return health as it is specific to Food
     */
    @Override
    public double getVar(){
        return health;
    }

    public double getHealth() {
        return health;
    }

    public boolean equals(Object inObject){
        boolean isEqual = false;
        if (inObject instanceof Food){
            Food inFood = (Food)inObject;
            if(super.equals(inFood)){
                if(Math.abs(health - inFood.getHealth())<0.0001){
                    isEqual = true;
                }
            }
        }
        return isEqual;
    }


    public String toString(){
        String output = super.toString() + "HP:= " + health;
        return output;
    }
}
