package com.mathwhiz.service;

public final class MathService {
    private static final double TAN_COSINE_TOLERANCE = 1.0e-10;

    private MathService() {
    }

    public static int isPrime(int number) {
        if (number < 2) {
            return 0;
        }
        for (int divisor = 2; divisor <= number / divisor; divisor++) {
            if (number % divisor == 0) {
                return 0;
            }
        }
        return 1;
    }

    public static int calculateMod(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("The divisor cannot be zero.");
        }
        return a % b;
    }

    public static double calculateSin(double degree) {
        requireFinite(degree);
        return Math.sin(Math.toRadians(degree));
    }

    public static double calculateCos(double degree) {
        requireFinite(degree);
        return Math.cos(Math.toRadians(degree));
    }

    public static double calculateTan(double degree) {
        requireFinite(degree);
        double radians = Math.toRadians(degree);
        if (Math.abs(Math.cos(radians)) < TAN_COSINE_TOLERANCE) {
            throw new IllegalArgumentException("Tangent is undefined for this angle.");
        }
        return Math.tan(radians);
    }

    private static void requireFinite(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("The angle must be a finite number.");
        }
    }
}
