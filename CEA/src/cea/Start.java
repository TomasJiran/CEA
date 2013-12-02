package cea;

public class Start {
  //-----------------------------------------------------------------------
  public static void main(String[] args) throws Exception  {
    Fitness myFitness = new MyFitness();    
    int popSize = 20;               // initial population size
    int maxSize = 40;               // max population size
    int maxAge = 10;                // max individual's age
    
    Cea cea = new Cea(MyGenome.class, myFitness, popSize, maxSize, maxAge);
    for (int i = 0; i < 30; i++) {
      cea.step();
      printPopulation(cea);
    }
  
    compareSolution(cea);
  }
  
  //-----------------------------------------------------------------------
  private static void printPopulation(Cea cea) {
    System.out.println(cea.getPopulation());
  }

  //-----------------------------------------------------------------------
  private static void compareSolution(Cea cea) {
    System.out.print("Find local maximum of function ");
    System.out.println("y=(x+2)(x-1)(x-3) + 24, x in <-3, 3):");
    double x = (double) cea.getBestSolution().getGenome().getTopology();
    System.out.println("Best solution found: " +x);
    System.out.println("Optimal solution:    " +((4-Math.sqrt(76))/6));
  }
  
}