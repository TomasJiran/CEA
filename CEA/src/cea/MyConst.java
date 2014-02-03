package cea;

public final class MyConst {
  // CEA
  public static final double REPRODUCE_PROB = 0.01;   // minimal probability for reproduction
  public static final int N_CHILDREN = 2;             // number of parent's children

  // Population
  //public static final int INIT_SIZE = 4;              // initial population size
  //public static final int MAX_SIZE = 8;               // max population size
  public static final int CACHE_CAPACITY = 40;
  
  // Individual
  public static final int MAX_AGE = 7;                // max individuals' age
  
  // MyGenome
  public static final double MUTATE_PROB = 0.02;      // probability of mutation

  
  private MyConst() {
  }
}