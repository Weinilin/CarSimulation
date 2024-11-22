package com.example.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void testRotateLeft() {
        // Test rotating left from N
        assertEquals(Direction.W, Direction.N.rotateLeft());

        // Test rotating left from S
        assertEquals(Direction.E, Direction.S.rotateLeft());

        // Test rotating left from E
        assertEquals(Direction.N, Direction.E.rotateLeft());

        // Test rotating left from W
        assertEquals(Direction.S, Direction.W.rotateLeft());
    }

    @Test
    public void testRotateRight() {
        // Test rotating right from North
        assertEquals(Direction.E, Direction.N.rotateRight());

        // Test rotating right from East
        assertEquals(Direction.S, Direction.E.rotateRight());

        // Test rotating right from South
        assertEquals(Direction.W, Direction.S.rotateRight());

        // Test rotating right from West
        assertEquals(Direction.N, Direction.W.rotateRight());
    }
}

