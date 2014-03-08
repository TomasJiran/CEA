package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import java.util.HashMap;
import java.util.Map;



class Vaha {
  double vaha;
  Vaha(double vaha) { this.vaha = vaha; }

  public String toString() {
    return "" + vaha;
  }

  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (o instanceof Vaha == false)
      return false;
    boolean stejnaVaha = (vaha == ((Vaha) o).vaha);
    return stejnaVaha;
  }

  
  public int hashCode() {
    return (int) vaha;
  }
  
  /*
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 47 * hash + (this != null ? (int) this.vaha : 0);
    return hash;
  }
  */
  
}

//===========================================================================
public class MyCache2 {
  private Map<Double, Vaha> hm; 
    
  public MyCache2() {
      hm = new HashMap<Double, Vaha>();
  }
  
  public boolean contains(double key) {
    //System.out.println("key: ");
    
    return hm.containsKey(key);
    //V v = hm.get(key);
    //return false;
  }
  
  public void put(double key, double num) {
    hm.put(key, new Vaha(num));
  }
  
  public double get(double key) {
    Vaha v = hm.get(key);
    return v.vaha;
  }
}