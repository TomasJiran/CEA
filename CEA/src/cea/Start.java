package cea;

public class Start {
  //-----------------------------------------------------------------------
  public static void main(String[] args) throws Exception  {
    Fitness myFitness = new MyFitness();    
    int initSize = 20;              // initial population size
    int maxSize = 40;               // max population size
    int maxAge = 10;                // max individual's age
    
    Cea cea = new Cea(MyGenome.class, myFitness, initSize, maxSize, maxAge);
    for (int i = 0; i < 30; i++) {
      cea.step();
      cea.printPopulation(i+1);
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