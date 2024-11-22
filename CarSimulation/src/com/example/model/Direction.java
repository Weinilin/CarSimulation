package com.example.model;

public enum Direction {
//north, south, east, west
    N, S, E, W;

    public Direction rotateLeft() {
        return switch (this) {
            case N -> W;
            case S -> E;
            case E -> N;
            case W -> S;
        };
    }

    public Direction rotateRight() {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }
}

