package com.example.ericwojcik.game;

/***
 * Represents equipment, subclass of Item
 */

public class Equipment extends Item{
    private double mass;


    public Equipment(){
        super();
        mass = 0.0;
    }

    /***
     * Create a new equipment object
     * @param item_id identifier for individual equipment item
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     * @param inMass how much this particular piece of equipment weighs
     */
    public Equipment(int item_id, int inValue, String inDesc, double inMass){
        super(item_id, inValue,inDesc);
        mass = inMass;
    }

    /***
     * Creates a new Equipment object, without specifying id
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     * @param inMass how much equipment weighs
     */
    public Equipment(int inValue, String inDesc, double inMass){
        super(inValue,inDesc);
        mass = inMass;
    }

    /***
     * Copy constructor
     * @param inEquip equipment
     */
    public Equipment(Equipment inEquip){
        mass = inEquip.getMass();
    }

    public double getMass(){
        return mass;
    }

    /***
     * Generic method used to set a variable either specific to Equipment or Food i.e. mass/health
     * @return mass as it is specific to Equipment
     */
    @Override
    public double getVar(){
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public boolean equals(Object inObject){
        boolean isEqual = false;
        if (inObject instanceof Equipment){
            Equipment inEquipment = (Equipment)inObject;
            if(super.equals(inEquipment)){
                if(Math.abs(mass - inEquipment.getMass())<0.0001){
                    isEqual = true;
                }
            }
        }
        return isEqual;
    }

    public String toString(){
        return super.toString() + "MASS:= " + mass;
    }






}

