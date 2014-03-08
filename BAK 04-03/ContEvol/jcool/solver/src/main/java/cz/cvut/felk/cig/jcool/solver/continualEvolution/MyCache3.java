package cz.cvut.felk.cig.jcool.solver.continualEvolution;

import java.util.HashMap;
import java.util.Map;



class Vaha3 {
  double vaha;
  Vaha3(double vaha) { this.vaha = vaha; }

  @Override
  public String toString() {
    return "" + vaha;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (o instanceof Vaha == false)
      return false;
    boolean stejnaVaha = (vaha == ((Vaha) o).vaha);
    return stejnaVaha;
  }

  
  @Override
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
public class MyCache3 {
  // java Vector<Double>
  private Map<double[], Vaha3> hm; 
    
  public MyCache3() {
      hm = new HashMap<double[], Vaha3>();
  }
  
  public boolean contains(double[] key) {
    //System.out.println("key: ");
    
    return hm.containsKey(key);
    //V v = hm.get(key);
    //return false;
  }
  
  public void put(double[] key, double num) {
    hm.put(key, new Vaha3(num));
  }
  
  public double get(double[] key) {
    Vaha3 v = hm.get(key);
    return v.vaha;
  }
}