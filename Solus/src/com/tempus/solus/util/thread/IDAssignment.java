package com.tempus.solus.util.thread;

public class IDAssignment {
    private int baseID;

    public IDAssignment(int baseID) {
        this.baseID = baseID;
    }

    public int next() {
        return baseID++;
    }

    public int getCurrentID() {
        return baseID;
    }
}