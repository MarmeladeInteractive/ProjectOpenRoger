package com.jv01.buildings;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.jv01.generations.Objects;
import com.jv01.generations.Tools;

public class PersonalFarm{
    public String gameName;
    public int boxSize;

    public JPanel backgroundPanel;

    public List<int[][]> restrictedAreas = new ArrayList<>();
    public List<Object[]> trigerEvents = new ArrayList<>();
    public int trigerSize = 50;


    public PersonalFarm(String gameName, int boxSize, JPanel backgroundPanel){
        this.gameName = gameName;
        this.boxSize = boxSize;

        this.backgroundPanel = backgroundPanel;

        addTools();
    }

    public void addTools(){
        
    }
    
}
