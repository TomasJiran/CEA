package cea;


public class Start {
  // The CEA is called from here
  
  //-----------------------------------------------------------------------
  public static void main(String[] args) throws Exception  {
    Fitness myFitness = new MyFitness();    
    int initSize = 10;              // initial population size
    int maxSize = 20;               // max population size
    int maxAge = 8;                 // max individual's age
    int iterations = 15;
    
    Cea cea = new Cea(MyGenome.class, myFitness, initSize, maxSize, maxAge);
    for (int i = 0; i < iterations; i++) {
      cea.step();                   // CEA algorithm
      cea.printPopulation(i+1);     // population output
    }
  
    compareSolution(cea, myFitness);
  }

  //-----------------------------------------------------------------------
  private static void compareSolution(Cea cea, Fitness myFitness) {
    double x, opt;
    
    x   = (double) cea.getBestSolution();
    opt = (double) cea.getOptimalSolution();

    System.out.print(myFitness);
    System.out.println("Best solution found: " +x);
    System.out.println("Optimal solution:    " +opt);
  }
  
}