package com.jv01.generations;

import com.jv01.fonctionals.GameEntity;

public class Merchants extends GameEntity {

    public int id;

    public Merchants(int id) {
        super("merchant");
        this.id = id;
    }

    public Merchants(int id, int[] position) {
        super(position, "merchant");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
