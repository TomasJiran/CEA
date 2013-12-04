package cea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
  private final int MAX_SIZE;        
  
  private List<Individual> individuals;
  private int size;
  private int initSize;
  private Individual fittest;
  
  private Class<? extends Genome> genomeClass;
  private Fitness myFitness;
  private int maxAge;
  
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
    this.maxAge = maxAge;

    for (int i = 0; i < initSize; i++) {
      newIndiv = new Individual((Genome) genomeClass.newInstance(), myFitness, maxAge);
      individuals.add(newIndiv);
    }
  }
  
  //-----------------------------------------------------------------------    
  public void evaluate() {  
    double sumFitVal = 0;
    for (Individual i: individuals)       
      sumFitVal += i.getFitnessValue();
      // TODO: efektivneji
    for (Individual i: individuals)
      i.evaluate((double)size/MAX_SIZE, sumFitVal);
  }
  
  //-----------------------------------------------------------------------      
  public void saveFittest() {
    calcFittest();
    fittest.beImmortal();       // the best one won't die this iteration
  }
  
  //-----------------------------------------------------------------------      
  private void calcFittest() {
    Collections.sort(individuals);
    fittest = individuals.get(0);
  }
  
  //-----------------------------------------------------------------------      
  public Individual getFittest() {
    return fittest;
  }

  //-----------------------------------------------------------------------      
  
  public Individual generateIndividual() throws Exception {
    return new Individual((Genome) genomeClass.newInstance(), myFitness, maxAge);
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
  public void addAll(List<Individual> offspring) {
    for (Individual child: offspring) 
      addIndividual(child);
  }

  //-----------------------------------------------------------------------        
  public void update() {
    for (Individual indiv: individuals) 
      indiv.incAge();
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

}