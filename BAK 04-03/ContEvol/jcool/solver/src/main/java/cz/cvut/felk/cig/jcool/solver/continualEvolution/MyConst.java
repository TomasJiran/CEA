package cz.cvut.felk.cig.jcool.solver.continualEvolution;

public final class MyConst {
  // CEA
  public static final double REPRODUCE_PROB = 0.01;   // minimal probability for reproduction
  public static final double ELIMINATE_PROB = 0.8;
  public static final int N_CHILDREN = 2;             // number of parent's children
  //public static final int N_PARENTS = 2;              // number of child's parents

  // Population
  public static final int INIT_SIZE = 5;             // initial population size
  public static final int MAX_SIZE = 10;              // max population size
  public static final int CACHE_CAPACITY = 40;
  
  // Individual
  public static final int MAX_AGE = 8;                // max individuals' age
  
  // MyGenome
  public static final double MUTATE_PROB = 0.1;       // mutation probability - // should be very low
  public static final double MUTATE_RANDOM_PROB = 0.1;  // random mutation probability
  public static final int X_MIN = 10;
  public static final int X_MAX = 50;
  
  private MyConst() {
  }
}