package com.ts.game.components;

import com.ts.game.Resources;

public class Spectre extends tank {
    private static Resources res = new Resources();
    public Spectre(){
        super(res.getSpectre());
        super.getTankWeapons().add(new weapon("Railgun", 100, 100));
        super.getTankWeapons().add(new weapon("Lightning Ball", 100, 100));
        super.getTankWeapons().add(new weapon("Lightning Drone", 100, 100));
        super.getTankWeapons().add(new weapon("Lightning", 100, 100));
        super.getTankWeapons().add(new weapon("Orbital Strike", 100, 100));
        super.getTankWeapons().add(new weapon("Tesla Zone", 100, 100));
    }
}
