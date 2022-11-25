package com.ts.game.components;

import com.ts.game.Resources;

public class Frost extends tank{
    private static Resources res = new Resources();
    public Frost(){
        super(res.getFrost());
        super.getTankWeapons().add(new weapon("Frostblast", 100, 100));
        super.getTankWeapons().add(new weapon("Frostbite", 100, 100));
        super.getTankWeapons().add(new weapon("Assualt Drones", 100, 100));
        super.getTankWeapons().add(new weapon("Blizzard", 100, 100));
        super.getTankWeapons().add(new weapon("High Pressure", 100, 100));
        super.getTankWeapons().add(new weapon("Ice Splitter", 100, 100));
    }
}
