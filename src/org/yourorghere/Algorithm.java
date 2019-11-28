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

    /**
     *
     * @param transitionMatrix
     */
    public static void newMatrix(double[][] transitionMatrix) {
        //
        waysLength = transitionMatrix;
        //
        numOfCities = transitionMatrix.length;

        // strength of pheromones on every edge
        waysPheromone = new double[numOfCities][numOfCities];
        // how many ants went through in one iteration (optional)
        waysPopularity = new int[numOfCities][numOfCities];
        ants.clear();
        // adds ants to graph
        for (int i = 0; i < antCoef * numOfCities; i++) {
            ants.add(new Ant());
        }
        resetWays();
    }

    /**
     * Iterate function
     */
    public static void iterate() {
        ants.forEach((ant) -> {
            ant.newTravel();
        });
        for (currentTransition = 1; currentTransition <= numOfCities; currentTransition++) {
            ants.forEach((ant) -> {
                ant.selectCity();
            });
        }
        updatePheromone();
        updateBest();
        Data.isSolution = true;
    }

    private static void updatePheromone() {
        // evaporate pheromone
        for (int i = 0; i < numOfCities; i++) {
            for (int j = 0; j < numOfCities; j++) {
                getWaysPheromone()[i][j] *= evaporation;
            }
        }
        // 
        ants.forEach((ant) -> {
            // pheromoneReserve - pheromone amount for each ant
            // the longer the path the less we add
            double pheromoneInc = pheromoneReserve / ant.getChainLength();
            for (int i = 0; i < numOfCities; i++) {
                // we add popularity to every edge ant went through
                getWaysPopularity()[ant.citiesChain[i]][ant.citiesChain[(i + 1) % numOfCities]]++;
                // we add pheromone to every edge ant went through
                getWaysPheromone()[ant.citiesChain[i]][ant.citiesChain[(i + 1) % numOfCities]] += pheromoneInc;
            }
        });
    }

    /**
     * Update solution function
     */
    private static void updateBest() {
        // 
        if (bestChain == null) {
            bestChainLength = ants.get(0).getChainLength();
            bestChain = ants.get(0).getCitiesChain();
        }
        // we search for the shortest way and the chain of the city it produced
        ants.forEach((ant) -> {
            if (ant.getChainLength() < bestChainLength) {
                bestChainLength = ant.getChainLength();
                bestChain = ant.getCitiesChain().clone();
            }
        });
    }

    /**
     * Start pheromone distribution function
     */
    private static void resetWays() {
        for (int i = 0; i < numOfCities; i++) {
            for (int j = 0; j < numOfCities; j++) {
                getWaysPopularity()[i][j] = 0;
            }
        }
        for (int i = 0; i < numOfCities; i++) {
            for (int j = 0; j < numOfCities; j++) {
                getWaysPheromone()[i][j] = startPheromone;
            }
        }
        // transition vector
        bestChain = null;
        Data.isSolution = false;
    }

    private static class Ant {

        private int currentCity;
        private boolean[] visited;
        private int[] citiesChain;
        private double chainLength;

        public Ant() {
            this.citiesChain = new int[numOfCities + 1];
            this.visited = new boolean[numOfCities];
            newTravel();
        }

        /**
         * Function which selects next city to travel
         */
        public void selectCity() {
            // Possibility of a transition to a random city
            int randCity = random.nextInt(numOfCities);
            // 
            if (random.nextDouble() < randomFactor) {
                // 
                if (!visited[randCity]) {
                    visit(randCity);
                    return;
                }
            }
            // 
            // Calculate transition probabilities
            double[] transitionProbabilities = calcProbabilities();
            // Transition to city
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

        /**
         * Calculate probability of ant travel
         *
         * @return
         */
        private double[] calcProbabilities() {
            double[] transitionProbabilities = new double[numOfCities];
            // if it's last city
            if (currentTransition == numOfCities) {
                for (int i = 0; i < numOfCities; i++) {
                    // we stop
                    transitionProbabilities[i] = 0;
                }
                // get back to start city
                transitionProbabilities[citiesChain[0]] = 1;
                return transitionProbabilities;
            }
            // calculate according to formula
            double sumProbalities = 0.0;
            for (int i = 0; i < numOfCities; i++) {
                if (visited[i]) {
                    transitionProbabilities[i] = 0;
                } else {
                    // 
                    transitionProbabilities[i] = Math.pow(getWaysPheromone()[currentCity][i], alpha) * Math.pow(1.0 / waysLength[currentCity][i], beta);
                }
                sumProbalities += transitionProbabilities[i];
            }
            for (int i = 0; i < numOfCities; i++) {
                transitionProbabilities[i] /= sumProbalities;
            }
            return transitionProbabilities;
        }

        /**
         * Length and City update function
         * @param newCity
         */
        private void visit(int newCity) {
            // add length to the chain
            chainLength += waysLength[currentCity][newCity];
            // add city to the chain
            currentCity = citiesChain[currentTransition] = newCity;
            visited[newCity] = true;
        }

        /**
         * Send ant to
         */
        public final void newTravel() {
            chainLength = 0;
            for (int i = 0; i < numOfCities; i++) {
                visited[i] = false;
            }
            /*for (int i = 0; i < numOfCities; i++) {
                for (int j = 0; j < numOfCities; j++) {
                    getWaysPopularity()[i][j] = 0;
                }
            }*/
            // init ant in a random city
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
