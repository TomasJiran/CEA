package cz.cvut.felk.cig.jcool.solver.continualEvolution;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCache {
  private List<double[]> keys;
  private List<Double> values;
  private double lastValue;
  
  public MyCache() {
    keys = new ArrayList();
    values = new ArrayList();
    lastValue = 0;
  }
  
  private boolean equal(double[] k, double[] key) {
    if (k.length != key.length)
      return false;
    for (int i = 0; i < k.length; i++) {
      if (k[i] != key[i])
        return false;
    }
    return true;
  }
  
  public boolean contains(double[] key) {
    for (int i = 0; i < keys.size(); i++) {
      double[] k = keys.get(i);
      if (equal(k, key)) {
        lastValue = values.get(i);
        return true;  
      }
 
    }
    //   return keys.contains(key);
    return false;
  }
  
  public void put(double[] key, double value) {
    keys.add(key);
    values.add(value);
  }
  
  public double get(double[] key) {
    return lastValue;
  }
  
}