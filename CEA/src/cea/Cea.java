package cea;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cea {
  private final double mutateProb = 0.02;   
  private final double reproduceProb = 0.01;

  private Population population;
  //private Individual bestSolution;            // best individual found

  //private Class<? extends Genome> genomeClass;  
  //private Fitness myFitness;
  private Random random = new Random();

  //-----------------------------------------------------------------------  
  public Cea(Class<? extends Genome> genomeClass, Fitness myFitness,
             int initSize, int maxSize, int maxAge) throws Exception {
    population = new Population(genomeClass, myFitness, initSize, 
                               maxSize, maxAge);
  }

  //-----------------------------------------------------------------------    
  public Object getBestSolution() {
    return population.getFittest().getGenome().getSolution();   // returns best solution found
  }
  
  //-----------------------------------------------------------------------    
  public Object getOptimalSolution() {
    return population.getFittest().getGenome().getOptimal();   // returns the optimal solution
  }
  
  //-----------------------------------------------------------------------    
  public void printPopulation(int generation) {
    System.out.printf("GENERATION: %2d \n", generation);
    System.out.println(population);
  }

  //-----------------------------------------------------------------------    
  public void step() throws Exception { // main algorithm
    evaluate();
    saveFittest();
    eliminate();
    adapt();    
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
    //TODO
  }

  //-----------------------------------------------------------------------    
  private void reproduce() throws Exception {
    List<Individual> parents = selectParents();
    generateOffspring(parents);     // generates new population individuals
    //List<Individual> offspring = generateOffspring(parents);
    //population.addAll(offspring);       // add offspring to population
  }

  //-----------------------------------------------------------------------    
  private List<Individual> selectParents() {
    List<Individual> parents = new ArrayList();
    Individual indiv;

    for (int i = 0; i < population.getSize(); i++) {
      indiv = population.getIndiviual(i);
      if (indiv.getRP() > reproduceProb)
        parents.add(indiv);
    }
    return parents;
  }
  
  //-----------------------------------------------------------------------    
  private void generateOffspring(List<Individual> parents) throws Exception {
    Individual parent1, parent2, child;

    while (parents.size() >= 2) {
      parent1 = takeParent(parents);    // takes a parent from list
      parent2 = takeParent(parents);
      child = population.generateIndividual();
      //child = population.createIndividual();
      child.recombinate(parent1, parent2);
      child.mutate(mutateProb);
      population.addIndividual(child);  // adds a child 
    }
  }
  //-----------------------------------------------------------------------    
  private Individual takeParent(List<Individual> parents) {
    Individual parent;
    int index = random.nextInt(parents.size());
    parent = parents.get(index);
    
    parents.remove(index);
    return parent;
  }
  
  //-----------------------------------------------------------------------    
  private void update() {        // update for next iteration
    population.update();
  }

}