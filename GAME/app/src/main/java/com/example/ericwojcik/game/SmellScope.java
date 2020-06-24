package com.example.ericwojcik.game;

import java.util.ArrayList;
import java.util.List;

/***
 * Smell-o-scope functionality
 */

public class SmellScope extends Equipment {

    private List<Item> itemList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();

    public SmellScope(){
        super();
    }

    /***
     * Creates a new Smell o Scope by specifying id
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     * @param inMass how much Smell o Scope weighs
     */
    public SmellScope(int item_id, int inValue, String inDesc, double inMass){
        super(item_id, inValue,inDesc,inMass);
    }

    /***
     * Creates a new Smell o Scope, without specifying id
     * @param inValue worth of item, aka how to buy for
     * @param inDesc description of item
     * @param inMass how much Smell o Scope weighs
     */
    public SmellScope(int inValue, String inDesc, double inMass){
        super(inValue,inDesc,inMass);
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<String> getStringList(){
        return stringList;
    }

    public void useScope(Area[][] map, int col, int row){
      List<Item> listA = new ArrayList<>();
      List<Item> listB = new ArrayList<>();
      List<Item> listC = new ArrayList<>();
      List<Item> listD = new ArrayList<>();
        for(int i=0; i<=2; i++){
          for(int j=0; j<=2; j++){
              if(col+i<8 && row+j<8 && col-i>=0 && row-j>=0){
                  listA = new ArrayList<>();
                  listA = map[col+i][row+j].getListItem();
                  for(int k =0; k<listA.size();k++){
                      stringList.add(funcStringMake(col,row,col+i,row+j));
                  }
                  if(j!=0){
                      listB = new ArrayList<>();
                      listB = map[col+i][row-j].getListItem();
                      for(int k =0; k<listB.size();k++){
                          stringList.add(funcStringMake(col,row,col+i,row-j));
                      }
                      if(i!=0){
                          listC = new ArrayList<>();
                          listC = map[col-i][row+j].getListItem();
                          for(int k =0; k<listC.size();k++){
                              stringList.add(funcStringMake(col,row,col-i,row+j));
                          }
                          listD = new ArrayList<>();
                          listD = map[col-i][row-j].getListItem();
                          for(int k =0; k<listD.size();k++){
                              stringList.add(funcStringMake(col,row,col-i,row-j));
                          }
                      }
                  }
                  itemList.addAll(listA);
                  itemList.addAll(listB);
                  itemList.addAll(listC);
                  itemList.addAll(listD);
              }
          }
      }
    }


    private String funcStringMake(int col, int row, int int1, int int2)
    {
       String str1 = "",str2= "",finalStr="";
       if(col == int1){
           str1 = "CURRENT: null";
       }
       else if(col > int1){
           int diff = col - int1;
           str1 = "DOWN: "+ diff;
       }
       else if(col < int1){
           int diff = int1 - col;
           str1 = "UP: "+ diff ;
       }

       if(row == int2){
           str2 = "CURRENT: null";
       }
       else if(row > int2){
           int diff = row - int2;
           str2 = "RIGHT: "+ diff;
       }
       else if(row < int2){
           int diff = int2 - row;
           str2 = "LEFT: "+ diff;
       }
       finalStr = str1 + ", " + str2;
       return finalStr;
    }
}
