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
  public void crossoverS(Object o1, Object o2) {
  }

  //-------------------------------------------------------------------------  
  @Override
  public void crossoverT(Object x1, Object x2) {
    x = ((double)x1 + (double)x2)/2;
  }

  //-------------------------------------------------------------------------  
  @Override
  public void mutate(double prob) {
    if (random.nextDouble() <= prob)
      x = random.nextDouble()*(max-min) + min;
    
  }

  @Override
  public Object getOptimal() {
    return (4-Math.sqrt(76))/6;
  }

  @Override
  public Object getSolution() {
    return x;
  }

}
