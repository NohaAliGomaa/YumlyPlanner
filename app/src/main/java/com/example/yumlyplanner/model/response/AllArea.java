package com.example.yumlyplanner.model.response;

import com.example.yumlyplanner.R;
import com.example.yumlyplanner.model.pojo.Area;

import java.util.ArrayList;
import java.util.List;

public class AllArea {
    private static AllArea allAreas=null;
    private  List<Area> areas= new  ArrayList<>();

    private AllArea() {
        areas.add(new Area("American", R.drawable.america));
        areas.add(new Area("British", R.drawable.britain));
        areas.add(new Area("Canadian", R.drawable.canada));
        areas.add(new Area("Chinese", R.drawable.china));
        areas.add(new Area("Croatian", R.drawable.coratia));
        areas.add(new Area("Dutch", R.drawable.dutch));
        areas.add(new Area("Egyptian", R.drawable.egypt));
        areas.add(new Area("French", R.drawable.france));
        areas.add(new Area("Greek", R.drawable.greece));
        areas.add(new Area("Indian", R.drawable.india));
        areas.add(new Area("Irish", R.drawable.ireland));
        areas.add(new Area("Italian", R.drawable.italy));
        areas.add(new Area("Jamaican", R.drawable.jamaica));
        areas.add(new Area("Japanese", R.drawable.japan));
        areas.add(new Area("Kenyan", R.drawable.kenya));
        areas.add(new Area("Malaysian", R.drawable.malaysia));
        areas.add(new Area("Mexican", R.drawable.mexico));
        areas.add(new Area("Moroccan", R.drawable.morocco));
        areas.add(new Area("Polish", R.drawable.poland));
        areas.add(new Area("Portuguese", R.drawable.portugal));
        areas.add(new Area("Russian", R.drawable.russia));
        areas.add(new Area("Spanish", R.drawable.spain));
        areas.add(new Area("Thai", R.drawable.thailand));
        areas.add(new Area("Tunisian", R.drawable.tunisia));
        areas.add(new Area("Turkish", R.drawable.turkey));
        areas.add(new Area("Uruguay", R.drawable.uruguay));
        areas.add(new Area("Vietname", R.drawable.vietnam));



    }

    public static AllArea getInstance() {
        if (allAreas == null) {
            allAreas = new AllArea();
        }

        return allAreas;
    }
    public List<Area> getAllArea() {
        return areas;
    }
}