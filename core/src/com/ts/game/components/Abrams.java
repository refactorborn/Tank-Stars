package com.ts.game.components;

import com.ts.game.Resources;

public class Abrams extends tank{
    private static Resources res = new Resources();
    public Abrams(){
        super(res.getAbrams());
        super.getTankWeapons().add(new weapon("Vertical Slam", 100, 100));
        super.getTankWeapons().add(new weapon("Splitter Chain", 100, 100));
        super.getTankWeapons().add(new weapon("Big One", 100, 100));
        super.getTankWeapons().add(new weapon("Shotgun", 100, 100));
        super.getTankWeapons().add(new weapon("Volley", 100, 100));
    }
}
