package com.mathwhiz.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MathServiceTest {
    @Test
    void identifiesPrimeNumbers() {
        assertEquals(1, MathService.isPrime(7));
        assertEquals(0, MathService.isPrime(8));
        assertEquals(0, MathService.isPrime(1));
        assertEquals(1, MathService.isPrime(2));
        assertEquals(0, MathService.isPrime(0));
    }

    @Test
    void calculatesModulusAndRejectsZeroDivisor() {
        assertEquals(2, MathService.calculateMod(17, 5));
        assertEquals(1, MathService.calculateMod(10, 3));
        assertThrows(IllegalArgumentException.class, () -> MathService.calculateMod(17, 0));
    }

    @Test
    void calculatesTrigonometricValuesInDegrees() {
        assertEquals(0.5, MathService.calculateSin(30), 0.001);
        assertEquals(0.5, MathService.calculateCos(60), 0.001);
        assertEquals(1.0, MathService.calculateTan(45), 0.001);
    }

    @Test
    void rejectsUndefinedTangentAngles() {
        assertThrows(IllegalArgumentException.class, () -> MathService.calculateTan(90));
        assertThrows(IllegalArgumentException.class, () -> MathService.calculateTan(270));
    }
}
