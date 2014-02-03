package cea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static cea.MyConst.*;


public class Population {
  private final int DIM;
  private final int MAX_SIZE;
  
  private List<Individual> individuals;
  private int size;
  private Individual fittest;
  
  private Fitness myFitness;
  
  //-----------------------------------------------------------------------    
  public Population(Fitness myFitness, int dim, int initSize, int maxSize) {
    Individual newIndiv;
    this.DIM = dim;
    this.MAX_SIZE = maxSize;
    individuals = new ArrayList<>();
    size = initSize;
    this.myFitness = myFitness;

    for (int i = 0; i < size; i++) {        // create individuals
      newIndiv = new Individual(myFitness, DIM);
      newIndiv.randGenes();
      individuals.add(newIndiv);
      newIndiv.initFitnessValue();
    }
  }
  
  //-----------------------------------------------------------------------    
  public void evaluate() {                       // evaluate population
    double sumFitVal = 0;                        // sum of all fitness values
    for (Individual i: individuals)       
      sumFitVal += i.getFitnessValue();

    for (Individual i: individuals) {
//      if (! cache.containsKey(i)) {               // LRU cache
        if (myFitness.noMoreEvaluations())
          return;
        i.evaluate((double)size/MAX_SIZE, sumFitVal);
  //      cache.put(i, i.getFitnessValue());
   //   }
    }
  }
  
  //-----------------------------------------------------------------------      
  public void saveFittest() {                   // find and save fittest
    fittest = calcFittest();
    fittest.beImmortal();       // the best one won't die this iteration
  }
  
  //-----------------------------------------------------------------------      
  public Individual getFittest() {
    return fittest;
  }

  //-----------------------------------------------------------------------      
  
  public Individual generateIndividual() {
    return new Individual(myFitness, DIM);
  }

  //-----------------------------------------------------------------------      
  public void addIndividual(Individual indiv) {
    individuals.add(indiv);
    size++;
  }

  //-----------------------------------------------------------------------      
  public void removeIndividual(Individual indiv) {
    individuals.remove(indiv);
    size--;
  }
  
  //-----------------------------------------------------------------------      
  public Individual getIndiviual(int index) {
    return individuals.get(index);
  }
  
  //-----------------------------------------------------------------------      
  public int getSize() {
    return individuals.size();
  }

  //-----------------------------------------------------------------------        
  /*public void addAll(List<Individual> offspring) {
    for (Individual child: offspring) 
      addIndividual(child);
  }*/

  //-----------------------------------------------------------------------        
  public void update() {            // update population for the next iteration
    for (Individual indiv: individuals) 
      indiv.incAge();
  }
  
  //-----------------------------------------------------------------------        
  public void adapt() {             
    for (Individual indiv: individuals) 
      indiv.adapt();                // calling the local search function
  }
  
  //-----------------------------------------------------------------------    
  @Override
  public String toString() {
    String s = "";
    for (Individual indiv : individuals) 
      s += indiv.toString();
    s += "-------------------------------------";
    return s;
  }
  
  // PRIVATE METHODS:
  //=======================================================================  
  private Individual calcFittest() {
    double min = Double.MAX_VALUE;
    double f;
    int index = 0;
    
    for (int i = 0; i < size; i++) {
      f = individuals.get(i).getFitnessValue();
      if ((f < min) && (f > 0)) {
        min = f;
        index = i;
      }
    }
    return individuals.get(index);
  }

}