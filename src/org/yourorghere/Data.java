package org.yourorghere;

import java.awt.Dimension;
import java.util.ArrayList;

public class Data {

    final static int SIZE = 80;
    final static Dimension SIZES = new Dimension(SIZE, SIZE);

    public static ArrayList<double[]> cities = new ArrayList<double[]>();
    public static double[][] transitionsMatrix;

//<editor-fold defaultstate="collapsed" desc="Values">
    public static double tempValue1;
    public static double tempValue2;
    public static double tempValue3;
    public static double tempValue4;
    public static double tempValue5;

    public static boolean valueChanged1;
    public static boolean valueChanged2;
    public static boolean valueChanged3;
    public static boolean valueChanged4;
    public static boolean valueChanged5;

    public static double valueInWork1;
    public static double valueInWork2;
    public static double valueInWork3;
    public static double valueInWork4;
    public static double valueInWork5;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Âound">
    public static int min1 = 0;
    public static int max1 = 50;
    public static int min2 = 0;
    public static int max2 = 50;
    public static int min3 = 0;
    public static int max3 = 50;
    public static int min4 = 0;
    public static int max4 = 50;
    public static int min5 = 0;
    public static int max5 = 50;
//</editor-fold>

    public static int mainColor = 0xFFFFFFBB;
    public static int defaultColor = 0x7A7A7A77;
    public static int solutionColor = 0x2A62FFFF;
    public static int futherInformColor = 0xFFFFFF00;
    public static int[][] colorMatrix;

    public static boolean showPheromone = false;
    public static boolean showBestChain = true;
    public static boolean showAnts = false;
    public static boolean inverse = false;

    public static boolean newPoint = false;
    public static boolean clear = false;
    public static boolean iterate = false;
    public static boolean isData = false;
    public static boolean isSolution = false;

    public static double coef = 0;

    public static void synchronize() {
        if (newPoint) {
            cities.add(listenerCoords());
            update();
            newPoint = false;
        }
        if (inverse) {
            ACO.gl.glClearColor(((mainColor >> 24) & 0xFF) / 255f, ((mainColor >> 16) & 0xFF) / 255f, ((mainColor >> 8) & 0xFF) / 255f, ((mainColor) & 0xFF) / 255f);
            mainColor ^= 0xFFFFFF00;
            solutionColor ^= 0xFFFFFF00;
            futherInformColor ^= 0xFFFFFF00;
            inverse = false;
        }
        if (clear) {
            cities.clear();
            iterate = false;
            isData = false;
            isSolution = false;
            clear = false;
        }
        if (valueChanged1) {
            valueInWork1 = tempValue1;
            valueChanged1 = false;
        }
        if (valueChanged2) {
            valueInWork2 = tempValue2;
            valueChanged2 = false;
        }
        if (valueChanged3) {
            valueInWork3 = tempValue3;
            valueChanged3 = false;
        }
        if (valueChanged4) {
            valueInWork4 = tempValue4;
            valueChanged4 = false;
        }
        if (valueChanged5) {
            valueInWork5 = tempValue5;
            valueChanged5 = false;
        }
    }

    private static void update() {
        isData = true;
        transitionsMatrix = new double[cities.size()][cities.size()];
        colorMatrix = new int[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            for (int j = i + 1; j < cities.size(); j++) {
                double[] first = cities.get(i);
                double[] second = cities.get(j);
                transitionsMatrix[i][j] = transitionsMatrix[j][i] = (Math.hypot(second[0] - first[0], second[1] - first[1]));
                colorMatrix[i][j] = Data.defaultColor;
            }
            transitionsMatrix[i][i] = 0;
            colorMatrix[i][i] = Data.defaultColor;
        }
        Algorithm.newMatrix(transitionsMatrix);
    }

    public static void clearColor() {
        colorMatrix = new int[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            for (int j = i + 1; j < cities.size(); j++) {
                colorMatrix[i][j] = 0x000000;
            }
            colorMatrix[i][i] = 0x000000;
        }
    }

    private static double[] listenerCoords() {
        return new double[]{(Listener.location.x - 400) / 3.05, (-Listener.location.y + 380) / 3.05};
    }

}
