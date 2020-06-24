package com.example.ericwojcik.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TownActivity extends AppCompatActivity {

    private StatusBar statBar;
    private RecyclerView rv, rv1;
    private MyAdapter adapter;
    private MyAdapter2 adapter2;
    private LinearLayoutManager rvLayout;
    private LinearLayoutManager rvLayout1;

    //Widgets//
    private Button b_back;
    private TextView tv_title, tv_rName1, tv_rName2;


    private List<Item> listItem;
    private GameData theGame;
    private Player thePlayer;
    private Area[][] map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        theGame = new GameData().getInstance();
        thePlayer = theGame.getPlayer();
        map = theGame.getMap();
        listItem = new ArrayList<>();
        listItem = map[thePlayer.getColLocation()][thePlayer.getRowLocation()].getListItem();

        //Initialising Fragments//
        FragmentManager fm = getSupportFragmentManager();
        statBar = (StatusBar) fm.findFragmentById(R.id.fragment_place);
        if(statBar == null){
            statBar = new StatusBar();
            fm.beginTransaction().add(R.id.fragment_place_template,statBar).commit();
        }

        //Setting up RecyclerView//
        rv = (RecyclerView) findViewById(R.id.recycler_1_id);
        rv1 = (RecyclerView) findViewById(R.id.recycler_2_id);
        adapter = new MyAdapter();
        adapter2 = new MyAdapter2();
        rvLayout = new LinearLayoutManager(TownActivity.this);
        rvLayout1 = new LinearLayoutManager(TownActivity.this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);
        rv1.setAdapter(adapter2);
        rv1.setLayoutManager(rvLayout1);

        //Initialising widgets//
        b_back = (Button) findViewById(R.id.temp_button_1);
        tv_title = (TextView) findViewById(R.id.title_text_id);
        tv_rName1 = (TextView) findViewById(R.id.recycler_title_1);
        tv_rName2 = (TextView) findViewById(R.id.recycler_title_2);
        b_back.setText(String.valueOf("BACK"));
        tv_title.setText(String.valueOf("TOWN"));
        tv_rName1.setText(String.valueOf("Buy"));
        tv_rName2.setText(String.valueOf("Inventory"));

        //Button Back//
        b_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    /***
     * Adapter responsible for dealing with the buying list (items available for purchase
     */
    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            LayoutInflater li = LayoutInflater.from(TownActivity.this);
            return new MyViewHolder(li,parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder vh, int position) {
            vh.bind(listItem.get(position));
        }

        @Override
        public int getItemCount() {
            return listItem.size();
        }
    }


    /***
     * Adapter responsible for dealing with the the selling or using of items
     */
    public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder2>{

        @Override
        public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int i) {
            LayoutInflater li = LayoutInflater.from(TownActivity.this);
            return new MyViewHolder2(li,parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder2 vh, int position) {
            vh.bind(thePlayer.get(position));
        }

        @Override
        public int getItemCount() {
            return thePlayer.getSize();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        private Item itm;
        private TextView textBox;
        private Button buttonBox;
        private View divider;

        public MyViewHolder(LayoutInflater li, ViewGroup parent){
            super(li.inflate(R.layout.list_entry_1,parent,false));

            textBox = (TextView) itemView.findViewById(R.id.r1_text_id);
            buttonBox = (Button) itemView.findViewById(R.id.r1_button_1_id);
            divider = (View) itemView.findViewById(R.id.divider2);
            buttonBox.setText(String.valueOf("BUY"));

            buttonBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itm instanceof SmellScope && thePlayer.getSmellCount()>1){
                        listItem.remove(itm);
                        Toast.makeText(TownActivity.this, "ALREADY HAVE SMELL-O-SCOPE!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(thePlayer.getCash()>=itm.getInValue()){
                            listItem.remove(itm);
                            adapter.notifyItemRemoved(getAdapterPosition());
                            if(itm instanceof Equipment){
                                thePlayer.add((Equipment)itm);
                                adapter2.notifyItemInserted(getAdapterPosition());
                            }
                            else if(itm instanceof Food){
                                thePlayer.setHealth(((Food) itm).getHealth());
                            }
                            thePlayer.setCash(thePlayer.getCash()-itm.getInValue());
                            statBar.refreshViews();
                        }
                        else{
                            Toast.makeText(TownActivity.this, "INSUFFECIENT FUNDS",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            });
        }

        public void bind(Item itm){
            this.itm = itm;
            textBox.setText(String.valueOf(itm.getDesc()));
        }
    }




    private class MyViewHolder2 extends RecyclerView.ViewHolder{
        private Equipment e;
        private TextView textBox;
        private Button buttonBox, buttonBox1;
        private View divider;

        public MyViewHolder2(LayoutInflater li, ViewGroup parent){
            super(li.inflate(R.layout.list_entry_2,parent,false));

            textBox = (TextView) itemView.findViewById(R.id.r2_text_id);
            buttonBox = (Button) itemView.findViewById(R.id.r2_button_1_id);
            buttonBox1 = (Button) itemView.findViewById(R.id.r2_button_2_id);
            divider = (View) itemView.findViewById(R.id.divider2);
            buttonBox.setText((String.valueOf("SELL")));
            buttonBox1.setText((String.valueOf("USE")));

            //SELL button
            buttonBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItem.add(e);
                    adapter.notifyItemInserted(getAdapterPosition());
                    thePlayer.remove(e);
                    adapter2.notifyItemRemoved(getAdapterPosition());
                    thePlayer.setCash(e.getInValue()+thePlayer.getCash());
                    statBar.refreshViews();
                }
            });

            //USE button
            buttonBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(e instanceof SmellScope){

                        int col = thePlayer.getColLocation();
                        int row = thePlayer.getRowLocation();
                        ((SmellScope) e).useScope(map,col,row);

                        //start activity//
                        Intent iIntent = new Intent(TownActivity.this, SmellScopeActivity.class);
                        startActivity(iIntent);
                    }
                    else if (e instanceof BenKenobi){
                        thePlayer = ((BenKenobi) e).useKenobi(listItem,thePlayer);
                        thePlayer.remove(e);
                        listItem.clear();
                    }
                    else if (e instanceof ImprobDrive){
                       theGame.setMap(((ImprobDrive) e).useDrive(map));
                        Toast.makeText(TownActivity.this, "AN IMPROBABILITY HAS OCCURRED",
                                Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                    }
                }
            });

        }

        public void bind(Equipment e){
            this.e = e;
            textBox.setText(String.valueOf(e.getDesc()));
        }
    }

}
