package com.example.ericwojcik.game;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.ericwojcik.game.GameSchema.GameData_Table;
import com.example.ericwojcik.game.GameSchema.Player_Table;
import com.example.ericwojcik.game.GameSchema.Area_Table;
import com.example.ericwojcik.game.GameSchema.Item_Table;
import com.example.ericwojcik.game.GameSchema.Food_Table;
import com.example.ericwojcik.game.GameSchema.Equipment_Table;

public class DBHelper extends SQLiteOpenHelper{

    Context context;
    private static final String DATABASE_NAME = "Game_Database";
    private static final int DATABASE_V = 1;
    public static SQLiteDatabase db;

    //Table Create Statements
    //GameData table create statement
    private static final String CREATE_TABLE_GAMEDATA = "create table " + GameData_Table.t_name  + "(" + " _id integer primary key autoincrement, " +
    GameData_Table.Cols.ID + ", " + GameData_Table.Cols.GC + ")";

    //Player table create statement
    private static final String CREATE_TABLE_PLAYER = "create table " + Player_Table.t_name  + "(" + " _id integer primary key autoincrement, " +
            Player_Table.Cols.ID + ", " + Player_Table.Cols.COL + ", " + Player_Table.Cols.ROW + ", "
            + Player_Table.Cols.CASH + ", " + Player_Table.Cols.HEALTH + ", " + Player_Table.Cols.EQMASS +  ")";

    //Area table create statement
    private static final String CREATE_TABLE_AREA = "create table " + Area_Table.t_name  + "(" + " _id integer primary key autoincrement, " +
            Area_Table.Cols.ID + ", " + Area_Table.Cols.TOWN + ", " + Area_Table.Cols.DESC + ", "
            + Area_Table.Cols.STARRED + ", " + Area_Table.Cols.EXPLORED + ")";


    //Item table create statement
    private static final String CREATE_TABLE_ITEM = "create table " + Item_Table.t_name  + "(" + " _id integer primary key autoincrement, " +
            Item_Table.Cols.ID + ", " + Item_Table.Cols.DESC + ", " + (Item_Table.Cols.VALUE) + ")";

    //Food table create statement
    private static final String CREATE_TABLE_FOOD = "create table " + Food_Table.t_name  + "(" + " _id integer primary key autoincrement, " +
            Food_Table.Cols.ID + ", " + Food_Table.Cols.HEALTH + ", " + Food_Table.Cols.ITEM + ", " + "foreign key("+Food_Table.Cols.ITEM+") references "
            + Item_Table.t_name + " ("+Item_Table.Cols.ID+")" + ")";

    //Equipment table create statement
    private static final String CREATE_TABLE_EQUIP = "create table " + Equipment_Table.t_name  + "(" + " _id integer primary key autoincrement, " +
            Equipment_Table.Cols.ID + ", " + Equipment_Table.Cols.MASS + ", " + Equipment_Table.Cols.ITEM + ", " + "foreign key("+Equipment_Table.Cols.ITEM+") references "
            + Item_Table.t_name + " ("+Item_Table.Cols.ID+")" + ")";

    public DBHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating required tables
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL(CREATE_TABLE_GAMEDATA);
        db.execSQL(CREATE_TABLE_AREA);
        db.execSQL(CREATE_TABLE_PLAYER);
        db.execSQL(CREATE_TABLE_ITEM);
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_EQUIP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /***
     * GAME DATA
     */

    public boolean CheckIsGameExist(){
        Boolean hasGame = false;
        SQLiteDatabase db = this.getReadableDatabase();
        GDCursor c = new GDCursor(db.query(GameData_Table.t_name,
                null,
                null,
                null,
                null,
                null,
                null));
        try{
            if(c.getCount() > 0)
            hasGame = true;
        }
        finally{
            c.close();
        }
        return hasGame;
    }

    public void insertGameData(GameData gd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GameData_Table.Cols.ID, gd.getGameData_id());
        cv.put(GameData_Table.Cols.GC, gd.getGame_condition());
        db.insert(GameData_Table.t_name, null, cv);
    }

    public void updateGameData(GameData gd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GameData_Table.Cols.ID, gd.getGameData_id());
        cv.put(GameData_Table.Cols.GC, gd.getGame_condition());
        String[] whereValue = {String.valueOf(gd.getGameData_id())};
        db.update(GameData_Table.t_name, cv, GameData_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public void removeGameData(GameData gd){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereValue = {String.valueOf(gd.getGameData_id())};
        db.delete(GameData_Table.t_name, GameData_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public GameData getGameData(){
        SQLiteDatabase db = this.getReadableDatabase();
        GameData gd = new GameData();
        GDCursor c = new GDCursor(db.query(GameData_Table.t_name,
                null,
                null,
                null,
                null,
                null,
                null));
        try{
            c.moveToFirst();
            gd = c.getGameData();
        }
        finally{
            c.close();
        }
        return gd;
    }


    /***
     * PLAYER
     */

    public void insertPlayer(Player p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Player_Table.Cols.ID, p.getPlayer_id());
        cv.put(Player_Table.Cols.COL, p.getColLocation());
        cv.put(Player_Table.Cols.ROW, p.getRowLocation());
        cv.put(Player_Table.Cols.CASH, p.getCash());
        cv.put(Player_Table.Cols.HEALTH, p.getHealth());
        cv.put(Player_Table.Cols.EQMASS, p.getTotalEqMass());
        db.insert(Player_Table.t_name, null, cv);
    }

    public void updatePlayer(Player p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Player_Table.Cols.ID, p.getPlayer_id());
        cv.put(Player_Table.Cols.COL, p.getColLocation());
        cv.put(Player_Table.Cols.ROW, p.getRowLocation());
        cv.put(Player_Table.Cols.CASH, p.getCash());
        cv.put(Player_Table.Cols.HEALTH, p.getHealth());
        cv.put(Player_Table.Cols.EQMASS, p.getTotalEqMass());
        String[] whereValue = {String.valueOf(p.getPlayer_id())};
        db.update(Player_Table.t_name, cv, Player_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public void deletePlayer(Player p){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereValue = {String.valueOf(p.getPlayer_id())};
        db.delete(Player_Table.t_name, Player_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public Player getPlayerObject(){
        SQLiteDatabase db = this.getReadableDatabase();
        Player p = new Player();
        PCursor c = new PCursor(db.query(Player_Table.t_name,
                null,
                null,
                null,
                null,
                null,
                null));
        try{
            c.moveToFirst();
            p = c.getPlayer();

        }
        finally{
            c.close();
        }
        return p;
    }

    /***
     * AREA
     */

    public void insertArea(Area a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Area_Table.Cols.ID, a.getArea_id());
        cv.put(Area_Table.Cols.TOWN, a.getTown());
        cv.put(Area_Table.Cols.DESC, a.getDesc());
        cv.put(Area_Table.Cols.STARRED, a.getStarred());
        cv.put(Area_Table.Cols.EXPLORED, a.getExplored());
        db.insert(Area_Table.t_name, null, cv);
    }

    public void updateArea(Area a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Area_Table.Cols.ID, a.getArea_id());
        cv.put(Area_Table.Cols.TOWN, a.getTown());
        cv.put(Area_Table.Cols.DESC, a.getDesc());
        cv.put(Area_Table.Cols.STARRED, a.getStarred());
        cv.put(Area_Table.Cols.EXPLORED, a.getExplored());
        String[] whereValue = {String.valueOf(a.getArea_id())};
        db.update(Area_Table.t_name, cv, Area_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public void deleteArea(Area a){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereValue = {String.valueOf(a.getArea_id())};
        db.delete(Area_Table.t_name, Area_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public Area[][] getAreaList(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Area> aList = new ArrayList<>();
        Area[][] map = new Area[8][8];
        AreaCursor c = new AreaCursor(db.query(Area_Table.t_name,
                null,
                null,
                null,
                null,
                null,
                null));
        try{
            c.moveToFirst();
            while (!c.isAfterLast()) {
                aList.add(c.getArea());
                c.moveToNext();
            }
        }
        finally{
            c.close();
        }

        int index = 0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                map[i][j] = aList.get(index);
                index++;
            }
        }
        return map;
    }

    /***
     * EQUIPMENT
     */
     public void insertEquip(Equipment e){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put(Equipment_Table.Cols.ID, e.getItem_id());
         cv.put(Item_Table.Cols.ID, e.getItem_id());
         /*could be ItemId that you insert into*/
         cv.put(Equipment_Table.Cols.MASS, e.getMass());
         cv.put(Item_Table.Cols.DESC, e.getDesc());
         cv.put(Item_Table.Cols.VALUE, e.getInValue());
         db.insert(Equipment_Table.t_name, null, cv);
         db.insert(Item_Table.t_name, null, cv);
     }

    public void updateEquip(Equipment e){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Equipment_Table.Cols.ID, e.getItem_id());
        cv.put(Item_Table.Cols.ID, e.getItem_id());
        /*could be ItemId that you insert into*/
        cv.put(Equipment_Table.Cols.MASS, e.getMass());
        cv.put(Item_Table.Cols.DESC, e.getDesc());
        cv.put(Item_Table.Cols.VALUE, e.getInValue());
        String[] whereValue = {String.valueOf(e.getItem_id())};
        db.update(Equipment_Table.t_name, cv, Equipment_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
        db.update(Item_Table.t_name, cv, Item_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public void deleteEquipment(Equipment e){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereValue = {String.valueOf(e.getItem_id())};
        db.delete(Equipment_Table.t_name, Equipment_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
        db.delete(Item_Table.t_name, Item_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public List<Equipment> getAllEquipment(){
         db = this.getReadableDatabase();
            List<Equipment> e_list = new ArrayList<>();
            EquipCursor ec = new EquipCursor(
                    db.query(Equipment_Table.t_name,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null)

            );
            try{
                ec.moveToFirst();
                while (!ec.isAfterLast()){
                    e_list.add(ec.getEquipment());
                    ec.moveToNext();
                }
            }
            finally {
                ec.close();
            }
            return e_list;
    }



    /***
     * FOOD
     */
    public void insertFood(Food f){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Food_Table.Cols.ID, f.getItem_id());
        cv.put(Item_Table.Cols.ID, f.getItem_id());
        cv.put(Item_Table.Cols.DESC, f.getDesc());
        cv.put(Item_Table.Cols.VALUE, f.getInValue());
        cv.put(Food_Table.Cols.HEALTH, f.getHealth());
        db.insert(Food_Table.t_name, null, cv);
        db.insert(Item_Table.t_name, null, cv);
    }

    public void updateFood(Food f){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Food_Table.Cols.ID, f.getItem_id());
        cv.put(Item_Table.Cols.ID, f.getItem_id());
        cv.put(Item_Table.Cols.DESC, f.getDesc());
        cv.put(Item_Table.Cols.VALUE, f.getInValue());
        cv.put(Food_Table.Cols.HEALTH, f.getHealth());
        String[] whereValue = {String.valueOf(f.getItem_id())};
        db.update(Food_Table.t_name, cv, Food_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
        db.update(Item_Table.t_name, cv, Item_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public void deleteFood(Food f){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereValue = {String.valueOf(f.getItem_id())};
        db.delete(Food_Table.t_name, Food_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
        db.delete(Item_Table.t_name, Item_Table.Cols.ID + " = CAST(? as INTEGER)", whereValue);
    }

    public List<Food> getAllFood(){
        db = this.getReadableDatabase();
        List<Food> f_list = new ArrayList<>();
        FoodCursor fc = new FoodCursor(
                db.query(Food_Table.t_name,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)

        );
        try{
            fc.moveToFirst();
            while (!fc.isAfterLast()){
                f_list.add(fc.getFood());
                fc.moveToNext();
            }
        }
        finally {
            fc.close();
        }
        return f_list;
    }


    public class PCursor extends CursorWrapper{
        public PCursor(Cursor c){
            super(c);
        }

        public Player getPlayer(){
            int id = getInt(getColumnIndex(Player_Table.Cols.ID));
            int cols = getInt(getColumnIndex(Player_Table.Cols.COL));
            int rows = getInt(getColumnIndex(Player_Table.Cols.ROW));
            int cash = getInt(getColumnIndex(Player_Table.Cols.CASH));
            double health = getDouble(getColumnIndex(Player_Table.Cols.HEALTH));
            double eqmass = getDouble(getColumnIndex(Player_Table.Cols.EQMASS));
            return new Player(id,rows,cols,cash,health,eqmass);
        }
    }


    public class GDCursor extends CursorWrapper{
        public GDCursor(Cursor c){
            super(c);
        }

        public GameData getGameData(){
            GameData gd = new GameData();
            int id = getInt(getColumnIndex(GameData_Table.Cols.ID));
            int gc = getInt(getColumnIndex(GameData_Table.Cols.GC));
            gd.setGameData_id(id);
            gd.setGame_condition(gc);
            return gd;
        }
    }

    public class AreaCursor extends CursorWrapper{
        public AreaCursor(Cursor c){
            super(c);
        }
        public Area getArea(){
            int id = getInt(getColumnIndex(Area_Table.Cols.ID));
            String desc = getString(getColumnIndex(Area_Table.Cols.DESC));
            Boolean town = getInt(getColumnIndex(Area_Table.Cols.TOWN)) !=0;
            Boolean starred = getInt(getColumnIndex(Area_Table.Cols.STARRED)) !=0;
            Boolean explored = getInt(getColumnIndex(Area_Table.Cols.STARRED)) !=0;
            return new Area(id,town,desc,starred,explored);
        }
    }


    public class EquipCursor extends CursorWrapper{
        public EquipCursor(Cursor c){
            super(c);
        }

        public Equipment getEquipment(){
            int id = getInt(getColumnIndex(Equipment_Table.Cols.ID));
            String desc = getString(getColumnIndex(Item_Table.Cols.DESC));
            double mass = getDouble(getColumnIndex(Equipment_Table.Cols.MASS));
            int value = getInt(getColumnIndex(Item_Table.Cols.VALUE));
            return new Equipment(id,value,desc,mass);
        }
    }

    public class FoodCursor extends CursorWrapper{
        public FoodCursor(Cursor c){
            super(c);
        }

        public Food getFood(){
            int id = getInt(getColumnIndex(Food_Table.Cols.ID));
            String desc = getString(getColumnIndex(Item_Table.Cols.DESC));
            double health = getDouble(getColumnIndex(Food_Table.Cols.HEALTH));
            int value = getInt(getColumnIndex(Item_Table.Cols.VALUE));
            return new Food(id,value,desc,health);
        }
    }

}
