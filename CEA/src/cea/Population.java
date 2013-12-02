package cea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
  private final int MAX_SIZE;         
  
  private List<Individual> individuals;
  private int size;
  
  private Class<? extends Genome> genomeClass;
  private Fitness myFitness;
  
  //-----------------------------------------------------------------------    
  public Population(Class<? extends Genome> genomeClass, Fitness myFitness,
                    int size, int maxSize) {
    this.genomeClass = genomeClass;
    this.myFitness = myFitness;
    this.size = size;
    this.MAX_SIZE = maxSize;
    individuals = new ArrayList<>();
  }
  
  //-----------------------------------------------------------------------    
  public void init(int maxAge) throws Exception {  // creates individuals
    Individual indiv;
    for (int i = 0; i < size; i++) {
      indiv = new Individual((Genome) genomeClass.newInstance(), myFitness, maxAge);
      individuals.add(indiv);
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
    Collections.sort(individuals);
    individuals.get(0).beImmortal();      // the best one won't die
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