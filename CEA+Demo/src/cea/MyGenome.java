package cea;

import java.util.Random;


public class MyGenome implements Genome{
  private final int min = -3;
  private final int max = 3;
  private double x;
  private Random random = new Random();

  //-------------------------------------------------------------------------  
  public MyGenome() {
    x = random.nextDouble()*(max-min) + min;
  }
  
  //-------------------------------------------------------------------------  
  @Override
  public void crossoverS(Object o1, Object o2, int childNumber) {
  }

  //-------------------------------------------------------------------------  
  @Override
  public void crossoverT(Object x1, Object x2, int childNumber) {
    x = ((double)x1 + (double)x2 - childNumber/10)/2;
  }

  //-------------------------------------------------------------------------  
  @Override
  public void mutate(double prob) {
    if (random.nextDouble() <= prob)
      x = random.nextDouble()*(max-min) + min;
  }
  
  //-------------------------------------------------------------------------  
  @Override
  public Object generateNextSolution(Object solution) { // for adaptation
    double x = (double) solution;
    double dx = 0.05;
    if (x + dx >= max)
      return x;
    return x + dx;
  }

  //-------------------------------------------------------------------------  
  @Override
  public boolean isBetter(Object topology1, Object topology2) { // for adaptation
    return (((double) topology1) > ((double) topology2));
  }
  
  //-------------------------------------------------------------------------  
  @Override
  public boolean pivotRuleSatisfied(boolean foundBetter, int count) { // for adaptation
    return foundBetter || count > 1;
  }

  //-------------------------------------------------------------------------  
  @Override
  public boolean depthConditionSatisfied(int iterations) { // for adaptation
    return iterations > 3;
  }
  
  //-------------------------------------------------------------------------  
  @Override
  public Double getStructure() {
    return null;
  }

  //-------------------------------------------------------------------------  
  @Override
  public Double getTopology() {
    return x;
  }

  //-------------------------------------------------------------------------  
  @Override
  public void setTopology(Object topology) {
    x = (double) topology;
  }

  //-------------------------------------------------------------------------  
  @Override
  public Object getOptimal() {      // return the optimal solution
    return (4-Math.sqrt(76))/6;
  }

  //-------------------------------------------------------------------------  
  @Override
  public Object getSolution() {
    return x;
  }

}