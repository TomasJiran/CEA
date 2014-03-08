package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import static cz.cvut.felk.cig.jcool.solver.continualEvolution.MyConst.*;
import java.util.ArrayList;
import java.util.List;


public class Cea {
    
  private Population population;

  //-----------------------------------------------------------------------  
  public Cea(MyFitness myFitness, int dim, int initSize, int maxSize) {
    population = new Population(myFitness, dim, initSize, maxSize);
  }

  //-----------------------------------------------------------------------    
  public Object getBestSolution() {    // return the best solution found
    return population.getFittest().getGenome().getGenes();   
  }

  //-----------------------------------------------------------------------    
  public double getBestValue() {       // return the best fitness value found
    return population.getFittest().getFitnessValue();
  }
  
  //-----------------------------------------------------------------------    
  public void printPopulation(int generation) {
    System.out.println("");
    System.out.printf("GENERATION: %2d \n", generation);
    System.out.println(population);
  }
  
  //-----------------------------------------------------------------------    
  public void step() {                 // main algorithm
    List<Individual> children;
    
    evaluate();
    saveFittest();
    children = reproduce();
    evaluate(children); 
    eliminate();
    join(children);
    //adapt();
    update();
  }

  // PRIVATE METHODS:
  //=======================================================================  
  private void evaluate() {            // evaluate the population
    population.evaluate();
  }
  
  //-----------------------------------------------------------------------  
  private void evaluate(List<Individual> indivs) {
    population.evaluate(indivs);
  }
  
  //-----------------------------------------------------------------------  
  private void saveFittest() {
    population.saveFittest();
  }

  //-----------------------------------------------------------------------    
  private List<Individual> reproduce() {
    List<Individual> parents = selectParents(); 
    return generateChildren(parents);  // generate new individuals
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
  private List<Individual> generateChildren(List<Individual> parents) {
    // TODO: number of parents ?
    Individual parent1, parent2, child;
    List<Individual> children = new ArrayList<Individual>();

    while (parents.size() >= 2) {
      parent1 = takeParent(parents);   // take a parent from the list
      parent2 = takeParent(parents);
      int numOfChildren = Math.min(population.getMaxSize()-population.getSize(), N_CHILDREN);
      
      for (int i = 0; i < numOfChildren; i++) {
        child = population.generateIndividual(parent1);
        child.crossover(parent2);
        child.mutate();
        children.add(child);
      }
    }
    return children;
  }
  
  //-----------------------------------------------------------------------    
  private Individual takeParent(List<Individual> parents) { // take a parent from the list
    Individual parent;
    int index = RandGenerator.nextInt(parents.size()); 
                                       // choose a random parent
    parent = parents.get(index);
    parents.remove(index);
    return parent;
  }
  
  //-----------------------------------------------------------------------  
  private void eliminate() {
    Individual indiv;
    
    for (int i = 0; i < population.getSize(); i++) {
      indiv = population.getIndiviual(i);
      if (indiv.getEP() > ELIMINATE_PROB)
        population.removeIndividual(indiv);
    }
  }
  
  //-----------------------------------------------------------------------    
  private void join(List<Individual> indivs) {
    population.joinIndividuals(indivs);  
  }
  
  //-----------------------------------------------------------------------    
  private void adapt() {
    population.adapt();
  }
  
  //-----------------------------------------------------------------------    
  private void update() {             // update for next iteration
    population.update();
  }
}