package cea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Population {

  // TODO: global constants in 1 file
  private final int MAX_SIZE;       
  private final int MAX_AGE;
  private final int CACHE_CAPACITY = 10;
  
  private List<Individual> individuals;
  private int size;
  private int initSize;
  private Individual fittest;
  
  private Class<? extends Genome> genomeClass;
  private Fitness myFitness;
  private Cache<Object, Double> cache;     
  
  //-----------------------------------------------------------------------    
  public Population(Class<? extends Genome> genomeClass, Fitness myFitness,
                    int initSize, int maxSize, int maxAge) throws Exception {
    Individual newIndiv;
    this.MAX_SIZE = maxSize;
    individuals = new ArrayList<>();
    size = initSize;
    this.initSize = initSize;
    this.genomeClass = genomeClass;
    this.myFitness = myFitness;
    this.MAX_AGE = maxAge;
    cache = new Cache<>(CACHE_CAPACITY);    

    for (int i = 0; i < initSize; i++) {        // create individuals
      newIndiv = new Individual((Genome) genomeClass.newInstance(), myFitness, maxAge);
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
      if (!cache.containsKey(i)) {               // LRU cache
        i.evaluate((double)size/MAX_SIZE, sumFitVal);
        cache.put(i, i.getFitnessValue());
      }
    }
  }
  
  //-----------------------------------------------------------------------      
  public void saveFittest() {                   // find and save fittest
    calcFittest();
    fittest.beImmortal();           // the best one won't die this iteration
  }
  
  //-----------------------------------------------------------------------      
  public Individual getFittest() {
    return fittest;
  }

  //-----------------------------------------------------------------------      
  
  public Individual generateIndividual() throws Exception {
    return new Individual((Genome) genomeClass.newInstance(), myFitness, MAX_AGE);
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
      if (indiv.getFitnessValue() > 0)
        s += indiv.toString();
    s += "-------------------------------------";
    return s;
  }
  
  // PRIVATE METHODS:
  //=======================================================================  
  private void calcFittest() {
    Collections.sort(individuals);
    fittest = individuals.get(0);
  }

}