package cea;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cea {
  private final double mutateProb = 0.02;   
  private final double reproduceProb = 0.01;

  private Population population;
  private int maxAge;                         // max indivdual's age
  private Individual bestSolution;            // best individual found

  private Class<? extends Genome> genomeClass;  
  private Fitness myFitness;
  private Random random = new Random();

  //-----------------------------------------------------------------------  
  public Cea(Class<? extends Genome> genomeClass, Fitness myFitness,
             int popSize, int maxSize, int maxAge) throws Exception {
    this.genomeClass = genomeClass;
    this.myFitness = myFitness;
    this.maxAge = maxAge;
    population = new Population(this.genomeClass, 
                                myFitness, popSize, maxSize);
    population.init(maxAge);       // create and initialize population
  }

  //-----------------------------------------------------------------------  
  private void evaluate() {
    population.evaluate();
  }
  
  //-----------------------------------------------------------------------  
  private void saveFittest() {
    population.saveFittest();
    bestSolution = population.getIndiviual(0);
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
    List<Individual> offspring = generateOffspring(parents);
    population.addAll(offspring);       // add offspring to population
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
  private List<Individual> generateOffspring(List<Individual> parents)
                                                      throws Exception {
    List<Individual> offspring = new ArrayList();
    Individual parent1, parent2, child;

    while (parents.size() >= 2) {
      parent1 = takeParent(parents);    // takes a parent from list
      parent2 = takeParent(parents);
      child = new Individual((Genome) genomeClass.newInstance(),
                              myFitness, maxAge);
      child.recombinate(parent1, parent2);
      child.mutate(mutateProb);
      offspring.add(child);             // adds a child to list
    }
    return offspring;
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

  //-----------------------------------------------------------------------    
  public Individual getBestSolution() {
    return bestSolution;        // returns best solution found
  }
  
  //-----------------------------------------------------------------------    
  public Population getPopulation() {
    return population;  
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
}