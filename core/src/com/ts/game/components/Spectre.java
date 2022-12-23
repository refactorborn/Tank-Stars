package com.ts.game.components;

import com.ts.game.Resources;

public class Spectre extends Tank {
    private static Resources res = Resources.getGameResources();
    public Spectre(){
        super(res.getSpectre());
        super.getTankWeapons().add(new Weapon("Railgun", 100, 100, res.getBullet1()));
        super.getTankWeapons().add(new Weapon("Lightning Ball", 100, 100, res.getBullet3()));
        super.getTankWeapons().add(new Weapon("Lightning Drone", 100, 100, res.getBullet5()));
        super.getTankWeapons().add(new Weapon("Lightning", 100, 100, res.getBullet7()));
        super.getTankWeapons().add(new Weapon("Orbital Strike", 100, 100, res.getBullet9()));
        super.getTankWeapons().add(new Weapon("Tesla Zone", 100, 100, res.getBullet10()));
    }
}
