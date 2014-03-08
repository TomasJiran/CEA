package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import java.util.ArrayList;
import java.util.List;


public class Population {
  private final int DIM;
  private final int MAX_SIZE;
  
  private List<Individual> individuals;
  private int size;
  private Individual fittest;
  
  private MyFitness myFitness;
  
  
  //-----------------------------------------------------------------------    
  public Population(MyFitness myFitness, int dim, int initSize, int maxSize) {
    Individual newIndiv;
    this.DIM = dim;
    this.MAX_SIZE = maxSize;
    individuals = new ArrayList<Individual>();
    size = initSize;
    this.myFitness = myFitness;

    for (int i = 0; i < size; i++) {   // create individuals
      newIndiv = new Individual(myFitness, DIM);
      individuals.add(newIndiv);
      newIndiv.initFitnessValue();    
    }
  }
  
  //-----------------------------------------------------------------------    
  public void evaluate() {             // evaluate population
    double sumFitVal = 0.1;            // TODO: initial sum of all fitness values
    for (Individual i: individuals)       
      sumFitVal += i.getFitnessValue();            

    for (Individual indiv: individuals) {
      if (myFitness.noMoreEvaluations())
        return;
      indiv.evaluate((double)size/MAX_SIZE, sumFitVal);
    }
  }
  
  //-----------------------------------------------------------------------    
  public void evaluate(List<Individual> indivs) {    // evaluate population
    double sumFitVal = 0.1;            // TODO: initial sum of all fitness values
    for (int i = 0; i < indivs.size(); i++) 
      sumFitVal += indivs.get(i).getFitnessValue();

    for (int i = 0; i < indivs.size(); i++) {
      if (myFitness.noMoreEvaluations())
        return;
      indivs.get(i).evaluate((double)size/MAX_SIZE, sumFitVal);
    }
  }
  
  //-----------------------------------------------------------------------      
  public void saveFittest() {          // find and save fittest
    fittest = calcFittest();
    fittest.beImmortal();              // the best one won't die this iteration
  }
  
  //-----------------------------------------------------------------------      
  public Individual getFittest() {
    return fittest;
  }

  //-----------------------------------------------------------------------      
  public Individual generateIndividual(Individual parent) {
    Individual indiv = new Individual(myFitness, DIM, parent);
    //MyGenome gP = parent.getGenome();
    //MyGenome gI = indiv.getGenome();
    //gI.insert(gP);
    return indiv;
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
  public void joinIndividuals(List<Individual> indivs) {
    for (int i = 0; i < indivs.size(); i++) {
      if (individuals.size() >= MAX_SIZE)
        break;
      //indiv = pop.getIndiviual(i);
      this.addIndividual(indivs.get(i));
    }
  }
  
  //-----------------------------------------------------------------------      
  public Individual getIndiviual(int index) {
    return individuals.get(index);
  }
  
  //-----------------------------------------------------------------------      
  public int getSize() {
    return individuals.size();         // TODO: overit spravnost
  }

  //-----------------------------------------------------------------------      
  public int getMaxSize() {
    return MAX_SIZE;
  }

  //-----------------------------------------------------------------------        
  public void update() {               // update population for the next iteration
    for (Individual indiv: individuals) 
      indiv.incAge();
  }
  
  //-----------------------------------------------------------------------        
  public void adapt() {             
    for (Individual indiv: individuals) 
      indiv.adapt();                   // calling the local search function
  }
  
  //-----------------------------------------------------------------------    
  @Override
  public String toString() {
    String s = "";
    System.out.println("population size: " +this.size);
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
      if (f < min) { //&& (f > 0)) {
        min = f;
        index = i;
      }
    }
    return individuals.get(index);
  }

}