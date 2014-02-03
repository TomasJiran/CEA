package cea;

import static cea.MyConst.CACHE_CAPACITY;
import javabbob.JNIfgeneric;


public class MyFitness implements Fitness {
  JNIfgeneric fgeneric;
  int maxfunevals;
  private Cache<Object, Double> cache;     
  
  
  //-------------------------------------------------------------------------  
  public MyFitness(JNIfgeneric fgeneric, int maxfunevals) {
    this.fgeneric = fgeneric;
    this.maxfunevals = maxfunevals;
    cache = new Cache<>(CACHE_CAPACITY);        // LRU cache
  }

  //-------------------------------------------------------------------------  
  @Override
  public double getValue(MyGenome genome) {
    double[] x = (double[]) genome.getTopology();
    return calcValue(x);
  }

  //-------------------------------------------------------------------------  
  @Override
  public double getTopologyValue(Object topology) {
    double[] x = (double[]) topology;
    double value;

    if (! cache.containsKey(x)) {               // LRU cache
      value = calcValue(x);
      cache.put(x, value);
      return value;
    }
    else
      return cache.get(x);
  }

  //-------------------------------------------------------------------------  
  @Override
  public boolean noMoreEvaluations() {
    return maxfunevals <= 0;
  }
  
  // PRIVATE METHODS:
  //=======================================================================  
  private double calcValue(double[] x) {
    maxfunevals--;
    return fgeneric.evaluate(x);
  }
}