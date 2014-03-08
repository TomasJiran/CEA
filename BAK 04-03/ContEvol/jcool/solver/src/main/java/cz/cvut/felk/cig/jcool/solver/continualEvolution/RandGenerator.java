package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import java.util.Random;


public class RandGenerator {
  private static Random random = new Random();
  
  public static int nextInt(int max) {
    //int r = random.nextInt(max);
    //System.out.println("nextInt " +max +" :"+ r);
    return random.nextInt(max);
    //return r;    
  }

  public static double nextDouble() {
    //double r = random.nextDouble();
    //System.out.println("nextDouble: " + r);
    return random.nextDouble();
    //return r;
  }
  
  public static int nextSign() {  // returns +1 or -1
    if (random.nextBoolean()) {
      //System.out.println("1");
      return 1;
    }
    //System.out.println("-1");
    return -1;
  }
}