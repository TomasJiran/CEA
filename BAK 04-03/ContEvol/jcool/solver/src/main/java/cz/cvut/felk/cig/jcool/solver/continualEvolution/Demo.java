package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import static cz.cvut.felk.cig.jcool.solver.continualEvolution.MyConst.INIT_SIZE;
import static cz.cvut.felk.cig.jcool.solver.continualEvolution.MyConst.MAX_SIZE;


/**
 *
 * @author Tomas Jiran
 */
public class Demo {
  //-----------------------------------------------------------------------  
  public static void main(String[] args) {
    int dim = 1;
    int maxfunevals = 1000;
    // Obtain the target function value, which only use is termination 
    double targetFitnessValue = -2.99999;
    double bestFitnessValue = Integer.MAX_VALUE; 
                                       // best fitness function value found
    int initSize = 5;          // initial population size
    int maxSize = 10;            // max population size
    int i = 0;                         // number of CEA iterations
      
    MyFitness myFitness = new MyFitness(maxfunevals, dim); 
    Cea cea = new Cea(myFitness, dim, initSize, maxSize);

    while(true) {
      if (myFitness.noMoreEvaluations()) 
        break;                         // number of evaluations exhausted 
      i++;
      cea.step();                      // evolution algorithm step
      cea.printPopulation(i);          // population output
        
      bestFitnessValue = cea.getBestValue();
      if (bestFitnessValue < targetFitnessValue)
        break;
    }
    
    printSolution(targetFitnessValue, bestFitnessValue);//, (double[]) cea.getBestSolution());
  }
  
  //-----------------------------------------------------------------------  
  private static void printSolution(double targetY, double bestY) {//, double[] bestX) {
    System.out.println("------------------------------------------------");
    //System.out.println("Best solution found: " +bestX);
    System.out.println("Best fitness found: " +bestY);
    System.out.println("Optimal fitness:    " +targetY);  
  }
  
}