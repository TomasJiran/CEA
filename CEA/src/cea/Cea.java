package cea;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static cea.MyConst.*;


public class Cea {
    
  private Population population;
  private Random random = new Random();

  
  //-----------------------------------------------------------------------  
  public Cea(Fitness myFitness, int dim, int initSize, int maxSize) {
    population = new Population(myFitness, dim, initSize, maxSize);
  }

  //-----------------------------------------------------------------------    
  public Object getBestSolution() {   // return the best solution found
    return population.getFittest().getGenome().getSolution();   
  }

  //-----------------------------------------------------------------------    
  public Object getBestValue() {   // return the best fitness value found
    return population.getFittest().getFitnessValue();
  }
  
  //-----------------------------------------------------------------------    
  /*public Object getOptimalSolution() { // return the optimal solution
    return population.getFittest().getGenome().getOptimal();   
  }*/
  
  //-----------------------------------------------------------------------    
  public void printPopulation(int generation) {
    System.out.printf("GENERATION: %2d \n", generation);
    System.out.println(population);
  }
  
  //-----------------------------------------------------------------------    
  public void step() { // main algorithm
    evaluate();
    saveFittest();
    eliminate();
    //adapt();    
    reproduce();  
    update();
  }

  // PRIVATE METHODS:
  //=======================================================================  
  private void evaluate() {
    population.evaluate();
  }
  
  //-----------------------------------------------------------------------  
  private void saveFittest() {
    population.saveFittest();
  }

  //-----------------------------------------------------------------------  
  private void eliminate() {
    Individual indiv;
    
    for (int i = 0; i < population.getSize(); i++) {
      indiv = population.getIndiviual(i);
      if (indiv.getEP() > random.nextDouble())
        population.removeIndividual(indiv);
    }
  }
  
  //-----------------------------------------------------------------------    
  private void adapt() {
    population.adapt();
  }

  //-----------------------------------------------------------------------    
  private void reproduce() {
    List<Individual> parents = selectParents();
    generateOffspring(parents);     // generates new population of individuals
  }

  //-----------------------------------------------------------------------    
  private List<Individual> selectParents() {  // select a reproduction pool
    List<Individual> parents = new ArrayList();
    Individual indiv;

    for (int i = 0; i < population.getSize(); i++) {
      indiv = population.getIndiviual(i);
      if (indiv.getRP() > REPRODUCE_PROB)
        parents.add(indiv);
    }
    return parents;
  }
  
  //-----------------------------------------------------------------------    
  private void generateOffspring(List<Individual> parents) {
    Individual parent1, parent2;
    Individual[] children = new Individual[N_CHILDREN];

    while (parents.size() >= 2) {
      parent1 = takeParent(parents);            // take a parent from list
      parent2 = takeParent(parents);
      for (int i = 0; i < N_CHILDREN; i++) {
        children[i] = population.generateIndividual();
        children[i].recombinate(parent1, parent2, i);
        children[i].mutate();
        population.addIndividual(children[i]);  // add a child to the population
      }
    }
  }
  
  //-----------------------------------------------------------------------    
  private Individual takeParent(List<Individual> parents) { // take a parent from list
    Individual parent;
    int index = random.nextInt(parents.size()); // choose a random parent
    parent = parents.get(index);
    
    parents.remove(index);
    return parent;
  }
  
  //-----------------------------------------------------------------------    
  private void update() {             // update for next iteration
    population.update();
  }
}