package cea;

import static cea.MyConst.*;


public class Individual {
  
  private double fitnessValue;
  private int age;
  private double EP;            // elimination probability
  private double RP;            // reproduction probability
  
  private MyGenome myGenome;
  private Fitness myFitness;  
          
  //-------------------------------------------------------------------------  
  public Individual(Fitness myFitness, int dim) {
    age = 0;
    myGenome = new MyGenome(dim);
    this.myFitness = myFitness;
  }

  //-------------------------------------------------------------------------  
  public void evaluate(double normSize, double sumFitVal) {
    double normFV;        // normalized fitness value
    double normAge;       // normalized age
    fitnessValue = myFitness.getValue(myGenome);  
    normFV = 0.5 - fitnessValue/sumFitVal;    
    normAge = ((double)age)/MAX_AGE;

    if (age > MAX_AGE)   
      EP = 1;
    else
      EP = calcEP(calcRawEP(normAge, normFV), normSize);
    RP = calcRP(calcRawRP(normAge, normFV), normSize);
  }
  
  //-------------------------------------------------------------------------  
  public void recombinate(Individual parent1, Individual parent2, int childNumber) {
    myGenome.crossoverS(parent1.myGenome.getStructure(), parent2.myGenome.getStructure(), childNumber);
    myGenome.crossoverT(parent1.myGenome.getTopology(), parent2.myGenome.getTopology(), childNumber);    
  }

  //-------------------------------------------------------------------------  
  public void mutate() {
    myGenome.mutate();
  }
  
  //-------------------------------------------------------------------------  
  public void adapt() {         // the local search function
    Object initSolution = myGenome.getTopology();
    Object bestSolution = initSolution;
    Object nextSolution;
    int iterations;
    int count;
    boolean foundBetter;
    
    iterations = 0;
    do {
      foundBetter = false;
      count = 0;
      do {
        nextSolution = myGenome.generateNextSolution(bestSolution);
        count++;
        if (myFitness.noMoreEvaluations())
          return;
        if (myGenome.isBetter(myFitness.getTopologyValue(nextSolution),
                              myFitness.getTopologyValue(bestSolution))) {
          bestSolution = nextSolution;
          foundBetter = true;
        }
      } while (!myGenome.pivotRuleSatisfied(foundBetter, count));
      myGenome.setTopology(bestSolution);
      iterations++;
    } while (!myGenome.depthConditionSatisfied(iterations));
  }

  //-------------------------------------------------------------------------  
  public double getFitnessValue() {
    return fitnessValue;
  }

  //-------------------------------------------------------------------------  
  public void initFitnessValue() {
    fitnessValue = myFitness.getValue(myGenome);
  }
  
  //-------------------------------------------------------------------------  
  public void randGenes() {
    myGenome.randGenes();
  }
  
  //-------------------------------------------------------------------------  
  public double getEP() {
    return EP;
  }

  //-------------------------------------------------------------------------  
  public double getRP() {
    return RP;
  }

  //-------------------------------------------------------------------------  
  public void beImmortal() {
    EP = 0;
    age = 0;
  }

  //-------------------------------------------------------------------------  
  public void incAge() {
    age++;
  }

  //-------------------------------------------------------------------------  
  public MyGenome getGenome() {
    return myGenome;
  }


  // PRIVATE METHODS:
  //=======================================================================  
  //-------------------------------------------------------------------------  
  private double calcEP(double rawEP, double n) { // rawEP, population size
    return Math.min(Math.max(n*rawEP, 0), 1);
  }
  
  //-------------------------------------------------------------------------  
  private double calcRP(double rawRP, double n) { // rawEP, population size
    return Math.min(Math.max((1-n)*(1-n)*rawRP, 0), 1);
  }

  //-------------------------------------------------------------------------  
  private double calcRawEP(double a, double f) { // age, fitnessValue
    return Math.min(Math.max(f*Math.pow(a, 10) + 3*a*(1-f), 0), 1);    
  }

  //-------------------------------------------------------------------------  
  private double calcRawRP(double a, double f) { // age, fitnessValue
    return (1-a)*f + a*f/2;
  }
  
}