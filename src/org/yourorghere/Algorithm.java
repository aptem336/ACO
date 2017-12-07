package org.yourorghere;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;

public class Algorithm {

    private static double startPheromone = 1;
    private static double evaporation = 0.5;
    private static double pheromoneReserve = 500;

    private static double alpha = 1;
    private static double beta = 1;

    private static double antCoef = 0.8;
    private static double randomFactor = 0.05;

    private static int numOfCities;
    private static int currentTransition;

    private static double[][] waysLength;
    private static double[][] waysPheromone;
    private static int[][] waysPopularity;

    private static ArrayList<Ant> ants = new ArrayList<>();
    private static Random random = new Random();

    private static int[] bestChain;
    private static double bestChainLength;

    public static void newMatrix(double[][] transitionMatrix) {
        waysLength = transitionMatrix;
        numOfCities = transitionMatrix.length;

        waysPheromone = new double[numOfCities][numOfCities];
        waysPopularity = new int[numOfCities][numOfCities];
        ants.clear();
        for (int i = 0; i < antCoef * numOfCities; i++) {
            ants.add(new Ant());
        }
        resetWays();
    }

    public static void iterate() {
        ants.forEach((ant) -> {
            ant.newTravel();
        });
        for (currentTransition = 1; currentTransition < numOfCities; currentTransition++) {
            ants.forEach((ant) -> {
                ant.selectCity();
            });
        }
        updatePheromone();
        updateBest();
        Data.isSolution = true;
    }

    private static void updatePheromone() {
        for (int i = 0; i < numOfCities; i++) {
            for (int j = 0; j < numOfCities; j++) {
                getWaysPheromone()[i][j] *= evaporation;
            }
        }
        ants.forEach((ant) -> {
            double pheromoneInc = pheromoneReserve / ant.getChainLength();
            for (int i = 0; i < numOfCities; i++) {
                getWaysPopularity()[ant.citiesChain[i]][ant.citiesChain[(i + 1) % numOfCities]]++;
                getWaysPheromone()[ant.citiesChain[i]][ant.citiesChain[(i + 1) % numOfCities]] += pheromoneInc;
            }
        });
    }

    private static void updateBest() {
        if (bestChain == null) {
            bestChainLength = ants.get(0).getChainLength();
            bestChain = ants.get(0).getCitiesChain();
        }
        ants.stream().filter((ant) -> (ant.getChainLength() < bestChainLength)).map((ant) -> {
            bestChainLength = ant.getChainLength();
            return ant;
        }).forEachOrdered((ant) -> {
            bestChain = ant.getCitiesChain().clone();
        });
    }

    private static void resetWays() {
        for (int i = 0; i < numOfCities; i++) {
            for (int j = 0; j < numOfCities; j++) {
                getWaysPheromone()[i][j] = startPheromone;
            }
        }
        bestChain = null;
        Data.isSolution = false;
    }

    private static class Ant {

        private int currentCity;
        private boolean visited[];
        private int[] citiesChain;
        private double chainLength;

        public Ant() {
            this.citiesChain = new int[numOfCities];
            this.visited = new boolean[numOfCities];
            newTravel();
        }

        public void selectCity() {
            //Допускаем случайный переход
            int randCity = random.nextInt(numOfCities);
            if (random.nextDouble() < randomFactor) {
                OptionalInt cityIndex = IntStream.range(0, numOfCities)
                        .filter(i -> i == randCity && !visited[i])
                        .findFirst();
                if (cityIndex.isPresent()) {
                    visit(cityIndex.getAsInt());
                    return;
                }
            }
            //Вычисляем вероятности перехода
            double[] transitionProbabilities = calcProbabilities();
            //Пальцем в небо и сомтрим куда попали
            double rand = random.nextDouble();
            double total = 0;
            for (int i = 0; i < numOfCities; i++) {
                total += transitionProbabilities[i];
                if (total >= rand) {
                    visit(i);
                    return;
                }
            }
        }

        private double[] calcProbabilities() {
            double[] transitionProbabilities = new double[numOfCities];
            double sumProbalities = 0.0;
            for (int i = 0; i < numOfCities; i++) {
                if (visited[i]) {
                    transitionProbabilities[i] = 0;
                } else {
                    transitionProbabilities[i] = Math.pow(getWaysPheromone()[currentCity][i], alpha) * Math.pow(1.0 / waysLength[currentCity][i], beta);
                }
                sumProbalities += transitionProbabilities[i];
            }
            for (int i = 0; i < numOfCities; i++) {
                transitionProbabilities[i] /= sumProbalities;
            }
            return transitionProbabilities;
        }

        private void visit(int newCity) {
            chainLength += waysLength[currentCity][newCity];
            currentCity = citiesChain[currentTransition] = newCity;
            visited[newCity] = true;
        }

        public final void newTravel() {
            chainLength = 0;
            for (int i = 0; i < numOfCities; i++) {
                visited[i] = false;
            }
            for (int i = 0; i < numOfCities; i++) {
                for (int j = 0; j < numOfCities; j++) {
                    getWaysPopularity()[i][j] = 0;
                }
            }
            currentCity = citiesChain[0] = random.nextInt(numOfCities);
            visited[currentCity] = true;
        }

        public int[] getCitiesChain() {
            return citiesChain;
        }

        public double getChainLength() {
            return chainLength;
        }
    }

    public static int[] getBestChain() {
        return bestChain;
    }

    public static double getBestChainLength() {
        return bestChainLength;
    }

    public static double[][] getWaysPheromone() {
        return waysPheromone;
    }

    public static int[][] getWaysPopularity() {
        return waysPopularity;
    }
}
