package com.ts.game.components;

import com.ts.game.Resources;

public class Abrams extends Tank {
    private static Resources res = Resources.getGameResources();
    public Abrams(){
        super(res.getAbrams());
        super.getTankWeapons().add(new Weapon("Vertical Slam", 100, 100, res.getBullet7()));
        super.getTankWeapons().add(new Weapon("Splitter Chain", 100, 100, res.getBullet8()));
        super.getTankWeapons().add(new Weapon("Big One", 100, 100, res.getBullet9()));
        super.getTankWeapons().add(new Weapon("Shotgun", 100, 100, res.getBullet10()));
        super.getTankWeapons().add(new Weapon("Volley", 100, 100, res.getBullet1()));
    }
}
