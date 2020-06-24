package com.example.ericwojcik.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import java.util.List;

public class SmellScopeActivity extends AppCompatActivity {

    private TextView tv_title, tv_title2;
    private Button b_back;

    private List<Item> listItem;
    private List<String> listString;
    private GameData theGame;
    private Player thePlayer;
    private SmellScope ss;
    private Area[][] map;

    private RecyclerView rv;
    private MyAdapter adapter;
    private LinearLayoutManager rvLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_smellscope);


        //Initialising widgets//
        b_back = (Button) findViewById(R.id.smell_back_id);
        tv_title = (TextView) findViewById(R.id.smell_title_id);
        tv_title2 = (TextView) findViewById(R.id.textView2);

        theGame = new GameData().getInstance();
        map = theGame.getMap();
        thePlayer = theGame.getPlayer();
        ss = thePlayer.findSmellScope();
        listItem = ss.getItemList();
        listString = ss.getStringList();

        rv = (RecyclerView) findViewById(R.id.recycler_3_id);
        adapter = new MyAdapter();
        rvLayout = new LinearLayoutManager(SmellScopeActivity.this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

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
            LayoutInflater li = LayoutInflater.from(SmellScopeActivity.this);
            return new MyViewHolder(li,parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder vh, int position) {
            vh.bind(position);
        }

        @Override
        public int getItemCount() {
            return listItem.size();
        }
    }


    private class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textBox1,textBox2;
        private View divider;

        public MyViewHolder(LayoutInflater li, ViewGroup parent){
            super(li.inflate(R.layout.list_entry_3,parent,false));

            textBox1 = (TextView) itemView.findViewById(R.id.r3_text_id);
            textBox2 = (TextView) itemView.findViewById(R.id.r3_text_id2);
            divider = (View) itemView.findViewById(R.id.divider2);
        }

        public void bind(int position){
            textBox1.setText(String.valueOf(listItem.get(position).getDesc()));
            textBox2.setText(String.valueOf(listString.get(position)));
        }
    }


}
