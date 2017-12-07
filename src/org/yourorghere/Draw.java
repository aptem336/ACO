package org.yourorghere;

public class Draw {

    public static void calcsDraw() {
        if (!Data.isData) {
            return;
        }
        if (Data.iterate) {
            Algorithm.iterate();
        }
        if (!Data.isSolution) {
            draw();
            return;
        }
        Data.clearColor();
        if (Data.showAnts) {
            int[][] waysPopularity = Algorithm.getWaysPopularity();
            for (int i = 0; i < Data.cities.size(); i++) {
                for (int j = i + 1; j < Data.cities.size(); j++) {
                    int antsCount = waysPopularity[i][j] + waysPopularity[j][i];
                    double coef = Data.transitionsMatrix[i][j] / antsCount;
                    double[] begin = Data.cities.get(i);
                    double[] end = Data.cities.get(j);
                    double[] diffVector = {(end[0] - begin[0]) / Data.transitionsMatrix[i][j], (end[1] - begin[1]) / Data.transitionsMatrix[i][j]};
                    for (int k = 0; k < antsCount; k++) {
                        Build.buildLightSphere(diffVector[0] * coef * k + begin[0], diffVector[1] * coef * k + begin[1], 1, 1, Data.solutionColor ^ 0xFFFFFF44);
                    }
                }
            }
        }
        if (Data.showPheromone) {
            double[][] waysPheromone = Algorithm.getWaysPheromone();
            double sumPheromone = 0;
            for (int i = 0; i < Data.cities.size(); i++) {
                for (int j = 0; j < Data.cities.size(); j++) {
                    sumPheromone += waysPheromone[i][j];
                }
            }
            for (int i = 0; i < Data.cities.size(); i++) {
                for (int j = 0; j < Data.cities.size(); j++) {
                    Data.colorMatrix[i][j] = Data.futherInformColor | (int) (0x00000FF0 * waysPheromone[i][j] / sumPheromone);
                }
            }
        }
        if (Data.showBestChain) {
            int[] bestChain = Algorithm.getBestChain().clone();
            for (int i = 0; i < bestChain.length; i++) {
                int firstNum = bestChain[i];
                int secondNum = bestChain[(i + 1) % bestChain.length];
                Data.colorMatrix[Math.min(firstNum, secondNum)][Math.max(firstNum, secondNum)] = Data.solutionColor;
            }
        }
        draw();
    }

    private static void draw() {
        for (int i = 0; i < Data.cities.size(); i++) {
            for (int j = i + 1; j < Data.cities.size(); j++) {
                double[] first = Data.cities.get(i);
                double[] second = Data.cities.get(j);
                Build.buildLine(new double[][]{first, second}, Data.colorMatrix[i][j]);
            }
        }
        Data.cities.forEach((city) -> {
            Build.buildSphere(city[0], city[1], 0, 2, Data.solutionColor);
        });
    }
}
