package com.example.ericwojcik.game;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class StatusBar extends Fragment{
    private TextView showHp;
    private TextView showCash;
    private TextView showMass;
    private TextView showStatus;
    private GameData theGame;
    private Player thePlayer;
    private Area[][] map;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_bar, container, false);
        showHp = (TextView) view.findViewById(R.id.show_health_id);
        showCash = (TextView) view.findViewById(R.id.show_cash_id);
        showMass = (TextView) view.findViewById(R.id.show_mass_id);
        showStatus = (TextView) view.findViewById(R.id.show_status_id);
        theGame = new GameData().getInstance();
        thePlayer = theGame.getPlayer();

        refreshViews();

        // Inflate the layout for this fragment
        return view;
    }

    public void refreshViews(){
        showHp.setText(String.valueOf(thePlayer.getHealth()));
        showCash.setText(String.valueOf(thePlayer.getCash()));
        showMass.setText(String.valueOf(thePlayer.getTotalEqMass()));
        showStatus.setText(String.valueOf("RUNNING"));
    }


    public void updateText(String type, double msg){
        switch (type) {
            case "health":
                showHp.setText(String.valueOf(msg));
                break;
            case "cash":
                showCash.setText(String.valueOf(msg));
                break;
            case "mass":
                showMass.setText(String.valueOf(msg));
                break;
        }
    }



}
