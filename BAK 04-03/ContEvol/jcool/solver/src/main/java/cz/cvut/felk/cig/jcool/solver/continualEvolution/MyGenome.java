package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import cz.cvut.felk.cig.jcool.solver.BasicSolver;
import static cz.cvut.felk.cig.jcool.solver.continualEvolution.MyConst.*;


public class MyGenome implements Genome {
  private final int DIM;
  private double[] x;                     // genes

  //-------------------------------------------------------------------------  
  public MyGenome(int dim) { 
    DIM = dim;
    x = new double[dim];
  }

  //-------------------------------------------------------------------------  
  public void crossover(MyGenome parentGenome) {
    // whole aritmetic crossover
    double[] x2 = parentGenome.x;
    for (int i = 0; i < x2.length; i++) {
      x[i] = (x[i] + x2[i]) / 2;       
    }
  }

  /*  
  //-------------------------------------------------------------------------  
  private void crossoverS(Object o1, Object o2) {
  }

  //-------------------------------------------------------------------------  
  private void crossoverT(double[] x2) {   
    // whole aritmetic crossover
    for (int i = 0; i < x2.length; i++) {
      x[i] = (x[i] + x2[i]) / 2;       
    }
  }*/

  //-------------------------------------------------------------------------  
  public void mutate() {
    // 10 percent mutated randomly, 90 percent so to be different by 10 percent of the x range
    // muze se prizpusobit podle generaci
    for (int i = 0; i < DIM; i++) {
      if (RandGenerator.nextDouble() <= MUTATE_PROB) {
        if (RandGenerator.nextDouble() <= MUTATE_RANDOM_PROB)  {
          x[i] = this.getRandGene();
        }
        else {
          x[i] = x[i] + RandGenerator.nextSign() * (X_MAX - X_MIN)/10;
          if (x[i] < X_MIN)            // checking x value range
            x[i] = X_MIN;
          if (x[i] > X_MAX)            // checking x value range
            x[i] = X_MAX;
        }
      }
    }
  }
  
  //-------------------------------------------------------------------------  
  public void setResult(BasicSolver solver) {
    x = solver.getResultX();
  }
  
  //-------------------------------------------------------------------------  
  public void randGenes() {
    for (int i = 0; i < DIM; i++) {
      x[i] = getRandGene();
    }
  }
  
  //-------------------------------------------------------------------------  
  public void setGenes(MyGenome g) {
    for (int i = 0; i < DIM; i++) 
      this.x[i] = g.x[i];
  }

  //-------------------------------------------------------------------------  
  public double[] getGenes() {
    return x;
  }
  
  //-------------------------------------------------------------------------  
  private double getRandGene() {
    return RandGenerator.nextDouble() * (X_MAX - X_MIN) + X_MIN;    
  }
  
  //-------------------------------------------------------------------------  
  /*public Object getStructure() {
    return null;
  }

  //-------------------------------------------------------------------------  
  public double[] getTopology() {       
    return x;
  }

  //-------------------------------------------------------------------------  
  public void setTopology(double[] topology) {        // for adaptation
    x = topology;
  }*/

  /*//-------------------------------------------------------------------------  
  public Object getSolution() {
    return x;
  }*/

  //-------------------------------------------------------------------------  
  public void print(String msg, double[] x, double y) {
    System.out.print(msg);
    for (int i = 0; i < x.length; i++) 
      System.out.printf("%10f ", x[i]);        
    System.out.println(y);
  }

  //-------------------------------------------------------------------------  
  public void print(String msg, double[] x) {
    System.out.print(msg);
    for (int i = 0; i < x.length; i++) 
      System.out.printf("%10f ", x[i]);        
    System.out.println(3*Math.sin(x[0]) + (0.1*x[0]-3)*(0.1*x[0]-3));
  }

}