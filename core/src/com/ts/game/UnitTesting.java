package com.ts.game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTesting {
    private static double toCheck;
    private static double expected;

    public static void setToCheck(double toCheck) {
        UnitTesting.toCheck = toCheck;
    }
    public static void setExpected(double expected) {
        UnitTesting.expected = expected;
    }

    @Test
    public void test() {
        assertEquals(expected, toCheck, 0.0);
    }
}
