# AI Course Assignment

![Ants](http://www.callbarrier.com/wp-content/uploads/2014/06/ant-trail.jpg)

Assignment repository for AI course at [Inha University](https://eng.inha.ac.kr/)

**Problem choice:** metaheuristic algorithm "Ant colony optimization"

## Demo

![Demo](https://i.imgur.com/u4B0R0D.gif)

## Technologies used
- Java
- OpenGL

## How to run

Clone repository:

```
$ git clone git://github.com/xtenzQ/Uni-AI.git
```

To run the application, you need:
- **NetBeans IDE** (no later than 8.2) [[Download ver 8.2](https://netbeans.org/downloads/8.2/)]
- **JOGL Plugin** [[Download](http://plugins.netbeans.org/plugin/3260/netbeans-opengl-pack)]

After NetBeans installation, you should install JOGL plugin:
- Go menubar `Tools`
  - Menu `Plugin`
    - Tab `Downloaded`
      - Button `Add Plugins...`
        - Choose all plugins in the folder except `GLSL editor`.

## TODO

- [x] Algorithm
- [ ] Settings menu

## Problem

> In the ant colony optimization algorithms, an artificial ant is a simple computational agent that searches for good solutions to a given optimization problem. To apply an ant colony algorithm, the optimization problem needs to be converted into the problem of finding the shortest path on a weighted graph. In the first step of each iteration, each ant stochastically constructs a solution, i.e. the order in which the edges in the graph should be followed. In the second step, the paths found by the different ants are compared. The last step consists of updating the pheromone levels on each edge. --Wikipedia

Pseudocode:
```
procedure ACO_MetaHeuristic
  while(not_termination)
     generateSolutions()
     daemonActions()
     pheromoneUpdate()
  end while
end procedure
```

Each ant needs to construct a solution to move through the graph. To select the next edge in its tour, an ant will consider the length of each edge available from its current position, as well as the corresponding pheromone level.

![Probability equation](https://i.imgur.com/YKLUTR6.png)

- ![](https://i.imgur.com/iQ0NrFH.png) - pheromone level on ***i,j*** edge;
- ![](https://i.imgur.com/wNgN5ND.png) - parameter which controls influence on ![](https://i.imgur.com/iQ0NrFH.png)
- ![](https://i.imgur.com/uz0PGhP.png) - attractiveness of ***i,j*** edge (![](https://i.imgur.com/fuB8Nbd.png) where ***d*** is distance);
- ![](https://i.imgur.com/iQ0NrFH.png) - parameter which controls influence on ![](https://i.imgur.com/uz0PGhP.png)

The process of updating pheromones is slightly different from the one which is commonly used. I decided to update pheromone level on all the roads by multiplying the current level with evaporation coefficient and the update of pheronome on the egdes depends on its length. The longer the way, the less pheromone is added to the graph edge. 

## Solution

### Algorithm

0. Initialize transition matrix by calculating the length of the edges of the graph (hypotenuse) (`update()` function, `Data.java` file):

```Java
private static void update() {
    isData = true;
    transitionsMatrix = new double[cities.size()][cities.size()];
    colorMatrix = new int[cities.size()][cities.size()];
    for (int i = 0; i < cities.size(); i++) {
        for (int j = i + 1; j < cities.size(); j++) {
            double[] first = cities.get(i);
            double[] second = cities.get(j);
            // fill edge matrix with its length
            transitionsMatrix[i][j] = transitionsMatrix[j][i] = (Math.hypot(second[0] - first[0], second[1] - first[1]));
            colorMatrix[i][j] = Data.defaultColor;
        }
        // 
        transitionsMatrix[i][i] = 0;
        colorMatrix[i][i] = Data.defaultColor;
    }
    Algorithm.newMatrix(transitionsMatrix);
}
 ```
 
In `newMatrix()` method `transitionMatrix` is assigned to `waysLength` which represents the road length between cities.

1. Initiate ants traveling (`iterate()` function, `Algorithm.java` file)

```Java
ants.forEach((ant) -> {
    ant.newTravel();
});
```

by assigning ants to random cities

```Java
/**
 * Send ant to new city
 */
public final void newTravel() {
    chainLength = 0;
    for (int i = 0; i < numOfCities; i++) {
        visited[i] = false;
    }
    // init ant in a random city
    currentCity = citiesChain[0] = random.nextInt(numOfCities);
    visited[currentCity] = true;
}
  ```
2. Select next city for ant to travel to (`iterate()` function, `Algorithm.java` file)

```Java
for (currentTransition = 1; currentTransition <= numOfCities; currentTransition++) {
    ants.forEach((ant) -> {
        ant.selectCity();
    });
}
```

2.1. There's a possibility for an ant to travel to a random city (`selectCity()` function, `Algorithm.java` file)
  
```Java
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
```

2.2. Otherwise ant is going to city based on probabilities (`selectCity()` function, `Algorithm.java` file)

```Java
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
```

`calcProbabilities()` function:

```Java
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
   ```

3. Update pheromones on edges (`iterate()` function, `Algorithm.java` file)

```Java
updatePheromone();
```

3.1. Evaporate pheromone from every edge by multiplying evaporation coefficient with existing pheromone level on the edge (`updatePheromone()` function, `Algorithm.java` file)
  
```Java
// evaporate pheromone
for (int i = 0; i < numOfCities; i++) {
    for (int j = 0; j < numOfCities; j++) {
        getWaysPheromone()[i][j] *= evaporation;
    }
}
 ```
3.2. Add pheromone to every edge ant went through. The longer the way, the less pheromone is added to the graph edge (`updatePheromone()` function, `Algorithm.java` file)

```Java
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
 ```

4. Update best solution (`Algorithm.java` file)

```Java
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
 ```

