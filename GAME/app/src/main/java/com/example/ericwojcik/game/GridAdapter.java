package com.example.ericwojcik.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {

    private final int[] image;
    private final Area[] map;
    private Context context;
    private LayoutInflater infl;

    /***
     * Constructor for grid
     */
    public GridAdapter(Context context,  int[] image, Area[] map){
        this.context=context;
        this.image=image;
        this.map=map;
    }

    @Override
    public int getCount() {
        return 64;
    }

    @Override
    public Object getItem(int position) {
        return map[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vH;
        View gridView = convertView;
        if(convertView == null){
            infl = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = infl.inflate(R.layout.custom_grid,null);
            vH = new ViewHolder();
            vH.iV = (ImageView) gridView.findViewById(R.id.cellView);
            gridView.setTag(vH);
        }
        else{
            vH = (ViewHolder) gridView.getTag();
        }
        vH.iV.setImageResource(image[position]);






        return gridView;
    }

    public void updateImage(int position, int newImage){
        image[position] = newImage;
        notifyDataSetChanged();
    }

    public static class ViewHolder{
        ImageView iV;
    }
}
