package com.ts.game.components;

public class weapon {
    private String name;
    private double damage;
    private double range;
    public weapon(String name, double damage, double range){
        this.name = name;
        this.damage = damage;
        this.range = range;
    }
    public String getName (){
        return name;
    }
}
