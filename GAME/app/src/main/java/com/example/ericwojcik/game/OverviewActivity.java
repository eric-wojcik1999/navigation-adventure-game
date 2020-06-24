package com.example.ericwojcik.game;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;

public class OverviewActivity extends AppCompatActivity {

    private AreaInfo areaInfo;
    private GridAdapter adapter;
    private GameData theGame;
    private Player player;
    private Area[] tempList;
    int[] default_images;
    int image_1 = R.drawable.ic_tree1;
    int image_2 = R.drawable.ic_building1;
    int image_3 = R.drawable.vector_player;
    int image_4 = R.drawable.vector_star;
    int image_5 = R.drawable.vector_black;
    Button b_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //Initialising Fragments//
        FragmentManager fm = getSupportFragmentManager();
        areaInfo = (AreaInfo) fm.findFragmentById(R.id.fragment_place_overview);
        if(areaInfo == null){
            areaInfo = new AreaInfo();
            fm.beginTransaction().add(R.id.fragment_place_overview,areaInfo).commit();
        }

        //Initialising Serializable Objects//
        default_images = new int[64];
        tempList = new Area[64];
        theGame = new GameData().getInstance();
        player = theGame.getPlayer();
        initDefaultImages();
        convertToOneD();
        GridView gridView = (GridView) findViewById(R.id.overview_grid);
        adapter = new GridAdapter(OverviewActivity.this, default_images, tempList);
        gridView.setAdapter(adapter);
        initConditionalImages();

        //Button BACK//
        b_back = (Button) findViewById(R.id.button);
        b_back.setText(String.valueOf("BACK"));
        b_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        //OnClick for Grid
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
            int position, long id){
                areaInfo.updateDisplay(position);
            }
        });


    }

    /***
     * This initialises the actual location images depending on certain factors e.g. if a player is
     * located at a map location, then it changes from the default image to the player location image
     */
    public void initConditionalImages(){
        for(int i=0; i<64; i++){
            if(locationCheck(i)){
                adapter.updateImage(i,image_3);
            }
            else if(tempList[i].getTown()){
                adapter.updateImage(i,image_2);
            }
            else if(!tempList[i].getTown()){
                adapter.updateImage(i,image_1);
            }
            if(tempList[i].getStarred()){
                adapter.updateImage(i,image_4);
            }
            if(!tempList[i].getExplored()){
                adapter.updateImage(i,image_5);
            }
        }
    }

    /***
     * This function is responsible for basically finding the the row/col location of the map object
     * at the given index. However, because the map's index is representative of a 1D array and the row/
     * col location of the player is represented as a 2D array, several steps must be taken. E.g. index location
     * of the map object is 45, the 2D representation of that would be row: 4, and col: 4. A temp variable count
     * is used inside the nested for loop to count up to the index variable. Once both the row and col of the player
     * equals the nested for loop indexes, and the index variable equals the count variable, then true is returned.
     * @param index this represents a map object in the GridView
     * @return When true is returned, it essentially means this map location on the gridView is the current player
     * location a
     * and needs to be represented by an arrow
     */
    public boolean locationCheck(int index){
        boolean result = false;
        int count = 0;
        for (int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(player.getColLocation()==i && player.getRowLocation()==j){
                    if(count==index){
                        result = true;
                    }
                }
                count++;
            }
        }
        return result;
    }


    /***
     * This is responsible for initialising all the placeholder images for each location
     */
    public void initDefaultImages(){
        for(int i=0; i<64; i++){
            default_images[i]= R.drawable.ic_default;
        }
    }

    /***
     * This is a function that converts the temporary 2D array representing the map objects and
     * transfers it into a 1D temp array so that it is more consistent with the one represented in
     * the grid adapter
     */
    public void convertToOneD(){
        int index = 0;
        for(int i =0; i<8; i++){
            for(int j=0; j<8; j++){
                tempList[index]=theGame.returnArea(i,j);
                index++;
            }
        }
    }


}
