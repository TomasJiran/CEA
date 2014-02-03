package cea;

import java.util.Random;
import static cea.MyConst.*;


public class MyGenome {
  private final int DIM;
  private final int MIN = -5;
  private final int MAX = 5;  
  private double[] x;                     // genes
  private Random random = new Random();

  //-------------------------------------------------------------------------  
  public MyGenome(int dim) {
    DIM = dim;
    x = new double[dim];
  }

  //-------------------------------------------------------------------------  
  public void crossoverS(Object o1, Object o2, int childNumber) {
  }

  //-------------------------------------------------------------------------  
  public void crossoverT(Object o1, Object o2, int childNumber) {
    int index = random.nextInt(DIM);
    double[] x1 = (double[]) o1;
    double[] x2 = (double[]) o2;
    
    for (int i = 0; i < index; i++) 
      x[i] = x1[i];
    for (int j = index; j < DIM; j++) 
      x[j] = x2[j];
    }

  //-------------------------------------------------------------------------  
  public void mutate() {
    for (int i = 0; i < DIM; i++) {
      if (random.nextDouble() <= MUTATE_PROB)
        x[i] = 10. * random.nextDouble() - 5.;
    }
  }
  
  //-------------------------------------------------------------------------  
  public Object generateNextSolution(Object solution) { // for adaptation
    double s = (double) solution;
    double ds = 0.1;
    for (int i = 0; i < DIM; i++) {
      if (x[i] + ds >= MAX)
        x[i] = MIN;
      else
        x[i] += ds;
    }
    return x;
  }

  //-------------------------------------------------------------------------  
  public boolean isBetter(Object topology1, Object topology2) { // for adaptation
    return (((double) topology1) < ((double) topology2));
  }
  
  //-------------------------------------------------------------------------  
  public boolean pivotRuleSatisfied(boolean foundBetter, int count) { // for adaptation
    return foundBetter || count > 1;
  }

  //-------------------------------------------------------------------------  
  public boolean depthConditionSatisfied(int iterations) { // for adaptation
    return iterations > 5;
  }
  
  //-------------------------------------------------------------------------  
  public void randGenes() {
    for (int i = 0; i < DIM; i++) 
      x[i] = 10. * random.nextDouble() - 5.;
  }
  
  //-------------------------------------------------------------------------  
  public Object getStructure() {
    return null;
  }

  //-------------------------------------------------------------------------  
  public double[] getTopology() {       
    return x;
  }

  //-------------------------------------------------------------------------  
  public void setTopology(Object topology) {        // for adaptation
    x = (double[]) topology;
  }

  //-------------------------------------------------------------------------  
  public Object getSolution() {
    return x;
  }

}