package com.example.ericwojcik.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AreaInfo extends Fragment{

    private TextView showLocation;
    private EditText editDesc;
    private Button editButton;
    private ToggleButton starLocation;
    private GameData theGame;
    private Player thePlayer;
    private Area[][] map;
    private String currentLocation;
    private int col, row, i,j;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area_info, container, false);

        theGame = new GameData().getInstance();
        map = theGame.getMap();
        thePlayer = theGame.getPlayer();
        showLocation = (TextView) view.findViewById(R.id.show_location_id);
        editDesc = (EditText) view.findViewById(R.id.edit_desc_id);
        starLocation = (ToggleButton) view.findViewById(R.id.star_area_button);
        editButton = (Button) view.findViewById(R.id.edit_button_id);

        col = thePlayer.getColLocation();
        row = thePlayer.getRowLocation();

        updateLocation(col,row);
        updateToggle();
        updateDesc(col,row);

        //Button Edit//
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String input = editDesc.getText().toString();
                Toast.makeText(getContext(), "Set description to: "+input,
                        Toast.LENGTH_SHORT).show();
                map[thePlayer.getColLocation()][thePlayer.getRowLocation()].setDesc(input);
                updateDesc(col,row);
                theGame.setMap(map);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    /***
     * An update method specifically accessed from GridView (in OverviewActivity) as it converts the
     * 1D coordinate into 2D coordinates, then updates all the display views accordingly
     * @param position index of grid element that was tapped
     */
    public void updateDisplay(int position){
        int index = 0;
        for(i=0; i<8; i++){
            for(j=0; j<8; j++){
                if(index == position){
                    Toast.makeText(getActivity(), "HELLO",
                            Toast.LENGTH_SHORT).show();
                    updateDesc(i,j);
                    updateLocation(i,j);
                    Boolean starCheck = map[i][j].getStarred();
                    if(starCheck){
                        starLocation.setSelected(true);
                        starLocation.setChecked(true);
                    }
                    else{
                        starLocation.setSelected(false);
                        starLocation.setChecked(false);
                    }
                    starLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                starLocation.setChecked(true);
                                map[i][j].setStarred(true);
                            }
                            else{
                                starLocation.setChecked(false);
                                map[i][j].setStarred(false);
                            }
                        }
                    });
                    theGame.setMap(map);
                }
                index++;
            }
        }
    }

    /***
     * UpdateToggle is the method used to update the toggle button. This is called both by the fragment
     * and the parent activity
     */
    public void updateToggle(){
        Boolean starCheck = map[thePlayer.getColLocation()][thePlayer.getRowLocation()].getStarred();
        if(starCheck){
            starLocation.setSelected(true);
            starLocation.setChecked(true);
        }
        else{
            starLocation.setSelected(false);
            starLocation.setChecked(false);
        }
        starLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    starLocation.setChecked(true);
                    map[thePlayer.getColLocation()][thePlayer.getRowLocation()].setStarred(true);
                }
                else{
                    starLocation.setChecked(false);
                    map[thePlayer.getColLocation()][thePlayer.getRowLocation()].setStarred(false);
                }
            }
        });
        theGame.setMap(map);
    }


    /***
     * Updates the description to that corresponding to the player's current location
     * @param col player's column location
     * @param row player's row location
     */
    public void updateDesc(int col, int row){
        editDesc.setText(map[col][row].getDesc());
    }

    /***
     * Updates the location status to either that of TOWN or WILD
     * @param col player's column location
     * @param row player's row location
     */
    public void updateLocation(int col, int row){
        if(map[col][row].getTown()){
                currentLocation = "TOWN";
        }
        else{
            currentLocation = "WILD";
        }
        showLocation.setText(String.valueOf(currentLocation));
    }

}
