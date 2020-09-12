package com.asgarov.elevator_challenge.domain;

public enum Direction {
    UP,
    DOWN;

    public static Direction calculate(int floorFrom, int floorTo){
        return floorFrom < floorTo ? UP : DOWN;
    }
}
