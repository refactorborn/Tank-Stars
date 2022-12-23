package com.ts.game.components;

import com.ts.game.Resources;

public class Frost extends Tank {
    private static Resources res = Resources.getGameResources();
    public Frost(){
        super(res.getFrost());
        super.getTankWeapons().add(new Weapon("Frostblast", 100, 100, res.getBullet1()));
        super.getTankWeapons().add(new Weapon("Frostbite", 100, 100, res.getBullet2()));
        super.getTankWeapons().add(new Weapon("Assualt Drones", 100, 100, res.getBullet3()));
        super.getTankWeapons().add(new Weapon("Blizzard", 100, 100, res.getBullet4()));
        super.getTankWeapons().add(new Weapon("High Pressure", 100, 100, res.getBullet5()));
        super.getTankWeapons().add(new Weapon("Ice Splitter", 100, 100, res.getBullet6()));
    }
}
