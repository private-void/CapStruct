package com.private_void.core.math.utils;

import static com.private_void.core.constants.Constants.ZERO;

public final class Utils {

    private Utils() {}

    public static boolean compareToZero(double value) {
        return Math.abs(value) < ZERO;
    }

    public static double[] matrixMultiplication(final double[][] A, final double[] B) {
        double[] C = new double[3];
        for (int i = 0; i < 3; i++) {
            C[i] = 0;
            for (int k = 0; k < 3; k++) {
                C[i] += A[i][k] * B[k];
            }
        }
        return C;
    }

    public static double[][] inverseMatrix(final double[][] A) {
        double[][] B = new double[3][3];
        try {
            double detA = A[0][0] * det(A, 0, 0)
                        + A[0][1] * det(A, 0, 1)
                        + A[0][2] * det(A, 0, 2);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    B[j][i] = det(A, i, j) / detA;
                }
            }
        }
        catch (ArithmeticException e) {
            System.out.println("Division by zero.");
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return B;
    }

    private static double det(final double[][] A, int i, int j) {
        i += 1;
        if (i == 3)
            i -= 3;

        j += 1;
        if (j == 3)
            j -= 3;

        int a = i + 1;
        if (a == 3)
            a -= 3;

        int b = j + 1;
        if (b == 3)
            b -= 3;

        return A[i][j] * A[a][ b] - A[i][b] * A[a][j];
    }

    public static double getMax(final double[] A) {
        double max = Math.abs(A[0]);
        for (int i = 1; i < 3; i++) {
            if (Math.abs(A[i]) > max) {
                max = Math.abs(A[i]);
            }
        }
        return max;
    }

    public static double getConeLength(double radius, double divergentAngleR, double coneCoefficient) {
        return radius * (1 / Math.tan(divergentAngleR)) * coneCoefficient;
    }

    public static double getConeDivergentAngle(double radius, double length, double coneCoefficient) {
        return Math.atan((radius / length) * coneCoefficient);
    }

    public static double getTorusLength(double curvRadius, double curvAngleR) {
        return curvRadius * Math.sin(curvAngleR);
    }

    public static double getTorusCurvRadius(double length, double curvAngleR) {
        return length / Math.sin(curvAngleR);
    }
}